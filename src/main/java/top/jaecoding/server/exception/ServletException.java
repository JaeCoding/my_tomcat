package top.jaecoding.server.exception;


/**
 * @author: 彭文杰
 * @create: 2017-07-05 19:39
 **/
public class ServletException extends Exception {
    private int code;
    private String message;
    public ServletException(int code, String message){
        this.code = code;
        this.message = message;
    }
    public String get(){
        return code+" "+message;
    }
}
