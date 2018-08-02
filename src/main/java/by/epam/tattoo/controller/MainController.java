package main.java.by.epam.tattoo.controller;

import main.java.by.epam.tattoo.command.Command;
import main.java.by.epam.tattoo.command.CommandException;
import main.java.by.epam.tattoo.command.CommandFactory;
import main.java.by.epam.tattoo.util.JspAddr;
import main.java.by.epam.tattoo.util.JspParam;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MainController", urlPatterns = {"/app"})
public class MainController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String commandName = request.getParameter(JspParam.COMMAND);
        try {
            Command command = CommandFactory.getInstance().defineCommand(commandName);
            String page = command.execute(request);
            if (page!=null) {
                request.getRequestDispatcher(page).forward(request, response);
            }
        } catch (CommandException e) {
            request.getRequestDispatcher(JspAddr.ERROR_PAGE).forward(request, response);
            e.printStackTrace();
        }
    }
}
