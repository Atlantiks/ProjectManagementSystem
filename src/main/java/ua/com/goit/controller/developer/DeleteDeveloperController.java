package ua.com.goit.controller.developer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.exception.DataBaseOperationException;
import ua.com.goit.exception.NotFoundException;
import ua.com.goit.service.DeveloperService;

import java.io.IOException;

@WebServlet("/delete-developer")
public class DeleteDeveloperController extends HttpServlet {
    private final DeveloperService DEV_SERVICE = DeveloperService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/delete-developer.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            DEV_SERVICE.deleteDeveloperById(req.getParameter("id"));
            req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);
            var writer = resp.getWriter();
            writer.write("<div class=\"container\">");
            writer.write(String.format("<p>Developer with id=%s was successfully deleted</p>", req.getParameter("id")));
            writer.write("</div");
        } catch (NotFoundException | DataBaseOperationException e) {
            req.setAttribute("errors",e.getMessage());
            doGet(req, resp);
        }
    }
}
