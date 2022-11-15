package ua.com.goit.controller.skill;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.service.SkillService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/view-all-skills")
public class FindAllSkillsController extends HttpServlet {
    private static final SkillService SKILL_SERVICE = SkillService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);

        try (var writer = resp.getWriter()) {
            writer.write("<div class=\"container\">");
            writer.write("<h1>Список всех технологий</h1>");
            writer.write("<ul>");
            SKILL_SERVICE.getAllSkillNames().forEach(skillName -> {
                writer.write(String.format("<li><strong>%s</strong></li>", skillName));
            });
            writer.write("</ul>");
            writer.write("</div");
        }
    }
}
