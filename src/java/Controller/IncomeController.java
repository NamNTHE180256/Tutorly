package Controller;


import DAO.IncomeDao;
import Model.Income;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "IncomeController", urlPatterns = {"/IncomeController"})
public class IncomeController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        IncomeDao idao = new IncomeDao();
        String view = request.getParameter("view");
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String tutorIdStr = request.getParameter("tutorID");

        int tutorId = 0;
        if (tutorIdStr != null) {
            try {
                tutorId = Integer.parseInt(tutorIdStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                request.setAttribute("error", "Invalid tutor ID.");
                request.getServletContext().getRequestDispatcher("/View/Error.jsp").forward(request, response);
                return;
            }
        }

        Date startDate = null;
        Date endDate = null;
        try {
            if (start != null) {
                startDate = dateFormat.parse(start);
            }
            if (end != null) {
                endDate = dateFormat.parse(end);
            }
            System.out.println("Converted Start Date: " + startDate);
            System.out.println("Converted End Date: " + endDate);
        } catch (ParseException e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid date format.");
            request.getServletContext().getRequestDispatcher("/View/Error.jsp").forward(request, response);
            return;
        }

        if (view != null && view.equalsIgnoreCase("week")) {
            ArrayList<Income> list = idao.GetAllIncomeWithDate(startDate, endDate, tutorId);
            System.out.println("List size: " + list.size()); // Debugging line
            for (Income income : list) {
                System.out.println(income); // Debugging line
            }
            request.setAttribute("listIncome", list);
            request.getServletContext().getRequestDispatcher("/View/Income.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Invalid view type.");
            request.getServletContext().getRequestDispatcher("/View/Error.jsp").forward(request, response);
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
        return "IncomeController Servlet";
    }
}
