package top.jaecoding.server.servlet;



import top.jaecoding.server.servlet.DefaultServlet;
import top.jaecoding.server.servlet.Servlet;
import top.jaecoding.server.api.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * @author: 彭文杰
 * @create: 2017-07-04 12:32
 **/
public abstract class Context {
    public static final Map<Integer, ArrayList<byte[]>> contextRequest = new ConcurrentHashMap<>();

    public static final Map<String,Session> sessions = new ConcurrentHashMap<>();

    public static final Map<String,Servlet> servlets = new HashMap<>();

    public static final ExecutorService es = Executors.newCachedThreadPool();
    public static final Servlet defaultServlet = new DefaultServlet();

    public static final String path = "C:\\Users\\jae\\Desktop\\demo";//自制tomcat的位置

    public static final String ERROR_404 =
            "<html>\n" +
                    "\t<body>\n" +
                    "\t\t404 NOT FOUND!\n" +
                    "\t</body>\n" +
                    "</html>";
    public static final String ERROR_405 = "<html>\n" +
            "\t<body>\n" +
            "\t\t405 METHOD NOT SUPPOET!\n" +
            "\t</body>\n" +
            "</html>";

    public static Map<String, Session> getSessions() {
        return sessions;
    }
}
