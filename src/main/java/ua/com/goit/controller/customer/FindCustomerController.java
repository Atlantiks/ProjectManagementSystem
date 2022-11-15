package ua.com.goit.controller.customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.exception.NotFoundException;
import ua.com.goit.exception.ValidationException;
import ua.com.goit.service.CustomerService;

import java.io.IOException;

@WebServlet("/find-customer")
public class FindCustomerController extends HttpServlet {
    private static final CustomerService CUSTOMER_SERVICE = CustomerService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/find-customer.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String devId = req.getParameter("id");

        try {
            var customer = CUSTOMER_SERVICE.findCustomerById(devId);
            req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);
            var writer = resp.getWriter();
            writer.write("<div class=\"container\">");
            writer.write("<H1>Customer</H1>");
            writer.write(String.format("<p>Name: %s</p>", customer.getName()));
            writer.write(String.format("<p>Surname: %s</p>", customer.getSurname()));
            writer.write(String.format("<p>Company: %s</p>",
                    customer.getCompany().isBlank() ? "none" : customer.getCompany()));
            writer.write(String.format("<p>Address: %s</p>", customer.getAddress()));
            writer.write("</div>");
        } catch (NotFoundException | ValidationException e) {
            req.setAttribute("errors",e.getMessage());
            doGet(req, resp);
        }
    }
}
