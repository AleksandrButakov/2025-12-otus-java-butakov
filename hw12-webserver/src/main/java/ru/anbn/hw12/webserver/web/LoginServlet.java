package ru.anbn.hw12.webserver.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    private static final String LOGIN_PARAM = "login";
    private static final String PASSWORD_PARAM = "password";

    private final TemplateProcessorImpl templateProcessor;

    public LoginServlet(TemplateProcessorImpl templateProcessor) {
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Если пришел запрос на логаут, уничтожаем сессию
        if ("true".equals(req.getParameter("logout"))) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.invalidate();
            }
        }

        resp.setContentType("text/html;charset=utf-8");
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("hasError", false);

        String page = templateProcessor.getPage("login.ftl", pageVariables);
        resp.getWriter().println(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter(LOGIN_PARAM);
        String password = req.getParameter(PASSWORD_PARAM);

        if ("admin".equals(name) && "admin".equals(password)) {
            HttpSession session = req.getSession(true);
            session.setAttribute("user", "admin");
            // Успешный вход — отправляем на админку
            resp.sendRedirect("/admin");
        } else {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("hasError", true);

            String page = templateProcessor.getPage("login.ftl", pageVariables);
            resp.getWriter().println(page);
        }
    }
}
