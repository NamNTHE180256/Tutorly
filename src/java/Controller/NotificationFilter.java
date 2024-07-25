package Controller;

import DAO.NotificationDAO;
import Model.Learner;
import Model.User;
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

/**
 *
 * @author Tung Duong
 */
@WebFilter("/*")
public class NotificationFilter implements Filter {

    private NotificationDAO notificationDAO;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        notificationDAO = new NotificationDAO();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        if (session != null) {
            Learner learner = (Learner) session.getAttribute("learner");
            if (learner != null) {
                List<Notification> notifications = notificationDAO.getNotificationsByLearnerId(learner.getId());
                request.setAttribute("notifications", notifications);
                int totalNoti = notificationDAO.countUnreadNotifications(learner.getId());
                notifications.forEach(System.out::println);
                request.setAttribute("count", totalNoti);
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
