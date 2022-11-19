package ua.com.goit.controller.customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.dto.UpdateCustomerDto;
import ua.com.goit.exception.DataBaseOperationException;
import ua.com.goit.exception.NotFoundException;
import ua.com.goit.exception.ValidationException;
import ua.com.goit.service.CustomerService;

import java.io.IOException;
import java.util.Objects;

@WebServlet("/update-customer")
public class UpdateCustomerController extends HttpServlet {
    private static final CustomerService CUSTOMER_SERVICE = CustomerService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Objects.isNull(req.getParameter("id"))) {
            req.getRequestDispatcher("/html/update-customer.jsp").forward(req,resp);
        } else {
            try {
                var customer = CUSTOMER_SERVICE.getCustomerForUpdate(req.getParameter("id"));

                req.setAttribute("customer", customer);
            } catch (NotFoundException e) {
                req.setAttribute("errors",e.getMessage());
            }
            req.getRequestDispatcher("/html/update-customer.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var customer = UpdateCustomerDto.builder()
                .id(req.getParameter("id"))
                .name(req.getParameter("name"))
                .surname(req.getParameter("surname"))
                .company(req.getParameter("company"))
                .address(req.getParameter("address"))
                .build();
        try {
            CUSTOMER_SERVICE.update(customer);
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
