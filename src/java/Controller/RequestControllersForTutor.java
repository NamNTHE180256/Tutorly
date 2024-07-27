package Controller;

import DAO.RegisterTrialClassDAO;
import Model.RegisterTrialClass;
import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RequestControllersForTutor", urlPatterns = {"/RequestControllersForTutor"})
public class RequestControllersForTutor extends HttpServlet {

    RegisterTrialClassDAO rgtDao = new RegisterTrialClassDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String requestType = request.getParameter("requestType");

        if (requestType == null || requestType.isEmpty()) {
            request.getRequestDispatcher("View/ManageChangeRequestsOfTutor.jsp").forward(request, response);
            return;
        }

        if (requestType.equalsIgnoreCase("registerTrial")) {
            try {
                String tutoridParam = request.getParameter("tutorid");
                if (tutoridParam != null) {
                    int tutorid_Raw = Integer.parseInt(tutoridParam);
                    ArrayList<RegisterTrialClass> listTrial = rgtDao.getTrialClassByTutorId(tutorid_Raw);
                    request.setAttribute("trial", listTrial);
                } else {
                    request.setAttribute("error", "Tutor ID is missing");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid Tutor ID format");
            }
        }

        request.getRequestDispatcher("View/ManageChangeRequestsOfTutor.jsp").forward(request, response);
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
