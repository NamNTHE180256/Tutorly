/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AClassDAO;
import DAO.LearnerDAO;
import DAO.LessonDAO;
import DAO.SaveTutorListDAO;
import DAO.SessionDAO;
import DAO.SubjectDAO;
import DAO.TutorAvailabilityDAO;
import DAO.TutorDAO;
import Model.AClass;
import Model.Lesson;
import Model.SaveTutorList;
import Model.Subject;
import Model.Tutor;
import Model.TutorAvailability;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author TRANG
 */
@WebServlet(name = "RegisterTrialCotroller", urlPatterns = {"/RegisterTrialCotroller"})
public class RegisterTrialCotroller extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String tutor_id = request.getParameter("tutor_id");
System.out.println("tutor_id: " + tutor_id);
TutorAvailabilityDAO taDao = new TutorAvailabilityDAO();
TutorDAO tDAO = new TutorDAO();
Tutor tutor = tDAO.getTutorById(Integer.parseInt(tutor_id));
System.out.println("Tutor: " + tutor);
Vector<TutorAvailability> tutorAvailabilities = taDao.getTutorAvailabilityByTutorId(Integer.parseInt(tutor_id));
System.out.println("Tutor Availabilities: " + tutorAvailabilities);
request.setAttribute("tutorAvailabilities", tutorAvailabilities);
request.setAttribute("tutor", tutor);

String service = request.getParameter("service");
System.out.println("service: " + service);
if (service != null && !service.isEmpty()) {
    String learner_id = request.getParameter("learner_id");
    System.out.println("learner_id: " + learner_id);
    String session_id = request.getParameter("session");
    System.out.println("session_id: " + session_id);

    AClassDAO aclassDAO = new AClassDAO();
    LearnerDAO lDAO = new LearnerDAO();
    LessonDAO lessonDAO = new LessonDAO();
    SessionDAO sDAO = new SessionDAO();
    Date today = new Date();

    AClass aClass = new AClass(
        lDAO.getLearnerById(Integer.parseInt(learner_id)), 
        tutor, 
        1, 
        getNearestDayOfWeek(today, sDAO.getSessionById(session_id).getDayOfWeek()), 
        getNearestDayOfWeek(today, sDAO.getSessionById(session_id).getDayOfWeek()), 
        "ongoing"
    );

    int classId = aclassDAO.addClass(aClass);
    System.out.println("Generated Class ID: " + classId);

    int lessonResult = 0;
    Lesson newLesson = null;

    if (classId != 0) {
        // Retrieve the newly added AClass with its classId
        AClass addedClass = aclassDAO.getClassById(aclassDAO.getLatestClassId());
        

        if (addedClass != null) {
            newLesson = new Lesson(
                addedClass, 
                sDAO.getSessionById(session_id), 
                addedClass.getStartDate(), 
                "Scheduled"
            ); 
            lessonResult = lessonDAO.addLesson(newLesson);
            
        }
    }
    boolean success = false;
    if(lessonResult != 0){
        success = true;
    }
    if (success) {
                        HttpSession session = request.getSession();
                        session.setAttribute("successMessage", "Register trial lesson success.");
                    } else {
                        HttpSession session = request.getSession();
                        session.setAttribute("errorMessage", "Register trial lesson fail.");
                    }
    HttpSession session = request.getSession();
    session.setAttribute("success", "Register trial class success");
    RequestDispatcher dispatcher = request.getRequestDispatcher("TutorController");
    dispatcher.forward(request, response);
} else {
    RequestDispatcher dispatcher = request.getRequestDispatcher("View/RegisterTrialView.jsp");
    dispatcher.forward(request, response);
}


    }
      public static Date getNearestDayOfWeek(Date date, String dayOfWeek) {
        // Define the day of the week as Calendar constant
        int targetDay = getDayOfWeekInt(dayOfWeek);

        // Initialize the calendar with the provided date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Find the nearest day of the week
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        int daysDifference = targetDay - currentDay;

        // If daysDifference is negative, it means the target day is in the past week
        if (daysDifference < 0) {
            daysDifference += 7;
        }

        // Adjust to find the nearest day
        calendar.add(Calendar.DAY_OF_WEEK, daysDifference);

        // Return the resulting Date object
        return calendar.getTime();
    }

    // Helper method to convert day of week string to Calendar constant
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

    public static void main(String[] args) {
        // Example usage
        Date today = new Date();
        String targetDayOfWeek = "Wednesday";
        Date nearestDate = getNearestDayOfWeek(today, targetDayOfWeek);
        
        // Format the resulting date for display
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Nearest " + targetDayOfWeek + ": " + sdf.format(nearestDate));
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
