import DAO.CancelClassDao;
import DAO.RegisterTrialClassDAO;
import Model.RegisterTrialClass;
import Model.RequestCancel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "RequestControllersForTutor", urlPatterns = {"/RequestControllersForTutor"})
public class RequestControllersForTutor extends HttpServlet {

    CancelClassDao ccDao = new CancelClassDao();
    RegisterTrialClassDAO rgtDao = new RegisterTrialClassDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String requestType = request.getParameter("requestType");
        String tutoridParam = request.getParameter("tutorid");

        if (requestType == null || requestType.isEmpty() || tutoridParam == null || tutoridParam.isEmpty()) {
            request.setAttribute("error", "Request type or Tutor ID is missing");
            request.getRequestDispatcher("View/ManageChangeRequestsOfTutor.jsp").forward(request, response);
            return;
        }

        try {
            int tutorid = Integer.parseInt(tutoridParam);

            if (requestType.equalsIgnoreCase("registerTrial")) {
                ArrayList<RegisterTrialClass> listTrial = rgtDao.getTrialClassByTutorId(tutorid);
                
                // Ghi log danh sách listTrial
                System.out.println("List of RegisterTrialClass:");
                for (RegisterTrialClass trial : listTrial) {
                    System.out.println(trial);
                }

                request.setAttribute("trial", listTrial);
                request.getRequestDispatcher("View/ManageChangeRequestsOfTutor.jsp").forward(request, response);
            } else if (requestType.equalsIgnoreCase("cancelClass")) {
                ArrayList<RequestCancel> listCancel = ccDao.getALlRequestWithTutorID(tutorid);

                // Ghi log danh sách listCancel
                System.out.println("List of RequestCancel:");
                for (RequestCancel cancel : listCancel) {
                    System.out.println(cancel);
                }

                request.setAttribute("cancel", listCancel);
                request.getRequestDispatcher("View/ManageChangeRequestsOfTutor.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Invalid request type");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid Tutor ID format");
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
