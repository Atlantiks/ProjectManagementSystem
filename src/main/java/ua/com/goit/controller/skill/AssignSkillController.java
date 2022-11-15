package ua.com.goit.controller.skill;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.service.DeveloperService;
import ua.com.goit.service.SkillService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/assign-skill")
public class AssignSkillController extends HttpServlet {
    private static final DeveloperService DEV_SERVICE = DeveloperService.getInstance();
    private static final SkillService SKILL_SERVICE = SkillService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("developers", DEV_SERVICE.getAllDevelopers());
        req.setAttribute("skills", SKILL_SERVICE.getAllSkillNames());
        req.getRequestDispatcher("/html/assign-skill.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String developer = req.getParameter("developer");
        String skill = req.getParameter("skill");

        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);

        try (var writer = resp.getWriter()) {
            writer.write("<div class=\"container\">");
            writer.write(String.format("<p>%s</p>", developer));
            writer.write(String.format("<p>%s</p>", skill));
            writer.write("</div");
        }
    }
}
