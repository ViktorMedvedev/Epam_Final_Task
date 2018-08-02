package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.entity.tattoo.Tattoo;
import main.java.by.epam.tattoo.service.ServiceException;
import main.java.by.epam.tattoo.service.TattooService;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
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
        HttpSession session = request.getSession();
        try {
            TattooService tattooService = new TattooService();
            ArrayList<Tattoo> tattoos = tattooService.findAllTattoos();
            if (!tattoos.isEmpty()) {
                session.setAttribute(JspAttr.TATTOOS, tattoos);
                return JspAddr.HOME_PAGE;
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return null;
    }
}
