package main.java.by.epam.tattoo.controller;

import main.java.by.epam.tattoo.entity.Offer;
import main.java.by.epam.tattoo.entity.Tattoo;
import main.java.by.epam.tattoo.entity.User;
import main.java.by.epam.tattoo.service.OfferService;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.service.TattooService;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
import main.java.by.epam.tattoo.util.JspAttrVal;
import main.java.by.epam.tattoo.util.JspParam;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;

@MultipartConfig(fileSizeThreshold = 1024 * 1024
        , maxFileSize = 1024 * 1024 * 5
        , maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadFileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        OfferService service = new OfferService();
        String userParam = request.getParameter(JspAttr.USER);
        Part filePart = request.getPart(JspParam.IMAGE_FILE);
        InputStream fileContent = filePart.getInputStream();
        byte[] fileAsByteArray = fileContent.readAllBytes();
        if (userParam.equals("user")) {
            Object userObj = session.getAttribute(JspAttr.USER);
            User user = (User) userObj;
            try {
                if (fileAsByteArray.length > 0) {
                    Offer offer = service.registerOffer(user.getId(), fileAsByteArray);
                    if (offer != null) {
                        session.setAttribute(JspAttr.MESSAGE, JspAttrVal.OFFER_SUCCESS);
                        response.sendRedirect(JspAddr.MESSAGE_PAGE);
                    }
                } else {
                    request.setAttribute(JspAttr.WRONG_DATA, "wrong");
                    request.getRequestDispatcher(JspAddr.UPLOAD_PHOTO_PAGE).forward(request, response);
                }
            } catch (ServiceException e) {
                response.sendRedirect(JspAddr.UPLOAD_PHOTO_PAGE);
            }
        }else {
            String style = request.getParameter(JspParam.STYLE);
            String size = request.getParameter(JspParam.SIZE);
            String price = request.getParameter(JspParam.PRICE);
            try {
                if (fileAsByteArray.length > 0) {
                    TattooService tattooService = new TattooService();
                    Tattoo tattoo = tattooService.registerTattoo(style, size, price, fileAsByteArray);
                    if (tattoo!=null){
                        session.setAttribute(JspAttr.MESSAGE, JspAttrVal.OFFER_ACCEPTED);
                        response.sendRedirect(JspAddr.MESSAGE_PAGE);
                    }else {
                        request.setAttribute(JspAttr.WRONG_DATA, "wrong");
                        request.getRequestDispatcher(JspAddr.UPLOAD_PHOTO_PAGE).forward(request, response);
                    }
                } else {
                    request.setAttribute(JspAttr.WRONG_DATA, "wrong");
                    request.getRequestDispatcher(JspAddr.UPLOAD_PHOTO_PAGE).forward(request, response);
                }
            } catch (ServiceException e) {
                response.sendRedirect(JspAddr.UPLOAD_PHOTO_PAGE);
            }
        }
    }
}

