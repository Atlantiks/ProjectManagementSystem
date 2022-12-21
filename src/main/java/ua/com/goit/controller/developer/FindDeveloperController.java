package ua.com.goit.controller.developer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.exception.NotFoundException;
import ua.com.goit.service.DeveloperService;

import java.io.IOException;

@WebServlet("/find-developer")
public class FindDeveloperController extends HttpServlet {
    private static final DeveloperService DEV_SERVICE = DeveloperService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/find-developer.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String devId = req.getParameter("id");

        try {
            var developer = DEV_SERVICE.findDeveloperById(devId);
            req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);
            var writer = resp.getWriter();
            writer.write("<div class=\"container\">");
            writer.write("<H1>Developer</H1>");
            writer.write(String.format("<p>Name: %s</p>", developer.getFirstName()));
            writer.write(String.format("<p>Surname: %s</p>", developer.getLastName()));
            writer.write(String.format("<p>Sex: %s</p>", developer.getSex().equals("M") ? "Male" : "Female"));
            writer.write(String.format("<p>Company: %s</p>", developer.getCompanyName()));
            writer.write(String.format("<p>Projects: %s</p>", developer.getProjects()));
            writer.write("</div>");
        } catch (NotFoundException e) {
            req.setAttribute("errors",e.getMessage());
            doGet(req, resp);
        }

    }
}
