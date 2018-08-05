package top.jaecoding.server;

import java.lang.annotation.*;

/**
 * @author: 彭文杰
 * @create: 2018-07-04 12:12
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebServlet {
    String urlPattern() default "";
}
