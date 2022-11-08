package ua.com.goit.controller.developer;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.service.DeveloperService;


import java.io.IOException;

@WebServlet("/add-developer")
public class CreateDeveloperController extends HttpServlet {
    private DeveloperService DEV_SERVICE;

    @Override
    public void init(ServletConfig config) throws ServletException {
        DEV_SERVICE = DeveloperService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/add-developer.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var name = req.getParameter("name");
        var surname = req.getParameter("surname");
        var sex = req.getParameter("sex");

        System.out.println("New Dev:" + name + surname + sex);
    }
}
