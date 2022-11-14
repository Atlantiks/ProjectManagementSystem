package ua.com.goit.controller.project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.mapper.CreateProjectMapper;
import ua.com.goit.service.ProjectService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/find-all-projects")
public class FindAllProjectsController extends HttpServlet {
    private final static ProjectService PROJECT_SERVICE = ProjectService.getInstance();
    private static final CreateProjectMapper PROJECT_MAPPER = CreateProjectMapper.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);

        try (var writer = resp.getWriter()) {
            writer.write("<div class=\"container\">");
            writer.write("<h1>Список проектов</h1>");
            writer.write("<ul>");
            PROJECT_SERVICE.findAllProjects().stream()
                    .map(PROJECT_MAPPER::mapTo)
                    .forEach(project ->
                            writer.write(
                                    String.format("<li><strong>%s</strong> %s %s %s</li>",
                                            project.getName(),
                                            project.getDescription(),
                                            project.getDate(),
                                            project.getStatus())));
            writer.write("</ul>");
            writer.write("</div");
        }
    }
}
