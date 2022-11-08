package ua.com.goit.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Objects;

@WebServlet(urlPatterns = {"/index.html"})
public class IndexController extends HttpServlet {
    private Integer hits;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession();

        System.out.println("req.getParameter(\"email\") = " + req.getParameter("email"));

        hits = (Integer)getServletContext().getAttribute("hits");
        if (Objects.isNull(hits)) {
            getServletContext().setAttribute("hits", 1);
        } else {
            hits += 1;
            getServletContext().setAttribute("hits", hits);
        }

        System.out.println(getServletContext().getAttribute("hits"));
        // отправить главную страницу пользователю
        req.getRequestDispatcher("/html/startPage.jsp").forward(req, resp);
    }
}
