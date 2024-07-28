/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import Config.VNPayConfig;
import static Controller.RegisterOfficialClass.getNearestDayOfWeek;
import DAO.AClassDAO;
import DAO.LearnerDAO;
import DAO.LessonDAO;
import DAO.SessionDAO;
import DAO.SubjectDAO;
import DAO.TransactionDAO;
import DAO.TutorDAO;
import DAO.UserDAO;
import Model.AClass;
import Model.Learner;
import Model.Lesson;
import Model.Transaction;
import Model.Tutor;
import Model.User;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
@WebServlet(name="VnpayReturnServlet", urlPatterns={"/VnpayReturnServlet"})
public class VnpayReturnServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Processing request...");

        Map<String, String> vnp_Params = new HashMap<>();
        for (Enumeration<String> params = req.getParameterNames(); params.hasMoreElements();) {
            String paramName = params.nextElement();
            String paramValue = req.getParameter(paramName);
            vnp_Params.put(paramName, paramValue);
        }

        String vnp_SecureHash = req.getParameter("vnp_SecureHash");
        if (vnp_Params.containsKey("vnp_SecureHash")) {
            vnp_Params.remove("vnp_SecureHash");
        }

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                if (!fieldName.equals(fieldNames.get(fieldNames.size() - 1))) {
                    hashData.append('&');
                }
            }
        }

        String secureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        System.out.println("Generated secure hash: " + secureHash);
        System.out.println("Received secure hash: " + vnp_SecureHash);

        if (secureHash.equals(vnp_SecureHash)) {
            String responseCode = req.getParameter("vnp_ResponseCode");
            System.out.println("Response code: " + responseCode);

            if ("00".equals(responseCode)) {
                addTransaction(req, resp, vnp_Params);
                RegisterOfficialClass(req, resp);
            } else {
                resp.getWriter().write("Payment failed. Response code: " + responseCode);
            }
        } else {
            resp.getWriter().write("Invalid signature \n" + secureHash + "\n" + vnp_SecureHash);
        }
    }

    public void RegisterOfficialClass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String tutor_id = (String) session.getAttribute("tutor_id");
        String learner_id = (String) session.getAttribute("learner_id");
        String session_id = (String) session.getAttribute("session");
        String totallesson = (String) session.getAttribute("totallesson");


        if (tutor_id == null || learner_id == null || session_id == null || totallesson == null) {
            resp.getWriter().write("Missing parameters for registering class");
            return;
        }

        // Example DAOs
        TutorDAO tDAO = new TutorDAO();
        LearnerDAO lDAO = new LearnerDAO();
        SessionDAO sDAO = new SessionDAO();
        AClassDAO aclassDAO = new AClassDAO();
        LessonDAO lessonDAO = new LessonDAO();
        SubjectDAO sbDAO = new SubjectDAO();

        Date today = new Date();
        Date startDate = getNearestDayOfWeek(today, sDAO.getSessionById(session_id).getDayOfWeek());
        Date endDate = getEndDate(startDate, Integer.parseInt(totallesson));

        AClass aClass = new AClass(
                lDAO.getLearnerById(Integer.parseInt(learner_id)),
                tDAO.getTutorById(Integer.parseInt(tutor_id)),
                Integer.parseInt(totallesson),
                startDate,
                endDate,
                "ongoing",
                sbDAO.getSubjectById(tDAO.getTutorById(Integer.parseInt(tutor_id)).getSubject().getId())
        );

        int classId = aclassDAO.addClass(aClass);
        System.out.println("Added class ID: " + classId);
        boolean success = false;
        if (classId != 0) {
            AClass addedClass = aclassDAO.getClassById(aclassDAO.getLatestClassId());
            if (addedClass != null) {
                for (int i = 0; i < Integer.parseInt(totallesson); i++) {
                    Date lessonDate = getLessonDate(startDate, i);
                    Lesson newLesson = new Lesson(
                            addedClass,
                            sDAO.getSessionById(session_id),
                            lessonDate,
                            "Scheduled"
                    );
                    int lessonResult = lessonDAO.addLesson(newLesson);
                    if (lessonResult == 0) {
                        success = false;
                        break;
                    }
                    success = true;
                }
            }
        }

        if (success) {
            session.setAttribute("successMessage", "Register official class success.");
            System.out.println("Register official class success.");
        } else {
            session.setAttribute("errorMessage", "Register official class fail.");
            System.out.println("Register official class fail.");
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("TutorController");
        dispatcher.forward(req, resp);
    }

    private void addTransaction(HttpServletRequest req, HttpServletResponse resp, Map<String, String> vnp_Params) throws IOException {
        // Detailed logging
        System.out.println("Starting addTransaction method...");
        UserDAO uDao = new UserDAO();
        TutorDAO tDao = new TutorDAO();
        HttpSession session = req.getSession();
        String learner_id = (String) session.getAttribute("learner_id");
        String tutor_id = (String) session.getAttribute("tutor_id");

        // Log session attributes
        System.out.println("Learner ID: " + learner_id);
        System.out.println("Tutor ID: " + tutor_id);

        if (learner_id == null || tutor_id == null) {
            resp.getWriter().write("Missing learner_id or tutor_id session attribute");
            return;
        }

        User user = uDao.getUserById(Integer.parseInt(learner_id));
        String amountParam = vnp_Params.get("vnp_Amount");
        String paymentMethod = "VNPay";
        String transactionType = "Payment";
        String status = "Completed";
        String vnp_CreateDate = vnp_Params.get("vnp_CreateDate");

        // Log parameters
        System.out.println("Amount: " + amountParam);
        System.out.println("Transaction Date: " + vnp_CreateDate);

        if (vnp_CreateDate == null) {
            resp.getWriter().write("Missing vnp_CreateDate parameter for transaction");
            return;
        }

        Tutor tutor = tDao.getTutorById(Integer.parseInt(tutor_id));

        if (user == null || amountParam == null) {
            resp.getWriter().write("Missing parameters for transaction");
            return;
        }

        String description = "Payment for learning " + tutor.getSubject().getName() + " by tutor " + tutor.getName();
        int amount = Integer.parseInt(amountParam) / 100; // Assuming the amount is in the smallest currency unit
        Date transactionDate;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            transactionDate = dateFormat.parse(vnp_CreateDate);
        } catch (ParseException e) {
            e.printStackTrace();
            resp.getWriter().write("Invalid transaction date format");
            return;
        }

        Transaction transaction = new Transaction(
                user,
                transactionDate,
                amount,
                paymentMethod,
                transactionType,
                status,
                description
        );

        TransactionDAO transactionDAO = new TransactionDAO();
        boolean isSuccess = transactionDAO.addTransaction(transaction);

        if (isSuccess) {
            System.out.println("Transaction added successfully.");
            resp.getWriter().write("Transaction added successfully.");
        } else {
            resp.getWriter().write("Failed to add transaction.");
            System.out.println("Failed to add transaction.");
        }
    }

    
    private static Date getEndDate(Date startDate, int totallesson) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.WEEK_OF_YEAR, totallesson - 1);
        return calendar.getTime();
    }

    private static Date getLessonDate(Date startDate, int weekOffset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.WEEK_OF_YEAR, weekOffset);
        return calendar.getTime();
    }

    public static Date getNearestDayOfWeek(Date date, String dayOfWeek) {
        int targetDay = getDayOfWeekInt(dayOfWeek);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        int daysDifference = targetDay - currentDay;
        if (daysDifference < 0) {
            daysDifference += 7;
        }
        calendar.add(Calendar.DAY_OF_WEEK, daysDifference);
        return calendar.getTime();
    }

    private static int getDayOfWeekInt(String dayOfWeek) {
        switch (dayOfWeek.toLowerCase()) {
            case "sunday":
                return Calendar.SUNDAY;
            case "monday":
                return Calendar.MONDAY;
            case "tuesday":
                return Calendar.TUESDAY;
            case "wednesday":
                return Calendar.WEDNESDAY;
            case "thursday":
                return Calendar.THURSDAY;
            case "friday":
                return Calendar.FRIDAY;
            case "saturday":
                return Calendar.SATURDAY;
            default:
                throw new IllegalArgumentException("Invalid day of the week: " + dayOfWeek);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
