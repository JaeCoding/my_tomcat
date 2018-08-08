package top.jaecoding.server.servlet;


import top.jaecoding.server.MethodEnum;
import top.jaecoding.server.api.HttpRequest;
import top.jaecoding.server.api.HttpResponse;
import top.jaecoding.server.exception.ServletException;

/**
 * @author: 彭文杰
 * @create: 2017-07-04 12:12
 **/
public class Servlet {
    public void service(HttpRequest request, HttpResponse response) throws ServletException {
        if(request.getMethod().equals(MethodEnum.GET))
            doGet(request,response);
        if(request.getMethod().equals(MethodEnum.POST))
            doPost(request,response);
    }

    protected void doGet(HttpRequest request, HttpResponse response) throws ServletException {
        response.write(Context.ERROR_405);
        throw new ServletException(405,"Method Not Allowed");
    }

    protected void doPost(HttpRequest request, HttpResponse response) throws ServletException {
        response.write(Context.ERROR_405);
        throw new ServletException(405,"Method Not Allowed");
    }
}
