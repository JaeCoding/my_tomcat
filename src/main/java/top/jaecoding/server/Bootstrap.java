package top.jaecoding.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import top.jaecoding.server.servlet.Context;
import top.jaecoding.server.servlet.Servlet;
import top.jaecoding.server.servlet.WebServlet;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 彭文杰
 * @create: 2017-07-04 12:12
 * 启动类
 **/
public class Bootstrap {
    public static final Log log = LogFactory.getLog(Bootstrap.class);

    public static void main(String[] args) {
        log.info("开始启动微型应用服务器");
        List<Class<Servlet>> servletList = loadClasses();
        log.info("加载class结束");
        servletList.forEach(servlet->{
            String url = HandlerAnnotation(servlet);//获取class有注释，有urlPattern，转化为servlet后获取
            if(url!=null)
                try {
                    Context.servlets.put(url, servlet.newInstance());//通过HashMap存储url和对应servlet实例
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
        });
        int port = 80;
        log.info("开始建立连接");
        new Thread(new NioServerHandler(port)).start();
    }

    private static List<Class<Servlet>> loadClasses() {
        List<Class<Servlet>> servletClassList = new ArrayList<>();
        try {
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("file://"+Context.path+"/WEB-INF/classes/")});
            List<File> fileList = new ArrayList<>();
            scanFiles(fileList,new File(Context.path+"/WEB-INF/classes"));//java生成类文件所在位置
            //解析文件 添加Class<servlet>  到 servletClassList
            fileList.forEach(file -> {
                try {
                    servletClassList.add((Class<Servlet>) urlClassLoader.
                            loadClass(file.getPath()
                                    .substring((Context.path+"/WEB-INF/classes/").length(),
                                            file.getPath().length()-6)
                                    .replaceAll("\\\\",".")));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }finally {
            return servletClassList;
        }
    }

    /**
     * 将文件递归添加到 fileList
     * @param fileList
     * @param file
     */
    private static void scanFiles(List<File> fileList, File file) {
        if(file.isDirectory()){
            File[] files = file.listFiles();//文件夹中 文件数组
            Arrays.stream(files).forEach(f->{
                if(f.isFile())
                    fileList.add(f);
                else scanFiles(fileList,f);
            });
        }
    }

    /**
     * 处理 Class servlet 这个注释会附在Class servlet上
     * @param servlet 文件解析成的class对象
     * @return
     */
    private static String HandlerAnnotation(Class servlet) {
        WebServlet webServlet = (WebServlet) servlet.getAnnotation(WebServlet.class);//class对象的方法 获取注释对象
        return webServlet.urlPattern();//default ""
    }
}
