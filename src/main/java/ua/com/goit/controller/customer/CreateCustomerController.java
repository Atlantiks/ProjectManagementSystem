package ua.com.goit.controller.customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.dto.CreateCustomerDto;
import ua.com.goit.exception.DataBaseOperationException;
import ua.com.goit.exception.ValidationException;
import ua.com.goit.service.CustomerService;

import java.io.IOException;

@WebServlet("/add-customer")
public class CreateCustomerController extends HttpServlet {
    private static final CustomerService CUSTOMER_SERVICE = CustomerService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/add-customer.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var newCustomerDto = CreateCustomerDto.builder()
                .name(req.getParameter("name"))
                .surname(req.getParameter("surname"))
                .company(req.getParameter("company").isBlank() ? null : req.getParameter("company"))
                .address(req.getParameter("address"))
                .build();
        try {
            CUSTOMER_SERVICE.createCustomer(newCustomerDto);
            req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);
            resp.getWriter().write("<p>New customer was added to the system.</p>");
        } catch (DataBaseOperationException | ValidationException e) {
            req.setAttribute("errors", e.getMessage());
            doGet(req, resp);
        }

    }
}
