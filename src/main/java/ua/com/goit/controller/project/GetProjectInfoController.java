package ua.com.goit.controller.project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.com.goit.service.ProjectService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/view-project-info")
public class GetProjectInfoController extends HttpServlet {
    private static final ProjectService PROJECT_SERVICE = ProjectService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/view-project-info.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        req.getRequestDispatcher("/html/navigationBar.jsp").include(req,resp);

        try (var writer = resp.getWriter()) {
            writer.write("<div class=\"container\">");
            writer.write("<table>");
            writer.write("<tr>");
            writer.write("<th>Дата создания</th>");
            writer.write("<th>Название проекта</th>");
            writer.write("<th>Количество разработчиков</th>");
            writer.write("</tr>");
            writer.write("<tr>");
            PROJECT_SERVICE.getAllProjectsInfo()
                    .forEach(projectInfoDto ->
                            writer.write(String.format("<tr><td>%s</td><td>%s</td><td>%s</td></tr>",
                                    projectInfoDto.getDate(),
                                    projectInfoDto.getName(),
                                    projectInfoDto.getNumberOfDevelopers())));
            writer.write("</tr>");
            writer.write("</table>");
            writer.write("</div>");
        }
    }
}
