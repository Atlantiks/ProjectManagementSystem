package ua.com.goit.controller.customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.service.CustomerService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/view-all-customers")
public class FindAllCustomersController extends HttpServlet {
    private static final CustomerService CUSTOMER_SERVICE = CustomerService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);

        try (var writer = resp.getWriter()) {
            writer.write("<div class=\"container\">");
            writer.write("<h1>Список всех заказчиков</h1>");
            writer.write("<ul>");
            CUSTOMER_SERVICE.findAllCustomers().forEach(customerDto -> {
                writer.write(String.format("<li><strong>%s %s</strong> %s %s</li>",
                        customerDto.getName(),
                        customerDto.getSurname(),
                        customerDto.getCompany(),
                        customerDto.getAddress()));
            });
            writer.write("</ul>");
            writer.write("</div");
        }
    }
}
