package test;

import top.jaecoding.server.servlet.Servlet;
import top.jaecoding.server.servlet.WebServlet;
import top.jaecoding.server.api.HttpRequest;
import top.jaecoding.server.api.HttpResponse;
import top.jaecoding.server.exception.ServletException;

/**
 * @author: 彭文杰
 * @create: 2017-07-05 16:17
 **/
@WebServlet(urlPattern = "/test")
public class TestLogin extends Servlet{
    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws ServletException {
        System.out.println(request.getSession().getAttribute("user_name"));
        response.write(request.getSession().getAttribute("user_name")+"");
    }
}
