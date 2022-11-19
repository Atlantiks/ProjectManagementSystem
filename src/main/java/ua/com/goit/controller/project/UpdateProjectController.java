package ua.com.goit.controller.project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.dto.UpdateDeveloperDto;
import ua.com.goit.dto.UpdateProjectDto;
import ua.com.goit.exception.DataBaseOperationException;
import ua.com.goit.exception.NotFoundException;
import ua.com.goit.exception.ValidationException;
import ua.com.goit.service.DeveloperService;
import ua.com.goit.service.ProjectService;

import java.io.IOException;
import java.util.Objects;

@WebServlet("/update-project")
public class UpdateProjectController extends HttpServlet {
    private static final ProjectService PROJECT_SERVICE = ProjectService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Objects.isNull(req.getParameter("id"))) {
            req.getRequestDispatcher("/html/update-project.jsp").forward(req,resp);
        } else {
            try {
                var project = PROJECT_SERVICE.getProjectForUpdateById(req.getParameter("id"));

                req.setAttribute("project", project);
            } catch (NotFoundException e) {
                req.setAttribute("errors",e.getMessage());
            }
            req.getRequestDispatcher("/html/update-project.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var project = UpdateProjectDto.builder()
                .id(req.getParameter("id"))
                .name(req.getParameter("name"))
                .description(req.getParameter("description"))
                .date(req.getParameter("date"))
                .status(req.getParameter("status"))
                .build();
        try {
            PROJECT_SERVICE.update(project);
            req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);
            resp.getWriter().write("<div class=\"container\">");
            resp.getWriter().write("Success!");
            resp.getWriter().write("</div>");
        } catch (ValidationException | DataBaseOperationException ex) {
            req.setAttribute("errors",ex.getMessage());
            doGet(req, resp);
        }
    }
}
