package ua.com.goit.controller.company;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.dto.CreateCompanyDto;
import ua.com.goit.exception.DataBaseOperationException;
import ua.com.goit.exception.ValidationException;
import ua.com.goit.service.CompanyService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/add-company")
public class CreateCompanyController extends HttpServlet {
    private static final CompanyService COMPANY_SERVICE = CompanyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/add-company.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var newCompanyDto = CreateCompanyDto.builder()
                .name(req.getParameter("name"))
                .country(req.getParameter("country"))
                .build();

        try {
            COMPANY_SERVICE.createCompany(newCompanyDto);
            req.getRequestDispatcher("/html/navigationBar.jsp").include(req, resp);
            try (var writer = resp.getWriter()) {
                writer.write("Company was successfully added!");
            }
        } catch (ValidationException | DataBaseOperationException e) {
            req.setAttribute("errors",e.getMessage());
            doGet(req, resp);
        }
    }
}
