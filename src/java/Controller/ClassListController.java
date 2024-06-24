package Controller;

import DAO.ClassDAO;
import DAO.SessionDAO;
import Model.AClass;
import Model.Session;
import Model.Tutor;
import jakarta.servlet.RequestDispatcher;
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

@WebServlet(name = "ClassListController", urlPatterns = {"/ClassList"})
public class ClassListController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        Tutor tutor = (Tutor) session.getAttribute("tutor");

        if (tutor != null) {
            int tutorId = tutor.getId();

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
            request.setAttribute("sessionData", sessionData);
            request.setAttribute("progressMap", progressMap);
            RequestDispatcher dispatcher = request.getRequestDispatcher("TutorDashboard.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
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
