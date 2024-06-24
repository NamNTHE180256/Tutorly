package Controller;

import DAO.MaterialDAO;
import Model.Material;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

@WebServlet(name = "MaterialControllers", urlPatterns = {"/MaterialControllers"})
public class MaterialControllers extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        MaterialDAO mDao = new MaterialDAO();
        ArrayList<Material> list = null;

        try {
            int slotid = Integer.parseInt(request.getParameter("Slotid"));
            int classid = Integer.parseInt(request.getParameter("classId"));

            if (action != null && action.equalsIgnoreCase("getall")) {
                list = mDao.getAllMaterialWithID(classid, slotid);
                request.setAttribute("listMaterial", list);
                request.setAttribute("classId", classid);
                request.setAttribute("slotid", slotid);
                request.getRequestDispatcher("View/Material.jsp").forward(request, response);
            } else if (action != null && action.equalsIgnoreCase("download")) {
                int fileid = Integer.parseInt(request.getParameter("id"));
                Material material = mDao.getAllMaterialWithlesson(classid, fileid, slotid);

                if (material != null) {
                    MaterialDAO.FileData fileData = mDao.getFileAsBinary(fileid);

                    if (fileData != null) {
                        String fileName = fileData.getFileName();
                        InputStream fileContent = fileData.getFileContent();

                        // Set the response headers to facilitate file download
                        response.setContentType("application/octet-stream");
                        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

                        // Write the file content to the response output stream
                        try (OutputStream os = response.getOutputStream()) {
                            byte[] buffer = new byte[1024];
                            int bytesRead;
                            while ((bytesRead = fileContent.read(buffer)) != -1) {
                                os.write(buffer, 0, bytesRead);
                            }
                        }

                        fileContent.close();
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid slot ID or class ID");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
