package ua.com.goit.controller.skill;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.dto.CreateSkillDto;
import ua.com.goit.exception.DataBaseOperationException;
import ua.com.goit.exception.ValidationException;
import ua.com.goit.service.SkillService;

import java.io.IOException;

@WebServlet("/add-skill")
public class CreateSkillController extends HttpServlet {
    private static final SkillService SKILL_SERVICE = SkillService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/add-skill.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var newSkillDto = CreateSkillDto.builder()
                .name(req.getParameter("name"))
                .level(req.getParameter("level").isBlank() ? null : req.getParameter("level"))
                .build();
        try {
            SKILL_SERVICE.createSkill(newSkillDto);
            req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);
            resp.getWriter().write("<div class=\"container\">");
            resp.getWriter().write("<h3>New skill was added to the system.</h3>");
            resp.getWriter().write("</div>");
        } catch (ValidationException | DataBaseOperationException e) {
            req.setAttribute("errors", e.getMessage());
            doGet(req, resp);
        }
    }
}
