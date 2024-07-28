
package Controller;

import DAO.CancelClassDao;
import Model.RequestCancel;
import DAO.RegisterTrialClassDAO;
import Model.RegisterTrialClass;
import java.io.IOException;
import java.util.Vector;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RequestControllerForLearner", urlPatterns = {"/RequestControllerForLearner"})
public class RequestControllerForLearner extends HttpServlet {

    CancelClassDao ccDao = new CancelClassDao();
    RegisterTrialClassDAO rgtDao = new RegisterTrialClassDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String requestType = request.getParameter("requestType");

        if (requestType == null || requestType.isEmpty()) {
            request.getRequestDispatcher("View/ManageChangeRequests.jsp").forward(request, response);
            return;
        }

        String learneridParam = request.getParameter("learnerid");
        if (learneridParam == null || learneridParam.isEmpty()) {
            request.setAttribute("error", "Learner ID is missing");
            request.getRequestDispatcher("View/ManageChangeRequests.jsp").forward(request, response);
            return;
        }

        try {
            int learnerid = Integer.parseInt(learneridParam);

            if (requestType.equalsIgnoreCase("registerTrial")) {
                Vector<RegisterTrialClass> listTrial = rgtDao.getTrialClassesByLearnerId(learnerid);
                request.setAttribute("trial", listTrial);
            } else if (requestType.equalsIgnoreCase("cancelClass")) {
                ArrayList<RequestCancel> listCancel = ccDao.getALlRequestWithLearnerID(learnerid);
                request.setAttribute("cancel", listCancel);
            } else {
                request.setAttribute("error", "Invalid request type");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid Learner ID format");
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
