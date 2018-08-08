package top.jaecoding.server.servlet;

import java.lang.annotation.*;

/**
 * @author: 彭文杰
 * @create: 2017-07-04 12:12
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebServlet {
    String urlPattern() default "";
}
