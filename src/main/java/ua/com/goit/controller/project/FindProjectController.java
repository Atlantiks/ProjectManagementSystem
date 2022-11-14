package ua.com.goit.controller.project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.exception.NotFoundException;
import ua.com.goit.exception.ValidationException;
import ua.com.goit.service.ProjectService;

import java.io.IOException;

@WebServlet("/find-project")
public class FindProjectController extends HttpServlet {
    private final static ProjectService PROJECT_SERVICE = ProjectService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/find-project.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            var project = PROJECT_SERVICE.findProjectById(id);
            req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);
            var writer = resp.getWriter();
            writer.write("<div class=\"container\">");
            writer.write(String.format("<H1>Project with id=%s</H1>", id));
            writer.write(String.format("<p>Name: %s</p>", project.getName()));
            writer.write(String.format("<p>Description: %s</p>", project.getDescription()));
            writer.write(String.format("<p>Date created: %s</p>", project.getDate()));
            writer.write(String.format("<p>Status: %s</p>", project.getStatus()));
            writer.write("</div>");
        } catch (NotFoundException | ValidationException e) {
            req.setAttribute("errors",e.getMessage());
            doGet(req, resp);
        }
    }
}
