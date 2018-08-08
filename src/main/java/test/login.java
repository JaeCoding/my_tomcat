package test;

import top.jaecoding.server.servlet.Servlet;
import top.jaecoding.server.servlet.WebServlet;
import top.jaecoding.server.api.HttpRequest;
import top.jaecoding.server.api.HttpResponse;
import top.jaecoding.server.exception.ServletException;

/**
 * @author: 彭文杰
 * @create: 2017-07-05 16:10
 **/
@WebServlet(urlPattern = "/login")
public class login extends Servlet {
    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws ServletException {
        request.getSession().setAttribute("user_name", "wangjingxin");
        response.write("ok!");
    }
}
