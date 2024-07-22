package Controller;

import DAO.LessonDAO;
import DAO.MaterialDAO;
import Model.Material;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Acer
 */
@WebServlet(name = "MaterialEachClassControllers", urlPatterns = {"/Material"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
        maxFileSize = 2L * 1024 * 1024 * 1024, // 2 GB
        maxRequestSize = 2L * 1024 * 1024 * 1024 // 2 GB
)
public class MaterialEachClassControllers extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        MaterialDAO mDao = new MaterialDAO(); // Ensure MaterialDAO is properly initialized
        ArrayList<Material> list = null;
        int slotid = Integer.parseInt(request.getParameter("slotid"));
        int classid = Integer.parseInt(request.getParameter("classid"));
        list = mDao.getAllMaterialWithID(classid, slotid);
        if (action.equalsIgnoreCase("download")) {
            int id = Integer.parseInt(request.getParameter("id"));
            list = mDao.getAllMaterialWithID(classid, slotid);
            Material mate = mDao.getAllMaterialWithLesson(classid, id, slotid);

            request.setAttribute("mate", mate);
            request.setAttribute("slotid", slotid);
            request.setAttribute("classid", classid);
            request.setAttribute("listmaterial", list);
            request.getRequestDispatcher("View/Material.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("getall")) {

            request.setAttribute("slotid", slotid);
            request.setAttribute("classid", classid);
            request.setAttribute("listmaterial", list);
            request.getRequestDispatcher("View/Material.jsp").forward(request, response);
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
    Date date = new Date();
    LessonDAO ldao = new LessonDAO();
    MaterialDAO mdao = new MaterialDAO();
    String action = request.getParameter("action");
    int slotid = Integer.parseInt(request.getParameter("slotid"));
    int classid = Integer.parseInt(request.getParameter("classid"));

    if (action.equalsIgnoreCase("upload")) {
        Part filePart = request.getPart("file");
        String fileName = getFileName(filePart);

        // Get the absolute path of the web application and create the uploads directory within it
        String appPath = getServletContext().getRealPath("/");
        File uploads = new File(appPath, "uploads");
        if (!uploads.exists()) {
            uploads.mkdir();
        }

        File file = new File(uploads, fileName);
        if (file.exists()) {
            String newFileName = UUID.randomUUID().toString() + "_" + fileName;
            file = new File(uploads, newFileName);
        }

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        String fileUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/uploads/" + file.getName();
        String fileType = getFileExtension(fileName);

        Material material = new Material(0, fileName, fileUrl, fileType, date, ldao.getLessonById(slotid, classid));
        // Now you can insert the material into the database

        int result = mdao.insertMaterialWithCondition(material, classid);
        if (result > 0) {
            response.sendRedirect("lessonDetailControllers?classid=" + classid + "&lessonId=" + slotid + "&error=upload Success");
        } else {
            request.setAttribute("error", "upload failed");
            response.sendRedirect("lessonDetailControllers?classid=" + classid + "&lessonId=" + slotid + "&error=upload failed");
        }
    }
}

private String getFileName(Part part) {
    String contentDisposition = part.getHeader("content-disposition");
    String[] items = contentDisposition.split(";");
    for (String item : items) {
        if (item.trim().startsWith("filename")) {
            return item.substring(item.indexOf('=') + 2, item.length() - 1);
        }
    }
    return "";
}

private String getFileExtension(String fileName) {
    if (fileName == null || fileName.lastIndexOf(".") == -1) {
        return "";
    }
    return fileName.substring(fileName.lastIndexOf(".") + 1);
}

    @Override
    public String getServletInfo() {
        return "UploadServlet handles file upload and material insertion.";
    }
}
