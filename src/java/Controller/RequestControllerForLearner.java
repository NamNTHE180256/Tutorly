package Controller;

import DAO.RegisterTrialClassDAO;
import Model.RegisterTrialClass;
import java.io.IOException;
import java.util.Vector;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RequestControllerForLearner", urlPatterns = {"/RequestControllerForLearner"})
public class RequestControllerForLearner extends HttpServlet {

    RegisterTrialClassDAO rgtDao = new RegisterTrialClassDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String requestType = request.getParameter("requestType");

        if (requestType == null || requestType.isEmpty()) {
            request.getRequestDispatcher("View/ManageChangeRequests.jsp").forward(request, response);
            return;
        }

        if (requestType.equalsIgnoreCase("registerTrial")) {
            try {
                String learneridParam = request.getParameter("learnerid");
                if (learneridParam != null) {
                    int learnerid = Integer.parseInt(learneridParam);
                    Vector<RegisterTrialClass> listTrial = rgtDao.getTrialClassesByLearnerId(learnerid);
                    request.setAttribute("trial", listTrial);
                } else {
                    request.setAttribute("error", "Learner ID is missing");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid Learner ID format");
            }
        }

        request.getRequestDispatcher("View/ManageChangeRequests.jsp").forward(request, response);
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
