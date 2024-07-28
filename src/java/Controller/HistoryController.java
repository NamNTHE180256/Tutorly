/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AClassDAO;
import DAO.LessonDAO;
import Model.AClass;
import Model.Learner;
import Model.Lesson;
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
import java.util.List;

/**
 *
 * @author Tung Duong
 */
@WebServlet(name = "HistoryController", urlPatterns = {"/history"})
public class HistoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Learner learner = (Learner) session.getAttribute("learner");
        Tutor tutor = (Tutor) session.getAttribute("tutor");
        String classParam = request.getParameter("class");
        String service = request.getParameter("service");

        Integer classId = null;
        if (request.getParameter("classId") != null) {
            try {
                classId = Integer.parseInt(request.getParameter("classId"));
            } catch (NumberFormatException e) {
                response.sendRedirect("View/Error.jsp");
                return;
            }
        }

        LessonDAO ldao = new LessonDAO();
        AClassDAO aClassDAO = new AClassDAO();

        if (user == null) {
            response.sendRedirect("View/Login.jsp");
            return;
        }

        List<Lesson> lessons = new ArrayList<>();
        List<AClass> list = new ArrayList<>();

        if (tutor != null) {
            list = aClassDAO.getClassesByTutorId(tutor.getId());
            if (classId != null) {
                lessons = ldao.getLessonForTutor(tutor.getId(), classId);
            }
        } else if (learner != null) {
            list = aClassDAO.getClassesByLearnerId(learner.getId());
            if (classId != null) {
                lessons = ldao.getLessonForLearner(learner.getId(), classId);
            }
        } 
        
        if (service != null && !service.isEmpty()) {
            try {
                request.setAttribute("list", list);
                request.setAttribute("lessons", lessons);
                request.setAttribute("classParam", classParam);
                Integer lessonId = Integer.parseInt(request.getParameter("lessonId"));
                ldao.updateLessonStatus(lessonId, "Finished");
                request.getRequestDispatcher("View/History.jsp").forward(request, response);
                return;
            } catch (NumberFormatException e) {
                response.sendRedirect("View/Error.jsp");
                return;
            }
        }

        request.setAttribute("list", list);
        request.setAttribute("lessons", lessons);
        request.setAttribute("classParam", classParam);
        request.getRequestDispatcher("View/History.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
