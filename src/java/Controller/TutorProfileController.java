package Controller;

import DAO.TutorDAO;
import DAO.SubjectCountPercentageDAO;
import Model.Tutor;
import Model.SubjectCountPercentage;
import Model.Subject;
import Model.User;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;

/**
 *
 * @author Tung Duong
 */
@WebServlet(name = "TutorProfileController", urlPatterns = {"/TutorProfileController"})
public class TutorProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Tutor tutor = (Tutor) session.getAttribute("tutor");
        if (user == null || !user.getRole().equalsIgnoreCase("tutor") || tutor == null) {
            request.setAttribute("errorMessage", "You don't have permission to access this page");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        String service = request.getParameter("service");
        TutorDAO tDAO = new TutorDAO();
        SubjectCountPercentageDAO scpDAO = new SubjectCountPercentageDAO();
        Vector<SubjectCountPercentage> scp_vector = scpDAO.getSubjectCountPercentage(tutor.getId());
        Vector<Subject> subjectsTaught = scpDAO.getSubjectsTaughtByTutor(tutor.getId());
        TutorDAO tdao = new TutorDAO();
        Tutor currTutor = tdao.getTutorById(tutor.getId());
        System.out.println(currTutor);
        request.setAttribute("t", currTutor);
        if (service == null || service.isEmpty()) {
            request.setAttribute("t", currTutor);
            request.setAttribute("scp_vector", scp_vector);
            request.setAttribute("subjectsTaught", subjectsTaught);
            RequestDispatcher dispatcher = request.getRequestDispatcher("View/TutorProfile.jsp");
            dispatcher.forward(request, response);
        } else if (service.equals("updateRequest")) {
            request.setAttribute("t", currTutor);
            request.setAttribute("scp_vector", scp_vector);
            request.setAttribute("subjectsTaught", subjectsTaught);
            RequestDispatcher dispatcher = request.getRequestDispatcher("View/UpdateTutor.jsp");
            dispatcher.forward(request, response);
        } else if (service.equals("update")) {
            String newName = request.getParameter("name");
            if (newName != null && !newName.isEmpty()) {
                tutor.setName(newName);
                tDAO.updateTutor(tutor);
            }
            request.setAttribute("t", currTutor);
            request.setAttribute("scp_vector", scp_vector);
            request.setAttribute("subjectsTaught", subjectsTaught);
            RequestDispatcher dispatcher = request.getRequestDispatcher("View/TutorProfile.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Tutor tutor = (Tutor) session.getAttribute("tutor");
        if (user == null || !user.getRole().equalsIgnoreCase("tutor") || tutor == null) {
            request.setAttribute("errorMessage", "You don't have permission to access this page");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        String action = request.getParameter("action");
        if (action.equals("add")) {
            String name = request.getParameter("name");
            String bio = request.getParameter("bio");
            int id = Integer.parseInt(request.getParameter("id"));
            TutorDAO tutorDAO = new TutorDAO();
            Tutor t = tutorDAO.getTutorById(id);
            t.setName(name);
            t.setBio(bio);
            try {
                tutorDAO.updateTutor(t);
                session.setAttribute("successMessage", "Update successfully!");
            } catch (Exception e) {
                session.setAttribute("errorMessage", "Update failed!");
            }

            response.sendRedirect("TutorProfileController");
        }
    }
}
