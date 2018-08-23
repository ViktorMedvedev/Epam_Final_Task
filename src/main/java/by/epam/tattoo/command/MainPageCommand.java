package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.entity.Tattoo;
import main.java.by.epam.tattoo.service.PaginationUtil;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.service.TattooService;
import main.java.by.epam.tattoo.util.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;


public class MainPageCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        PaginationUtil<Tattoo> paginationUtil = new PaginationUtil<>();
        HttpSession session = request.getSession();
        TattooService tattooService = new TattooService();
        ArrayList<Tattoo> tattoos;
        ArrayList<Tattoo> tattoosOnPage;
        String style = request.getParameter(JspParam.STYLE);
        String size = request.getParameter(JspParam.SIZE);
        try {
            if (style == null && size == null) {
                style = (String) session.getAttribute(JspParam.STYLE);
                size = (String) session.getAttribute(JspParam.SIZE);
            }

            tattoos = tattooService.findTattoosByParameter(style, size);
            if (!tattoos.isEmpty()) {
                session.setAttribute(JspParam.STYLE, style);
                session.setAttribute(JspParam.SIZE, size);
                tattoosOnPage = paginationUtil.executePagination(request, tattoos);
                session.setAttribute(JspAttr.TATTOOS, tattoosOnPage);
                return JspAddr.HOME_PAGE;
            } else {
                request.setAttribute(JspAttr.MESSAGE, JspAttrVal.SEARCH_FAIL);
                return JspAddr.MESSAGE_PAGE;
            }

        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
