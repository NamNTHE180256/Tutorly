/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.LearnerDAO;
import DAO.ManagerDAO;
import DAO.SubjectDAO;
import DAO.TransactionDAO;
import DAO.TutorDAO;
import DAO.UserDAO;
import Model.Learner;
import Model.Manager;
import Model.Subject;
import Model.Transaction;
import Model.Tutor;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;
import com.google.gson.Gson;
import java.util.List;

/**
 *admin
 * @author Admin
 */
@WebServlet(name="AdminController", urlPatterns={"/AdminController"})
public class AdminController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        ManagerDAO managerDAO = new ManagerDAO();
        LearnerDAO learnerDao = new LearnerDAO();
        TutorDAO tutorDao = new TutorDAO();
        UserDAO userDAO = new UserDAO();
        SubjectDAO subjectDAO = new SubjectDAO();
        ManagerDAO mDao = new ManagerDAO();
        switch (action) {
            case "learner":
                ArrayList<Learner> learners = learnerDao.getAllLearners();
                request.setAttribute("learners", learners);
                request.getRequestDispatcher("/View/AdminLearner.jsp").forward(request, response);
                break;
            case "searchLearner":
                String searchLearner = request.getParameter("searchLearner");
                //learners = learnerDao.searchLearners(searchLearner);
                //request.setAttribute("learners", learners);
                request.getRequestDispatcher("/View/AdminLearner.jsp").forward(request, response);
                break;
            case "tutor":
                ArrayList<Tutor> tutors = tutorDao.getAllTutors();
                request.setAttribute("tutors", tutors);
                request.getRequestDispatcher("/View/AdminTutor.jsp").forward(request, response);
                break;
            case "searchTutor":
                String searchTutor = request.getParameter("searchTutor");
                tutors = tutorDao.searchTutors(searchTutor);
                request.setAttribute("tutors", tutors);
                request.getRequestDispatcher("/View/AdminTutor.jsp").forward(request, response);
                break;
            case "viewManager":
                ArrayList<Manager> managers = mDao.getAllManagers();
                request.setAttribute("managers", managers);
                request.getRequestDispatcher("/View/AdminManager.jsp").forward(request, response);
                break;
            case "searchManager":
                String search = request.getParameter("searchManager");
                managers = mDao.searchManagers(search);
                request.setAttribute("managers", managers);
                request.getRequestDispatcher("/View/AdminManager.jsp").forward(request, response);
                break;
            case "deleteManager":
                String managerId = request.getParameter("managerId");
                if (managerId != null) {
                    try {
                        int id = Integer.parseInt(managerId);
                        managerDAO.deleteManager(id);
                        response.sendRedirect("AdminController?action=viewManager"); 
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "updateManager":
                int id = Integer.parseInt(request.getParameter("managerId"));
                String name = request.getParameter("name");
                String email = request.getParameter("email");

                Manager manager = new Manager();
                manager.setId(id);
                manager.setName(name);
                userDAO.setEmailByUserId(id, email);

                try {
                    managerDAO.updateManager(manager);
                    response.sendRedirect("AdminController?action=viewManager"); // Redirect to the list page after updating
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "addManager":
                name = request.getParameter("name");
                email = request.getParameter("email");
                String password = request.getParameter("password");
                id = userDAO.addUser(email, password, "manager");
                manager = new Manager(id, name, 0, 0, 0, "noUpdate");
                boolean success = managerDAO.addManager(manager);
                if (success) {
                    HttpSession session = request.getSession();
                    session.setAttribute("successMessage", "Manager added successfully.");
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("errorMessage", "An error occurred while adding the manager.");
                }
                response.sendRedirect("AdminController?action=viewManager");
                break;
            case "dashboard":
                int learnerQuantity = userDAO.getCount("Learner");
                int tutorQuantity = userDAO.getCount("Tutor");
                int managerQuantity = userDAO.getCount("Manager");
                int classQuantity = userDAO.getCount("Class");
                
                Map<String, Integer> subjectTutorCounts = subjectDAO.getTutorsPerSubject();
                
                Gson gson = new Gson();
                String subjectTutorCountsJson = gson.toJson(subjectTutorCounts);
                
                // Set attributes to be accessed in the JSP
                request.setAttribute("learnerQuantity", learnerQuantity);
                request.setAttribute("tutorQuantity", tutorQuantity);
                request.setAttribute("managerQuantity", managerQuantity);
                request.setAttribute("classQuantity", classQuantity);
                request.setAttribute("subjectTutorCountsJson", subjectTutorCountsJson);
                // Forward the request to the JSP page  
                request.getRequestDispatcher("/View/AdminDashboard.jsp").forward(request, response);
                break;
            case "viewSubject":
                Map<Subject, Integer> learnersBySubject = subjectDAO.getNumberOfLearnersBySubject();
                Map<Subject, Integer> classesBySubject = subjectDAO.getNumberOfClassesBySubject();
                Map<Subject, Integer> tutorsBySubject = subjectDAO.getNumberOfTutorsBySubject();
                request.setAttribute("learnersBySubject", learnersBySubject);
                request.setAttribute("classesBySubject", classesBySubject);
                request.setAttribute("tutorsBySubject", tutorsBySubject);
                request.getRequestDispatcher("View/AdminSubject.jsp").forward(request, response);
                break;
            case "addSubject":
                String subjectName = request.getParameter("subject_name");
                if (subjectName != null && !subjectName.isEmpty()) {
                    int add = subjectDAO.addSubject(subjectName);
                    success = add != 0;
                    if (success) {
                        request.setAttribute("successMessage", "Add subject success.");
                    } else {
                        request.setAttribute("errorMessage", "Add subject fail.");
                    }
                }
                request.getRequestDispatcher("View/AdminSubject.jsp").forward(request, response);
                break;
                
            case "viewIncome":
                TransactionDAO tranDao = new TransactionDAO();
                List<Transaction> trans = tranDao.getAllTransactions();
                request.setAttribute("trans", trans);
                request.getRequestDispatcher("View/AdminIncome.jsp").forward(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
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
