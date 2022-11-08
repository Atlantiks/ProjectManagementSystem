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
            writer.write("<h1>Список разработчиков</h1>");
            DEV_SERVICE.getAllDevelopers().forEach(developerDto -> {
                writer.write(String.format("<li>%s %s %s</li></br>", developerDto.getFullName(),
                        developerDto.getSex(),developerDto.getSalary().toString()));
            });
            writer.write("<ul");
            writer.write("</ul");
        }
    }
}
