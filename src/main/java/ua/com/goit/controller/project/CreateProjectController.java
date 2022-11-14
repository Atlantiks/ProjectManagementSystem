package ua.com.goit.controller.project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.dto.CreateProjectDto;
import ua.com.goit.exception.DataBaseOperationException;
import ua.com.goit.exception.ValidationException;
import ua.com.goit.service.ProjectService;

import java.io.IOException;

@WebServlet("/add-project")
public class CreateProjectController extends HttpServlet {
    private final static ProjectService PROJECT_SERVICE = ProjectService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/add-project.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var project = CreateProjectDto.builder()
                .name(req.getParameter("name"))
                .description(req.getParameter("description"))
                .date(req.getParameter("date"))
                .status(req.getParameter("status"))
                .build();
        try {
            PROJECT_SERVICE.createProject(project);
            req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);
            resp.getWriter().write("Success!");
        } catch (ValidationException | DataBaseOperationException e) {
            req.setAttribute("errors",e.getMessage());
            doGet(req, resp);
        }

    }
}
