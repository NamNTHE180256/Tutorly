package Controller;

import DAO.LessonDAO;
import DAO.MaterialDAO;
import DAO.VideoDAO;
import Model.Material;
import Model.Video;
import com.sun.jdi.connect.spi.Connection;
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
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

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
        VideoDAO vdao = new VideoDAO();
        MaterialDAO mDao = new MaterialDAO(); // Ensure MaterialDAO is properly initialized
        ArrayList<Material> list = null;
        int slotid = Integer.parseInt(request.getParameter("slotid"));
        int classid = Integer.parseInt(request.getParameter("classid"));
        list = mDao.getAllMaterialWithID(classid, slotid);
        ArrayList<Video> listVideo = vdao.getAllVideoWithID(classid, slotid);
        ArrayList<Material> doc = new ArrayList<>();
        ArrayList<Material> slide = new ArrayList<>();
        ArrayList<Material> book = new ArrayList<>();

        doc = mDao.getAllDocWithID(classid, slotid, "pdf");
        slide = mDao.getAllSlideWithID(classid, slotid, "pdf");
        if (action.equalsIgnoreCase("download")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Material mate = mDao.getAllMaterialWithLesson(classid, id, slotid);

            // Save the file data to a temporary file
            String appPath = getServletContext().getRealPath("/") + "uploads";
            File uploads = new File(appPath);
            if (!uploads.exists()) {
                uploads.mkdirs();
            }

            File file = new File(uploads, mate.getFileName());
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(mate.getFilePath());
            }

            // Set attributes to forward to JSP
            String fileUrl = request.getContextPath() + "/uploads/" + mate.getFileName();
            request.setAttribute("fileName", mate.getFileName());
            request.setAttribute("fileUrl", fileUrl);
            request.setAttribute("slotid", slotid);
            request.setAttribute("doc", doc);
            request.setAttribute("slide", slide);
            request.setAttribute("book", book);
            request.setAttribute("classid", classid);
            request.setAttribute("listvideo", listVideo);

            request.getRequestDispatcher("View/Material.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("getall")) {
            request.setAttribute("slotid", slotid);
            request.setAttribute("classid", classid);
            request.setAttribute("doc", doc);
            request.setAttribute("slide", slide);
            request.setAttribute("book", book);

            request.setAttribute("listvideo", listVideo);
            request.getRequestDispatcher("View/Material.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("downloadVideo")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Video video1 = vdao.getAllVideoWithLesson(classid, id, slotid);
            String videoUrl = video1.getFilePath();
            String videoId = videoUrl.substring(videoUrl.lastIndexOf("v=") + 2); // Trích xuất ID video từ URL
            String embedUrl = "https://www.youtube.com/embed/" + videoId + "?controls=0"; // Tạo URL nhúng
            request.setAttribute("fileUrlYtb", embedUrl);
            request.setAttribute("slotid", slotid);
            request.setAttribute("classid", classid);
            request.setAttribute("doc", doc);
            request.setAttribute("slide", slide);
            request.setAttribute("book", book);
            request.setAttribute("listvideo", listVideo);

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
        MaterialDAO mdao = new MaterialDAO();
        LessonDAO dao = new LessonDAO();
        int slotid = Integer.parseInt(request.getParameter("slotid"));
        int classid = Integer.parseInt(request.getParameter("classid"));
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("upload")) {
            String filename = request.getParameter("fileName");
            Part filePart = request.getPart("file");
            String fileName = getFileName(filePart);

            // Get the absolute path of the web application and create the uploads directory within it
            String appPath = getServletContext().getRealPath("/");
            File uploads = new File(appPath + File.separator + "uploads");
            if (!uploads.exists()) {
                uploads.mkdirs();
            }

            File file = new File(uploads, fileName);
            if (file.exists()) {
                String newFileName = UUID.randomUUID().toString() + "_" + fileName;
                file = new File(uploads, newFileName);
            }

            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            // Read the file into a byte array
            byte[] fileData;
            try (InputStream input = filePart.getInputStream()) {
                fileData = input.readAllBytes();
            }

            // Create the URL for accessing the file
            String fileType = getFileExtension(fileName);

            // Debugging information
            System.out.println("File path: " + file.getAbsolutePath());

            // Tạo đối tượng Material
            Material material = new Material(0, fileName, fileData, fileType, null, dao.getLessonById(slotid, classid));

            int result = mdao.insertMaterialWithCondition(material, classid, slotid);

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
        if (contentDisposition != null) {
            for (String cd : contentDisposition.split(";")) {
                if (cd.trim().startsWith("filename")) {
                    return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                }
            }
        }
        return null;
    }

    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.lastIndexOf('.') > 0) {
            return fileName.substring(fileName.lastIndexOf('.') + 1);
        }
        return null;
    }

    @Override
    public String getServletInfo() {
        return "UploadServlet handles file upload and material insertion.";
    }
}
