package ua.com.goit.controller.company;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.exception.NotFoundException;
import ua.com.goit.service.CompanyService;
import ua.com.goit.service.DeveloperService;

import java.io.IOException;

@WebServlet("/find-company")
public class FindCompanyController extends HttpServlet {
    private static final CompanyService COMPANY_SERVICE = CompanyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/find-company.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String companyId = req.getParameter("id");

        try {
            var company = COMPANY_SERVICE.findCompanyById(companyId);
            req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);
            var writer = resp.getWriter();
            writer.write("<div class=\"container\">");
            writer.write("<H1>Company</H1>");
            writer.write(String.format("<p>Name: %s</p>", company.getName()));
            writer.write(String.format("<p>Country: %s</p>", company.getCountry()));
            writer.write("</div>");
        } catch (NotFoundException e) {
            req.setAttribute("errors",e.getMessage());
            doGet(req, resp);
        }
    }
}
