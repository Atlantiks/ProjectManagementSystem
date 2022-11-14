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
import java.nio.charset.StandardCharsets;

@WebServlet("/get-project-cost")
public class GetProjectCostController extends HttpServlet {
    private static final ProjectService PROJECT_SERVICE = ProjectService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/get-project-cost.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);
        String projectId = req.getParameter("id");

        double salary = PROJECT_SERVICE.getDevelopersSalary(projectId);

        try {
            var writer = resp.getWriter();
            writer.write("<div class=\"container\">");
            writer.write(String.format("<H1>Project with id=%s</H1>", projectId));
            writer.write(String.format("<p>Total cost: <strong>%.2f$</strong></p>", salary));
            writer.write("</div>");
        } catch (NotFoundException | ValidationException e) {
            req.setAttribute("errors",e.getMessage());
            doGet(req, resp);
        }
    }
}
