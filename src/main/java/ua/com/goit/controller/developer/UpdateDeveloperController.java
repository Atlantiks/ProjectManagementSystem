package ua.com.goit.controller.developer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.dto.FindDeveloperDto;
import ua.com.goit.dto.UpdateDeveloperDto;
import ua.com.goit.exception.DataBaseOperationException;
import ua.com.goit.exception.NotFoundException;
import ua.com.goit.exception.ValidationException;
import ua.com.goit.service.CompanyService;
import ua.com.goit.service.DeveloperService;

import java.io.IOException;
import java.util.Objects;

@WebServlet("/update-developer")
public class UpdateDeveloperController extends HttpServlet {
    private static final DeveloperService DEV_SERVICE = DeveloperService.getInstance();
    private static final CompanyService COMPANY_SERVICE = CompanyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Objects.isNull(req.getParameter("id"))) {
            req.getRequestDispatcher("/html/update-developer.jsp").forward(req,resp);
        } else {
            try {
                var developer = DEV_SERVICE.getDeveloperForUpdate(req.getParameter("id"));
                var companies = COMPANY_SERVICE.findAllCompanies();
                System.out.println(developer);
                req.setAttribute("developer", developer);
                req.setAttribute("companies", companies);
            } catch (NotFoundException e) {
                req.setAttribute("errors",e.getMessage());
            }
            req.getRequestDispatcher("/html/update-developer.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var dev = UpdateDeveloperDto.builder()
                .id(req.getParameter("id"))
                .name(req.getParameter("name"))
                .surname(req.getParameter("surname"))
                .sex(req.getParameter("sex"))
                .companyName(req.getParameter("company"))
                .salary(req.getParameter("salary"))
                .build();
        System.out.println(dev);
        try {
            DEV_SERVICE.updateDeveloper(dev);
            req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);
            resp.getWriter().write("Success!");
        } catch (ValidationException | DataBaseOperationException ex) {
            req.setAttribute("errors",ex.getMessage());
            System.out.println(ex.getMessage());
            doGet(req, resp);
        }
    }
}
