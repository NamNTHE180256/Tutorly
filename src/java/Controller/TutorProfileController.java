package Controller;

import DAO.TutorDAO;
import DAO.SubjectCountPercentageDAO;
import Model.Tutor;
import Model.Subject;
import Model.User;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet(name = "TutorProfileController", urlPatterns = {"/TutorProfileController"})
@MultipartConfig
public class TutorProfileController extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "uploads";

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
        Vector<Subject> subjectsTaught = scpDAO.getSubjectsTaughtByTutor(tutor.getId());
        Tutor currTutor = tDAO.getTutorById(tutor.getId());

        request.setAttribute("t", currTutor);
        request.setAttribute("subjectsTaught", subjectsTaught);
        
        if (service == null || service.isEmpty()) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("View/TutorProfile.jsp");
            dispatcher.forward(request, response);
        } else if (service.equals("updateRequest")) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("View/UpdateTutor.jsp");
            dispatcher.forward(request, response);
        } else if (service.equals("update")) {
            String newName = request.getParameter("name");
            if (newName != null && !newName.isEmpty()) {
                currTutor.setName(newName);
                tDAO.updateTutor(currTutor);
            }

            // Handle profile image upload
            Part profileImagePart = request.getPart("profileImage");
            if (profileImagePart != null && profileImagePart.getSize() > 0) {
                String fileName = Paths.get(profileImagePart.getSubmittedFileName()).getFileName().toString();
                String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;

                // Create the upload directory if it doesn't exist
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String filePath = uploadPath + File.separator + fileName;
                profileImagePart.write(filePath);

                // Update tutor's image path in the database
                currTutor.setImage(UPLOAD_DIRECTORY + "/" + fileName);
                tDAO.updateTutor(currTutor);
            }

            request.setAttribute("t", currTutor);
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
        if (action.equals("update")) {
            String name = request.getParameter("name");
            int id = Integer.parseInt(request.getParameter("id"));
            TutorDAO tutorDAO = new TutorDAO();
            Tutor t = tutorDAO.getTutorById(id);
            t.setName(name);
            tutorDAO.updateTutor(t);

            // Handle profile image upload
            Part profileImagePart = request.getPart("profileImage");
            if (profileImagePart != null && profileImagePart.getSize() > 0) {
                String fileName = Paths.get(profileImagePart.getSubmittedFileName()).getFileName().toString();
                String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;

                // Create the upload directory if it doesn't exist
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String filePath = uploadPath + File.separator + fileName;
                profileImagePart.write(filePath);

                // Update tutor's image path in the database
                t.setImage(UPLOAD_DIRECTORY + "/" + fileName);
                tutorDAO.updateTutor(t);
            }

            response.sendRedirect("TutorProfileController");
        }
    }
}
