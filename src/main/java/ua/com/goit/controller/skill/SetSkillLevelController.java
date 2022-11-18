package ua.com.goit.controller.skill;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.dto.assignSkill.SetSelectedSkillToDeveloperDto;
import ua.com.goit.exception.DataBaseOperationException;
import ua.com.goit.service.SkillService;

import java.io.IOException;

@WebServlet("/set-skill-level")
public class SetSkillLevelController extends HttpServlet {
    private static final SkillService SKILL_SERVICE = SkillService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/set-skill-level.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var skillAndDeveloper = SetSelectedSkillToDeveloperDto.builder()
                .developerId(req.getParameter("developerId"))
                .skillId(req.getParameter("skillId"))
                .build();

        try {
            SKILL_SERVICE.assignNewSkillToDeveloper(skillAndDeveloper);
        } catch (DataBaseOperationException e) {
            req.setAttribute("errors", e.getMessage());
            doGet(req, resp);
        }

        req.getRequestDispatcher("/html/navigationBar.jsp").include(req, resp);
        try (var writer = resp.getWriter()) {
            writer.write("<div class =\"container\">");
            writer.write("<p>Skill added</p>");
            writer.write("</div>");
        }
    }
}
