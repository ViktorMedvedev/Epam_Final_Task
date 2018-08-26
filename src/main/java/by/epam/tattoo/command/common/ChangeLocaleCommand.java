package main.java.by.epam.tattoo.command.common;

import main.java.by.epam.tattoo.command.Command;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspAttr;
import main.java.by.epam.tattoo.util.JspAttrVal;
import main.java.by.epam.tattoo.util.JspParam;

import javax.servlet.http.HttpServletRequest;

public class ChangeLocaleCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String lang = request.getParameter(JspParam.LANGUAGE);
        request.getSession().setAttribute(JspAttr.LOCAL, lang);
        request.getSession().setAttribute(JspAttr.MESSAGE, JspAttrVal.CHANGED_LOCALE);
        request.setAttribute(JspAttr.MESSAGE, JspAttrVal.CHANGED_LOCALE);
        return JspAddr.MESSAGE_PAGE;
    }
}

