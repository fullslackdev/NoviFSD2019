package dev.fullslack;


import org.apache.commons.fileupload.servlet.ServletFileUpload;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

@WebServlet(
        name = "fileuploadservlet", // note all lower case
        urlPatterns = "/UploadServlet" // URL of the servlet, extension allowed
)
/*@MultipartConfig(fileSizeThreshold = 6291456, // 6 MB
                maxFileSize = 10485760L, // 10 MB
                maxRequestSize = 20971520L // 20 MB
)*/
@MultipartConfig() // !! TESTING ONLY !!

public class FileUploadServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "D:" + File.separator + "Test" + File.separator; // D:\Test\

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer = startHTMLOutput(writer, "File Uploading Result");

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            try {
                for (Part part : request.getParts()) {
                    if (part != null && part.getSize() > 0) {
                        String fileName = part.getSubmittedFileName();
                        String contentType = part.getContentType();
                        String fileSize = DecimalFormat.getNumberInstance().format(part.getSize());

                        writer.append("Filename: ")
                                .append(fileName)
                                .append("<br>\r\n")
                                .append("Content-type: ")
                                .append(contentType)
                                .append("<br>\r\n")
                                .append("File size: ")
                                .append(fileSize)
                                .append("<br>\r\n")
                                .append("<br>\r\n");

                        if (contentType.equalsIgnoreCase("video/ogg") ||
                                contentType.equalsIgnoreCase("audio/wav") ||
                                contentType.equalsIgnoreCase("audio/mpeg")) {
                            part.write(UPLOAD_DIR + fileName); // Writing the file to location and using original filename
                            writer.append("File uploaded successfully!");
                        } else {
                            writer.append("ERROR! Content-type: ")
                                    .append(contentType)
                                    .append(" is not allowed!");
                        }
                        writer = closeHTMLOutput(writer);
                    }
                }
            } catch (IllegalStateException ex) {
                writer.append("File exceeded allowed size limit!");
                writer = closeHTMLOutput(writer);
                //response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer = startHTMLOutput(writer, "File Uploading Form");

        writer.append("<div><h3>File Upload:</h3>")
                .append("Select a file to upload:<br />")
                .append("<form method=\"post\" action=\"UploadServlet\" enctype=\"multipart/form-data\">")
                .append("<input type=\"file\" name=\"file\" size=\"50\" />")
                .append("<br />")
                .append("<input type=\"submit\" value=\"Upload File\" />")
                .append("</form></div>");
        writer = closeHTMLOutput(writer);
    }

    protected PrintWriter startHTMLOutput(PrintWriter writer) {
        return startHTMLOutput(writer, "Default page title");
    }

    protected PrintWriter startHTMLOutput(PrintWriter writer, String pageTitle) {
        writer.append("<!DOCTYPE html>\r\n")
                .append("<html lang=\"en\">\r\n")
                .append("<head><meta charset=\"UTF-8\"><title>")
                .append(pageTitle)
                .append("</title></head>\r\n")
                .append("<body>\r\n");
        return writer;
    }

    protected PrintWriter closeHTMLOutput(PrintWriter writer) {
        writer.append("</body>\r\n")
                .append("</html>");
        return writer;
    }
}

/*public class FileUploadServlet extends HttpServlet {

    private ServletContext servletContext = this.getServletConfig().getServletContext();

    private final int yourMaxMemorySize = 5000 * 1024;
    private final File yourTempDirectory = new File (servletContext.getInitParameter("file-upload"));
    private final int yourMaxRequestSize = 5000 * 1024;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // Set factory constraints
            factory.setSizeThreshold(yourMaxMemorySize);
            factory.setRepository(yourTempDirectory);

            // Configure a repository (to ensure a secure temp location is used)
            //ServletContext servletContext = this.getServletConfig().getServletContext();
            //File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
            //factory.setRepository(repository);

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            // Set overall request size constraint
            upload.setSizeMax(yourMaxRequestSize);

            // Parse the request
            try {
                parseRequest(upload, request);
            } catch (FileUploadException ex) {
                ex.printStackTrace();
            }

            RequestDispatcher view = request.getRequestDispatcher("good.jsp");
            view.forward(request, response);
        } else {
            RequestDispatcher view = request.getRequestDispatcher("error.jsp");
            view.forward(request, response);
        }
    }

    protected void parseRequest(ServletFileUpload upload, HttpServletRequest request) throws FileUploadException {
        List<FileItem> items;
        items = upload.parseRequest(request);
        // Process the uploaded items
        Iterator<FileItem> iter = items.iterator();
        while (iter.hasNext()) {
            FileItem item = iter.next();

            if (item.isFormField()) {
                //processFormField(item);
            } else {
                processUploadedFile(item);
            }
        }
    }

    protected void processUploadedFile(FileItem item) {
        String fieldName = item.getFieldName();
        String fileName = item.getName();
        String contentType = item.getContentType();
        boolean isInMemory = item.isInMemory();
        long sizeInBytes = item.getSize();
        System.out.println(fileName);
    }
} */
