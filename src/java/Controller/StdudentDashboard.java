package Controller;

import DAO.AClassDAO;
import DAO.SessionDAO;
import Model.AClass;
import Model.Learner;
import Model.Session;
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

@WebServlet(name = "StdudentDashboard", urlPatterns = {"/StdudentDashboard"})
public class StdudentDashboard extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Learner learner = (Learner) session.getAttribute("learner");
        if (learner != null) {
            int learnerID = learner.getId();
            AClassDAO aClassDAO = new AClassDAO();
            SessionDAO sessionDAO = new SessionDAO();

            Vector<AClass> classes = aClassDAO.getClassesByLearnerId(learnerID);


            Map<Integer, Session> sessionData = new HashMap<>();
            for (AClass aClass : classes) {
                Session classSession = sessionDAO.getSessionByClassId(aClass.getId());
                sessionData.put(aClass.getId(), classSession);
            }

            Map<Integer, Double> progressMap = new HashMap<>();
            for (AClass aClass : classes) {
                int finishedSessions = aClassDAO.getFinishedSessions(aClass.getId());
                double progressPercentage = (double) finishedSessions / aClass.getTotalSession() * 100;
                progressMap.put(aClass.getId(), progressPercentage);
            }

            request.setAttribute("classes", classes);
            request.setAttribute("sessionData", sessionData);
            request.setAttribute("progressMap", progressMap);
            request.setAttribute("fn", aClassDAO.countClassByStatusLearner("finished", learnerID));
            request.setAttribute("og", aClassDAO.countClassByStatusLearner("ongoing", learnerID));
            request.getRequestDispatcher("View/StudentDashboard.jsp").forward(request, response);
        } else {
            response.sendRedirect("View/Login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
