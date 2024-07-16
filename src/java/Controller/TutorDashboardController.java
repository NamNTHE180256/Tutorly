/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AClassDAO;
import DAO.ClassDAO;
import DAO.SessionDAO;
import Model.AClass;
import Model.Session;
import Model.Tutor;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author Tung Duong
 */
@WebServlet(name = "TutorDashboardController", urlPatterns = {"/tutor-dashboard"})
public class TutorDashboardController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Tutor tutor = (Tutor) session.getAttribute("tutor");

        if (tutor != null) {
            int tutorId = tutor.getId();
            AClassDAO aClassDAO = new AClassDAO();
            ClassDAO classDAO = new ClassDAO();
            SessionDAO sessionDAO = new SessionDAO();
            Vector<AClass> classes = classDAO.getClassesByTutorId(tutorId);

            Map<Integer, Session> sessionData = new HashMap<>();
            for (AClass aClass : classes) {
                Session classSession = sessionDAO.getSessionByClassId(aClass.getId());
                sessionData.put(aClass.getId(), classSession);
            }

            Map<Integer, Double> progressMap = new HashMap<>();
            for (AClass aClass : classes) {
                int finishedSessions = classDAO.getFinishedSessions(aClass.getId());
                double progressPercentage = (double) finishedSessions / aClass.getTotalSession() * 100;
                progressMap.put(aClass.getId(), progressPercentage);
            }
           
            request.setAttribute("classes", classes);
            //request.setAttribute("fn", aClassDAO.countClassByStatus("finished", tutorId));
            //request.setAttribute("og", aClassDAO.countClassByStatus("ongoing", tutorId));
            request.setAttribute("sessionData", sessionData);
            System.out.println(sessionData);
            request.setAttribute("progressMap", progressMap);
            RequestDispatcher dispatcher = request.getRequestDispatcher("View/TutorDashboard.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("View/Login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
