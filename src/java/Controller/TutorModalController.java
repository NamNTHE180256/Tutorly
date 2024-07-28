/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.TutorDAO;
import Model.Certificate;
import Model.Tutor;
import Model.TutorAvailability;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name="TutorModalController", urlPatterns={"/TutorModal"})
public class TutorModalController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TutorModalController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TutorModalController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int tutorId = Integer.parseInt(request.getParameter("id"));
        TutorDAO tDao = new TutorDAO();
        Tutor tutor = tDao.getTutorById(tutorId);
        List<Certificate> certs = tDao.getAllCertificatesByTutorId(tutorId);

        // Generate the HTML content for the modal
        StringBuilder modalContent = new StringBuilder();
        modalContent.append("<div class='container mt-3'>");
        modalContent.append("<div class='accordion' id='accordion").append(tutorId).append("'>");

        // Hidden element to store the tutor status
        modalContent.append("<div id='tutorStatus' style='display: none;'>").append(tutor.getStatus()).append("</div>");

        // Description section
        modalContent.append("<div class='card'>")
                .append("<div class='card-header' id='headingOne").append(tutorId).append("'>")
                .append("<h5 class='mb-0'>")
                .append("<button class='btn btn-link' type='button' data-toggle='collapse' data-target='#collapseOne").append(tutorId).append("' aria-expanded='true' aria-controls='collapseOne").append(tutorId).append("'>")
                .append("Description")
                .append("</button>")
                .append("</h5>")
                .append("</div>")
                .append("<div id='collapseOne").append(tutorId).append("' class='collapse show' aria-labelledby='headingOne").append(tutorId).append("'>")
                .append("<div class='card-body'>").append(tutor.getBio()).append("</div>")
                .append("</div>")
                .append("</div>");

        // Education section
        modalContent.append("<div class='card'>")
                .append("<div class='card-header' id='headingTwo").append(tutorId).append("'>")
                .append("<h5 class='mb-0'>")
                .append("<button class='btn btn-link collapsed' type='button' data-toggle='collapse' data-target='#collapseTwo").append(tutorId).append("' aria-expanded='false' aria-controls='collapseTwo").append(tutorId).append("'>")
                .append("Education")
                .append("</button>")
                .append("</h5>")
                .append("</div>")
                .append("<div id='collapseTwo").append(tutorId).append("' class='collapse' aria-labelledby='headingTwo").append(tutorId).append("'>")
                .append("<div class='card-body'>").append(tutor.getEdu()).append("</div>")
                .append("</div>")
                .append("</div>");

        // Certificate section
        modalContent.append("<div class='card'>")
                .append("<div class='card-header' id='headingThree").append(tutorId).append("'>")
                .append("<h5 class='mb-0'>")
                .append("<button class='btn btn-link collapsed' type='button' data-toggle='collapse' data-target='#collapseThree").append(tutorId).append("' aria-expanded='false' aria-controls='collapseThree").append(tutorId).append("'>")
                .append("Certificate")
                .append("</button>")
                .append("</h5>")
                .append("</div>")
                .append("<div id='collapseThree").append(tutorId).append("' class='collapse' aria-labelledby='headingThree").append(tutorId).append("'>")
                .append("<div class='card-body'>");

        if (certs.isEmpty()) {
            modalContent.append("None");
        } else {
            for (Certificate certificate : certs) {
                modalContent.append("<img src='image/").append(certificate.getImage()).append("' alt='Certificate' class='img-thumbnail' style='margin: 5px;' />");
            }
        }

        modalContent.append("</div>")
                .append("</div>")
                .append("</div>");

        // Availability section
        modalContent.append("<div class='card'>")
                .append("<div class='card-header' id='headingFour").append(tutorId).append("'>")
                .append("<h5 class='mb-0'>")
                .append("<button class='btn btn-link collapsed' type='button' data-toggle='collapse' data-target='#collapseFour").append(tutorId).append("' aria-expanded='false' aria-controls='collapseFour").append(tutorId).append("'>")
                .append("Availability")
                .append("</button>")
                .append("</h5>")
                .append("</div>")
                .append("<div id='collapseFour").append(tutorId).append("' class='collapse' aria-labelledby='headingFour").append(tutorId).append("'>")
                .append("<div class='card-body'>")
                .append("<div class='table-responsive'>")
                .append("<table class='table table-bordered text-center'>")
                .append("<thead>")
                .append("<tr class='bg-light-gray'>")
                .append("<th class='text-uppercase'>Time</th>")
                .append("<th class='text-uppercase'>Monday</th>")
                .append("<th class='text-uppercase'>Tuesday</th>")
                .append("<th class='text-uppercase'>Wednesday</th>")
                .append("<th class='text-uppercase'>Thursday</th>")
                .append("<th class='text-uppercase'>Friday</th>")
                .append("<th class='text-uppercase'>Saturday</th>")
                .append("<th class='text-uppercase'>Sunday</th>")
                .append("</tr>")
                .append("</thead>")
                .append("<tbody>");

        List<TutorAvailability> availabilities = tutor.getAvailabilities();
        String[] times = {"08:00", "10:00", "14:00", "16:00", "19:00"};
        int timeIndex = 0;

        for (int i = 0; i < availabilities.size(); i += 7) {
            modalContent.append("<tr>")
                    .append("<td class='align-middle'>").append(times[timeIndex++]).append("</td>");
            for (int j = 0; j < 7; j++) {
                TutorAvailability availability = availabilities.get(i + j);
                if (availability.getStatus().equals("Available")) {
                    modalContent.append("<td class='text-success'><i class='fa fa-check'></i> Available</td>");
                } else {
                    modalContent.append("<td class='bg-light-gray'></td>");
                }
            }
            modalContent.append("</tr>");
        }

        modalContent.append("</tbody>")
                .append("</table>")
                .append("</div>") // close table-responsive
                .append("</div>") // close card-body
                .append("</div>") // close card
                .append("</div>") // close accordion

                .append("</div>") // close container
                .append("</div>"); // close outer div

        response.setContentType("text/html");
        response.getWriter().write(modalContent.toString());
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
