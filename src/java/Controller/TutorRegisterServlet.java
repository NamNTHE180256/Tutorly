package Controller;

import DAO.UserDAO;
import DAO.TutorDAO;
import Model.User;
import Model.Tutor;
import Model.Subject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet(name = "TutorRegisterServlet", urlPatterns = {"/TutorRegisterServlet"})
@MultipartConfig(maxFileSize = 16177215) // Upload file's size up to 16MB
public class TutorRegisterServlet extends HttpServlet {

    private final TutorDAO tutorDAO = new TutorDAO();
    private final UserDAO userDAO = new UserDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String step = request.getParameter("step");

        HttpSession session = request.getSession();

        if ("1".equals(step)) {
            String email = request.getParameter("email");
            User existingUser = userDAO.GetUserWithEmail(email);

            if (existingUser != null) {
                request.setAttribute("message", "Email already exists! Please enter a new email.");
                request.getRequestDispatcher("View/register1.jsp").forward(request, response);
            } else {
                User user = new User();
                user.setEmail(email);
                user.setPassword(userDAO.computeMD5Hash(request.getParameter("password")));
                user.setRole("tutor");

                Tutor tutor = new Tutor();
                tutor.setName(request.getParameter("full-name"));
                tutor.setGender("Male".equals(request.getParameter("gender")));
                Subject subject = new Subject();
                subject.setId(Integer.parseInt(request.getParameter("subject-id")));
                tutor.setSubject(subject);

                session.setAttribute("user", user);
                session.setAttribute("tutor", tutor);
                response.sendRedirect("View/register2.jsp");
            }
        } else if ("2".equals(step)) {
            Part filePart = request.getPart("upload-image");
            String fileName = filePart.getSubmittedFileName();
            if (fileName == null || fileName.isEmpty()) {
                request.setAttribute("message", "Please upload a profile picture to continue.");
                request.getRequestDispatcher("View/register2.jsp").forward(request, response);
            } else {
                Tutor tutor = (Tutor) session.getAttribute("tutor");
                tutor.setImage(fileName);
                session.setAttribute("tutor", tutor);
                response.sendRedirect("View/register3.jsp");
            }
        } else if ("3".equals(step)) {
            Tutor tutor = (Tutor) session.getAttribute("tutor");
            String noCertification = request.getParameter("no-certification");
            if (noCertification != null && noCertification.equals("on")) {
                tutor.setEdu("not enter");
            } else {
                tutor.setEdu(request.getParameter("description") + " - " + request.getParameter("issued-by") + ". ");
            }
            session.setAttribute("tutor", tutor);
            response.sendRedirect("View/register4.jsp");
        } else if ("4".equals(step)) {
            Tutor tutor = (Tutor) session.getAttribute("tutor");
            String noEducation = request.getParameter("no-education");
            if (noEducation != null && noEducation.equals("on")) {
                tutor.setEdu("not type");
            } else {
                tutor.setEdu(request.getParameter("school"));
            }
            session.setAttribute("tutor", tutor);
            response.sendRedirect("View/register5.jsp");
        } else if ("5".equals(step)) {
            Tutor tutor = (Tutor) session.getAttribute("tutor");
            tutor.setBio(request.getParameter("introduction"));

            try {
                // Save the user and tutor in the database only after completing the final step
                User user = (User) session.getAttribute("user");
                userDAO.register(user);

                // Retrieve the newly registered user to get the assigned ID
                User registeredUser = userDAO.GetUserWithEmail(user.getEmail());
                tutor.setId(registeredUser.getId());
                tutor.setPrice(0);
                tutor.setBank("Not process");
                tutor.setStatus("Pending");
                boolean success = tutorDAO.insertTutor(tutor);

                // Log the success status
                Logger.getLogger(TutorRegisterServlet.class.getName()).log(Level.INFO, "Tutor insert success: " + success);

                // Invalidate the session to clear the attributes
                session.invalidate();
            } catch (Exception ex) {
                Logger.getLogger(TutorRegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            request.setAttribute("message", "Registration successful! Wait for email. Click OK to comeback to Login.");
            request.getRequestDispatcher("View/register5.jsp").forward(request, response);
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
