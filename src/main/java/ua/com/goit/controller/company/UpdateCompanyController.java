package ua.com.goit.controller.company;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.dto.UpdateCompanyDto;
import ua.com.goit.exception.DataBaseOperationException;
import ua.com.goit.exception.NotFoundException;
import ua.com.goit.exception.ValidationException;
import ua.com.goit.service.CompanyService;

import java.io.IOException;
import java.util.Objects;

@WebServlet("/update-company")
public class UpdateCompanyController extends HttpServlet {
    private static final CompanyService COMPANY_SERVICE = CompanyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Objects.isNull(req.getParameter("id"))) {
            req.getRequestDispatcher("/html/update-company.jsp").forward(req,resp);
        } else {
            try {
                var company = COMPANY_SERVICE.getCompanyForUpdateById(req.getParameter("id"));

                req.setAttribute("company", company);
            } catch (NotFoundException e) {
                req.setAttribute("errors",e.getMessage());
            }
            req.getRequestDispatcher("/html/update-company.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var country = UpdateCompanyDto.builder()
                .id(req.getParameter("id"))
                .name(req.getParameter("name"))
                .country(req.getParameter("country"))
                .build();

        try {
            COMPANY_SERVICE.update(country);
            req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);
            try (var writer = resp.getWriter()) {
                writer.write("<div class=\"container\">");
                writer.write("Success!");
                writer.write("</div>");
            }
        } catch (ValidationException | DataBaseOperationException ex) {
            req.setAttribute("errors",ex.getMessage());
            doGet(req, resp);
        }
    }
}
