package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.entity.Tattoo;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.service.TattooService;
import main.java.by.epam.tattoo.util.AccessChecker;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
import main.java.by.epam.tattoo.util.JspParam;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DeleteTattooPageCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        if (AccessChecker.checkForUserSession(request)){
            return JspAddr.HOME_PAGE;
        }
        HttpSession session = request.getSession();
        String id = request.getParameter(JspParam.TATTOO_ID);
        try {
            TattooService tattooService = new TattooService();
            Tattoo tattoo = tattooService.findTattooById(id);
            if (tattoo != null) {
                session.setAttribute(JspAttr.TATTOO, tattoo);
                return JspAddr.DELETE_TATTOO;
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
