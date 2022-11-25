package Steg;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

@WebServlet("/FileUploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
        maxFileSize = 1024 * 1024 * 50, // 50 MB
        maxRequestSize = 1024 * 1024 * 100) // 100 MB
public class FileUploadServlet extends HttpServlet {

    private static final long serialVersionUID = 205242440643911308L;

    /**
     * Directory where uploaded files will be saved, its relative to the web
     * application directory.
     */
    private static final String UPLOAD_DIR = "uploads";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fileName = "stegEncryptFile.png";
        File file = new File(
                "C:\\Users\\LENOVO\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\Final-Project\\"
                        + fileName);
        if (!file.exists()) {
            throw new ServletException("File doesn't exists on server.");
        }
        System.out.println("File location on server::" + file.getAbsolutePath());
        ServletContext ctx = getServletContext();
        InputStream fis = new FileInputStream(file);
        String mimeType = ctx.getMimeType(file.getAbsolutePath());
        response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        ServletOutputStream os = response.getOutputStream();
        byte[] bufferData = new byte[1024];
        int read = 0;
        while ((read = fis.read(bufferData)) != -1) {
            os.write(bufferData, 0, read);
        }
        os.flush();
        os.close();
        fis.close();
        System.out.println("File downloaded at client successfully");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // gets absolute path of the web application
        String applicationPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        System.out.println("Upload File Directory=" + fileSaveDir.getAbsolutePath());
        String fileName = null;
        // Get all the parts from request and write it to the file on server
        String loc = null;
        for (Part part : request.getParts()) {
            System.out.println("part " + part);
            fileName = getFileName(part);
            String a[] = fileName.split("\\\\", -2);
            System.out.println(fileName + " " + a[a.length - 1]);
            part.write(uploadFilePath + File.separator + a[a.length - 1]);
            loc = a[a.length - 1];
            System.out.println("Inside for " + loc);
            break;
        }
        System.out.println("before try block " + loc);
        String pass = request.getParameter("Pass");
        int i = 1;
        try {
            System.out.println("try block");
            System.out.println(uploadFilePath + File.separator + loc);
            i = DecStegno.func(uploadFilePath + File.separator + loc, pass);
            System.out.println("iiiii    " + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("message", fileName + " File uploaded successfully!");
        System.out.println("File Uploaded ");
        if (i == 0) {
            System.out.println("Incorrect Password");
            request.setAttribute("errorMessage", "Incorrect Password");
            request.getRequestDispatcher("decryption.jsp").forward(request, response);
        } else if (i == 1) {
            String decMsgReceived = DecStegno.readFile();
            System.out.println(decMsgReceived);
            request.setAttribute("decMsgReceived", decMsgReceived);
            request.getRequestDispatcher("decryption1.jsp").forward(request, response);
        }
    }

    /**
     * Utility method to get file name from HTTP header content-disposition
     */
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= " + contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
}
