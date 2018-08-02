package main.java.by.epam.tattoo.controller;


import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.service.TattooService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class ImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageId = request.getParameter("imageId");
        TattooService service = new TattooService();
        try {
            byte[] imageData = service.findTattooImage(imageId);
            response.setContentType("image/jpeg");
            OutputStream os = response.getOutputStream();
            os.write(imageData);
            os.flush();
            os.close();
        } catch (ServiceException e) {
            //ADD CODE!
        }
    }
}
