package ua.com.goit.controller.project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.exception.DataBaseOperationException;
import ua.com.goit.exception.NotFoundException;
import ua.com.goit.service.ProjectService;

import java.io.IOException;

@WebServlet("/delete-project")
public class DeleteProjectController extends HttpServlet {
    private final static ProjectService PROJECT_SERVICE = ProjectService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/delete-project.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            PROJECT_SERVICE.deleteProjectById(req.getParameter("id"));
            req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);
            var writer = resp.getWriter();
            writer.write("<div class=\"container\">");
            writer.write(String.format("<p>Project with id=%s was successfully deleted</p>", req.getParameter("id")));
            writer.write("</div");
        } catch (NotFoundException | DataBaseOperationException e) {
            req.setAttribute("errors",e.getMessage());
            doGet(req, resp);
        }
    }
}
