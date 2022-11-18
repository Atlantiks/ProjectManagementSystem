package ua.com.goit.controller.skill;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.dto.assignSkill.AssignSkillDto;
import ua.com.goit.exception.NotFoundException;
import ua.com.goit.exception.ValidationException;
import ua.com.goit.service.DeveloperService;
import ua.com.goit.service.SkillService;

import java.io.IOException;

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

        var assignSkillDto = AssignSkillDto.builder()
                .developer(developer)
                .skill(skill)
                .build();

        try {
            var skillLevelsForDev = SKILL_SERVICE.getSkillLevels(assignSkillDto);

            req.setAttribute("info",skillLevelsForDev);

            req.getRequestDispatcher("/html/set-skill-level.jsp").forward(req, resp);
        } catch (NotFoundException | ValidationException e) {
            req.setAttribute("errors", e.getMessage());
            doGet(req, resp);
        }
    }
}
