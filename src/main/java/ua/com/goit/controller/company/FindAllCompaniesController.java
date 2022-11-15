package ua.com.goit.controller.company;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.service.CompanyService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/view-all-companies")
public class FindAllCompaniesController extends HttpServlet {
    private static final CompanyService COMPANY_SERVICE = CompanyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);

        try (var writer = resp.getWriter()) {
            writer.write("<div class=\"container\">");
            writer.write("<h1>Список всех компаний</h1>");
            writer.write("<ul>");
            COMPANY_SERVICE.findAllCompanies().forEach(companyDto -> {
                writer.write(String.format("<li><strong>%s</strong> %s</li>", companyDto.getName(),
                        companyDto.getCountry()));
            });
            writer.write("</ul>");
            writer.write("</div");
        }
    }
}
