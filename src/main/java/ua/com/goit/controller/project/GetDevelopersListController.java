package ua.com.goit.controller.project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ua.com.goit.service.ProjectService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/find-all-developers-in-project")
public class GetDevelopersListController extends HttpServlet {
    private static final ProjectService PROJECT_SERVICE = ProjectService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/find-all-developers-in-project.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);
        String projectId = req.getParameter("id");

        try (var writer = resp.getWriter()) {
            writer.write("<div class=\"container\">");
            writer.write("<h1>Список разработчиков</h1>");
            writer.write(String.format("<p><i>Проекта с id = %s</i></p>", projectId));


            var devs = PROJECT_SERVICE.getDevelopersList(projectId);

            if (devs.size() > 0) {
                writer.write("<ul>");
                devs.forEach(developerDto -> {
                    writer.write(String.format("<li>%s %s %s</li>",
                            developerDto.getFirstName(),
                            developerDto.getLastName(),
                            developerDto.getSex().equals("M") ? "Male" : "Female"));
                });
                writer.write("</ul>");
                writer.write("</div");
            } else {
                writer.write("<p>Разработчиков не обнаружено</p>");
            }
        }
    }
}
