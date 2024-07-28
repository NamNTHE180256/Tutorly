
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.AClassDAO;
import DAO.SessionDAO;
import Model.AClass;
import Model.Learner;
import Model.Session;
import Model.Tutor;
import Model.User;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "ViewClassnew", urlPatterns = {"/ViewClassnew"})
public class ViewClassnew extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("View/Login.jsp");
            return;
        }
        String status = request.getParameter("status");

        try {
            if (session.getAttribute("learner") != null) {
                processLearnerRequest(request, response, (Learner) session.getAttribute("learner"), status);
            } else if (session.getAttribute("tutor") != null) {
                processTutorRequest(request, response, (Tutor) session.getAttribute("tutor"), status);
            } else {
                response.sendRedirect("View/Login.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("View/ErrorPage.jsp");
        }
    }

    private void processLearnerRequest(HttpServletRequest request, HttpServletResponse response, Learner learner, String status)
            throws ServletException, IOException {
        int learnerId = learner.getId();
        AClassDAO aClassDAO = new AClassDAO();
        SessionDAO sessionDAO = new SessionDAO();

        Vector<AClass> classes =(status == null) ? aClassDAO.getClassesByLearnerId(learnerId) :aClassDAO.getClassesByLearnerIdAndStatus(learnerId, status);
        Map<Integer, Session> sessionData = getSessionData(classes, sessionDAO);
        Map<Integer, Double> progressMap = getProgressMap(classes, aClassDAO);
        Map<Integer, Integer> aMap = getAttendance(classes, aClassDAO);
        request.setAttribute("classes", classes);
        request.setAttribute("sessionData", sessionData);
        request.setAttribute("progressMap", progressMap);
        request.setAttribute("attendance", aMap);
        request.setAttribute("fn", aClassDAO.countClassByStatusLearner("finished", learnerId));
        request.setAttribute("og", aClassDAO.countClassByStatusLearner("ongoing", learnerId));
        request.setAttribute("tr", aClassDAO.countClassByStatusLearner("trial", learnerId));
        request.getRequestDispatcher("View/ViewClassnew.jsp").forward(request, response);
    }

    private void processTutorRequest(HttpServletRequest request, HttpServletResponse response, Tutor tutor, String status)
            throws ServletException, IOException {
        int tutorId = tutor.getId();
        AClassDAO aClassDAO = new AClassDAO();
        SessionDAO sessionDAO = new SessionDAO();

        Vector<AClass> classes = (status == null) ? aClassDAO.getClassesByTutorId(tutorId) : aClassDAO.getClassesByTutorIdAndStatus(tutorId, status);
        Map<Integer, Session> sessionData = getSessionData(classes, sessionDAO);
        Map<Integer, Double> progressMap = getProgressMap(classes, aClassDAO);
        Map<Integer, Integer> aMap = getAttendance(classes, aClassDAO);

        request.setAttribute("classes", classes);
        request.setAttribute("sessionData", sessionData);
        request.setAttribute("progressMap", progressMap);
        request.setAttribute("attendance", aMap);
        request.setAttribute("fn", aClassDAO.countClassByStatus("finished", tutorId));
        request.setAttribute("og", aClassDAO.countClassByStatus("ongoing", tutorId));
        request.getRequestDispatcher("View/ViewClassnew.jsp").forward(request, response);
    }

    private Map<Integer, Session> getSessionData(Vector<AClass> classes, SessionDAO sessionDAO) {
        Map<Integer, Session> sessionData = new HashMap<>();
        for (AClass aClass : classes) {
            Session classSession = sessionDAO.getSessionByClassId(aClass.getId());
            sessionData.put(aClass.getId(), classSession);
        }
        return sessionData;
    }

    private Map<Integer, Double> getProgressMap(Vector<AClass> classes, AClassDAO aClassDAO) {
        Map<Integer, Double> progressMap = new HashMap<>();
        for (AClass aClass : classes) {
            int finishedSessions = aClassDAO.getFinishedSessions(aClass.getId());
            double progressPercentage = (double) finishedSessions / aClass.getTotalSession() * 100;
            progressMap.put(aClass.getId(), progressPercentage);
        }
        return progressMap;
    }

    private Map<Integer, Integer> getAttendance(Vector<AClass> classes, AClassDAO aClassDAO) {
        Map<Integer, Integer> aMap = new HashMap<>();
        for (AClass aClass : classes) {
            int finishedSessions = aClassDAO.getFinishedSessions(aClass.getId());
            aMap.put(aClass.getId(), finishedSessions);
        }
        return aMap;
    }

    private int calculateTotalAttendance(Map<Integer, Double> progressMap) {
        return progressMap.values().stream().mapToInt(Double::intValue).sum();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
