package ua.com.goit.controller.developer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.service.DeveloperService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(urlPatterns = "/find-all-devs")
public class FindAllDevelopersServlet extends HttpServlet {
    private final DeveloperService DEV_SERVICE = DeveloperService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);

        try (var writer = resp.getWriter()) {
            writer.write("<div class=\"container\">");
            writer.write("<h1>Список разработчиков</h1>");
            writer.write("<ul>");
            DEV_SERVICE.getAllDevelopers().forEach(developerDto -> {
                writer.write(String.format("<li>%s %s</li>", developerDto.getFullName(),
                        developerDto.getSex().equals("M") ? "Male" : "Female"));
            });
            writer.write("</ul>");
            writer.write("</div");
        }
    }
}
