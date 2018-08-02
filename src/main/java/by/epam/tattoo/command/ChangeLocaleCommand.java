package main.java.by.epam.tattoo.command;

import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
import main.java.by.epam.tattoo.util.JspParam;

import javax.servlet.http.HttpServletRequest;

public class ChangeLocaleCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String lang = request.getParameter(JspParam.LANGUAGE);
        request.getSession().setAttribute(JspAttr.LOCAL, lang);
        request.getSession().setAttribute(JspAttr.CHANGED_LOCALE, "changed");
        return JspAddr.MESSAGE_PAGE;
    }
}
