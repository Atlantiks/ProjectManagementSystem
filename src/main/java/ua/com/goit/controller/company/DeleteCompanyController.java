package ua.com.goit.controller.company;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.exception.DataBaseOperationException;
import ua.com.goit.exception.NotFoundException;
import ua.com.goit.exception.ValidationException;
import ua.com.goit.service.CompanyService;

import java.io.IOException;

@WebServlet("/delete-company")
public class DeleteCompanyController extends HttpServlet {
    private static final CompanyService COMPANY_SERVICE = CompanyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/delete-company.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            COMPANY_SERVICE.deleteCompanyById(req.getParameter("id"));
            req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);
            var writer = resp.getWriter();
            writer.write("<div class=\"container\">");
            writer.write(String.format("<p>Company with id=%s was successfully deleted</p>", req.getParameter("id")));
            writer.write("</div");
        } catch (NotFoundException | DataBaseOperationException | ValidationException e) {
            req.setAttribute("errors",e.getMessage());
            doGet(req, resp);
        }
    }
}
