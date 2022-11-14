package ua.com.goit.controller.developer;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.dto.CreateDeveloperDto;
import ua.com.goit.exception.DataBaseOperationException;
import ua.com.goit.exception.ValidationException;
import ua.com.goit.service.CompanyService;
import ua.com.goit.service.DeveloperService;


import java.io.IOException;

@WebServlet("/add-developer")
public class CreateDeveloperController extends HttpServlet {
    private DeveloperService DEV_SERVICE;
    private CompanyService COMPANY_SERVICE;

    @Override
    public void init(ServletConfig config) throws ServletException {
        DEV_SERVICE = DeveloperService.getInstance();
        COMPANY_SERVICE = CompanyService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var allCompanies = COMPANY_SERVICE.findAllCompanies();
        req.setAttribute("companies",allCompanies);
        req.getRequestDispatcher("/html/add-developer.jsp").include(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreateDeveloperDto newDev = CreateDeveloperDto.builder()
                .firstName(req.getParameter("name"))
                .lastName(req.getParameter("surname"))
                .sex(req.getParameter("sex"))
                .companyId(req.getParameter("company"))
                .salary(req.getParameter("salary"))
                .build();
        try {
            DEV_SERVICE.createDeveloper(newDev);
            req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);
            resp.getWriter().write("Success!");
        } catch (ValidationException | DataBaseOperationException ex) {
            req.setAttribute("errors",ex.getMessage());
            doGet(req, resp);
        }
    }
}
