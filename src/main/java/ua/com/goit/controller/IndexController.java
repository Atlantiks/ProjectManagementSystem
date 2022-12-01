package ua.com.goit.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ua.com.goit.dao.ConnectionManager;

import java.io.IOException;
import java.util.Objects;

@WebServlet(urlPatterns = {"/index.html"})
public class IndexController extends HttpServlet {
    private final static ConnectionManager cm = ConnectionManager.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // отправить главную страницу пользователю
        req.getRequestDispatcher("/html/startPage.jsp").forward(req, resp);
    }
}
