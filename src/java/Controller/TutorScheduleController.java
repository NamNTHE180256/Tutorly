
import DAO.AClassDAO;
import DAO.LessonDAO;
import DAO.TutorDAO;
import Model.AClass;
import Model.Lesson;
import Model.Tutor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "TutorScheduleController", urlPatterns = {"/TutorScheduleController"})
public class TutorScheduleController extends HttpServlet {

    private List<String> generateWeekOptions() {
        List<String> weeks = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfOutput = new SimpleDateFormat("dd/MM/yyyy");

        // Generate 5 weeks in the past
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        for (int i = 5; i > 0; i--) {
            calendar.add(Calendar.DATE, -7); // Move to the previous Monday
            String startDate = sdfInput.format(calendar.getTime());
            calendar.add(Calendar.DATE, 6); // Move to Sunday of that week
            String endDate = sdfInput.format(calendar.getTime());
            try {
                String formattedWeek = sdfOutput.format(sdfInput.parse(startDate)) + " to " + sdfOutput.format(sdfInput.parse(endDate));
                weeks.add(formattedWeek);
            } catch (Exception e) {
                e.printStackTrace();
            }
            calendar.add(Calendar.DATE, -6); // Move back to Monday
        }

        // Reset to the start of the current week (Monday)
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Generate current week
        String currentStartDate = sdfInput.format(calendar.getTime());
        calendar.add(Calendar.DATE, 6); // Move to Sunday
        String currentEndDate = sdfInput.format(calendar.getTime());
        try {
            String formattedWeek = sdfOutput.format(sdfInput.parse(currentStartDate)) + " to " + sdfOutput.format(sdfInput.parse(currentEndDate));
            weeks.add(formattedWeek);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Generate 5 weeks in the future
        for (int i = 0; i < 5; i++) {
            calendar.add(Calendar.DATE, 1); // Move to next Monday
            String startDate = sdfInput.format(calendar.getTime());
            calendar.add(Calendar.DATE, 6); // Move to Sunday
            String endDate = sdfInput.format(calendar.getTime());
            try {
                String formattedWeek = sdfOutput.format(sdfInput.parse(startDate)) + " to " + sdfOutput.format(sdfInput.parse(endDate));
                weeks.add(formattedWeek);
            } catch (Exception e) {
                e.printStackTrace();
            }
            calendar.add(Calendar.DATE, 1); // Move to next Monday
        }

        // Sort the weeks to ensure past weeks are in ascending order
        weeks.sort((w1, w2) -> {
            try {
                Date d1 = sdfOutput.parse(w1.split(" to ")[0]);
                Date d2 = sdfOutput.parse(w2.split(" to ")[0]);
                return d1.compareTo(d2);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        });

        return weeks;
    }

    private String[] getCurrentWeekDates() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String startDate = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, 6); // Move to Sunday
        String endDate = sdf.format(calendar.getTime());

        return new String[]{startDate, endDate};
    }

     

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Tutor tutor = (Tutor) session.getAttribute("tutor"); // Get tutorId from session

        LessonDAO lessonDAO = new LessonDAO();
        TutorDAO tutorDAO = new TutorDAO();
        AClassDAO aClassDAO = new AClassDAO();

        Vector<Lesson> lessons;

        String selectedWeek = request.getParameter("selectedWeek");
        if (selectedWeek != null && !selectedWeek.isEmpty()) {
            String[] dates = selectedWeek.split(" to ");
            SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd");
            try {
                String startDate = sdfOutput.format(sdfInput.parse(dates[0]));
                String endDate = sdfOutput.format(sdfInput.parse(dates[1]));
                //lessons = lessonDAO.getLessonsByTutorIdAndDateRange(tutor.getId(), startDate, endDate);
            } catch (Exception e) {
                e.printStackTrace();
                lessons = new Vector<>();
            }
        } else {
            // Use current week as default
            String[] currentWeekDates = getCurrentWeekDates();
            String startDate = currentWeekDates[0];
            String endDate = currentWeekDates[1];
            //lessons = lessonDAO.getLessonsByTutorIdAndDateRange(tutor.getId(), startDate, endDate);
            selectedWeek = startDate + " to " + endDate;
        }

        Vector<AClass> classes = aClassDAO.displayAllClasses();
        List<String> weeks = generateWeekOptions();

        request.setAttribute("weeks", weeks);
        request.setAttribute("selectedWeek", selectedWeek);
        //request.setAttribute("lessons", lessons);
        request.setAttribute("tutor", tutor);
        request.setAttribute("classes", classes);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/View/TutorSchedule.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    public String getServletInfo() {
        return "Tutor Schedule Controller";
    }
}
