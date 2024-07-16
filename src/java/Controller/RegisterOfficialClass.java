package Controller;

import DAO.AClassDAO;
import DAO.LearnerDAO;
import DAO.LessonDAO;
import DAO.SessionDAO;
import DAO.SubjectDAO;
import DAO.TutorDAO;
import Model.AClass;
import Model.Lesson;
import Model.Tutor;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@WebServlet(name = "RegisterOfficialClass", urlPatterns = {"/RegisterOfficialClass"})
public class RegisterOfficialClass extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String tutor_id = request.getParameter("tutor_id");
        String learner_id = request.getParameter("learner_id");
        String session_id = request.getParameter("session");
        String totallesson = request.getParameter("totallesson");

        TutorDAO tDAO = new TutorDAO();
        Tutor tutor = tDAO.getTutorById(Integer.parseInt(tutor_id));
        LearnerDAO lDAO = new LearnerDAO();
        SessionDAO sDAO = new SessionDAO();
        AClassDAO aclassDAO = new AClassDAO();
        LessonDAO lessonDAO = new LessonDAO();
        SubjectDAO sbDAO = new SubjectDAO();

        Date today = new Date();
        Date startDate = getNearestDayOfWeek(today, sDAO.getSessionById(session_id).getDayOfWeek());
        Date endDate = getEndDate(startDate, Integer.parseInt(totallesson));

        AClass aClass = new AClass(
                lDAO.getLearnerById(Integer.parseInt(learner_id)),
                tutor,
                Integer.parseInt(totallesson),
                startDate,
                endDate,
                "ongoing",
                sbDAO.getSubjectById(tutor.getSubject().getId())
        );

        int classId = aclassDAO.addClass(aClass);
        boolean success = false;
        if (classId != 0) {
            AClass addedClass = aclassDAO.getClassById(aclassDAO.getLatestClassId());
            if (addedClass != null) {
                for (int i = 0; i < Integer.parseInt(totallesson); i++) {
                    Date lessonDate = getLessonDate(startDate, i);
                    Lesson newLesson = new Lesson(
                            addedClass,
                            sDAO.getSessionById(session_id),
                            lessonDate,
                            "Scheduled"
                    );
                    int lessonResult = lessonDAO.addLesson(newLesson);
                    if (lessonResult == 0) {
                        success = false;
                        break;
                    }
                    success = true;
                }
            }
        }

        HttpSession session = request.getSession();
        if (success) {
            session.setAttribute("successMessage", "Register official class success.");
        } else {
            session.setAttribute("errorMessage", "Register official class fail.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("TutorController");
        dispatcher.forward(request, response);
    }

    private static Date getEndDate(Date startDate, int totallesson) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.WEEK_OF_YEAR, totallesson - 1);
        return calendar.getTime();
    }

    private static Date getLessonDate(Date startDate, int weekOffset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.WEEK_OF_YEAR, weekOffset);
        return calendar.getTime();
    }

    public static Date getNearestDayOfWeek(Date date, String dayOfWeek) {
        int targetDay = getDayOfWeekInt(dayOfWeek);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        int daysDifference = targetDay - currentDay;
        if (daysDifference < 0) {
            daysDifference += 7;
        }
        calendar.add(Calendar.DAY_OF_WEEK, daysDifference);
        return calendar.getTime();
    }

    private static int getDayOfWeekInt(String dayOfWeek) {
        switch (dayOfWeek.toLowerCase()) {
            case "sunday":
                return Calendar.SUNDAY;
            case "monday":
                return Calendar.MONDAY;
            case "tuesday":
                return Calendar.TUESDAY;
            case "wednesday":
                return Calendar.WEDNESDAY;
            case "thursday":
                return Calendar.THURSDAY;
            case "friday":
                return Calendar.FRIDAY;
            case "saturday":
                return Calendar.SATURDAY;
            default:
                throw new IllegalArgumentException("Invalid day of the week: " + dayOfWeek);
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
