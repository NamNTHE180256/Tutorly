package Controller;

import DAO.TutorNotificationDAO;
import Model.Notification;
import Model.Tutor;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class TutorNotificationFilter implements Filter {

    private TutorNotificationDAO notificationDAO;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        notificationDAO = new TutorNotificationDAO();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        if (session != null) {
            Tutor tutor = (Tutor) session.getAttribute("tutor");
            if (tutor != null) {
                List<Notification> notifications = notificationDAO.getNotificationsByTutorId(tutor.getId());
                request.setAttribute("notifications", notifications);
                int totalNoti = notificationDAO.countUnreadNotifications(tutor.getId());
                request.setAttribute("count", totalNoti);
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
