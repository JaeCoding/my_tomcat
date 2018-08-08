package top.jaecoding.server.api;

import java.util.Date;

/**
 * @author: 彭文杰
 * @create: 2017-07-05 15:20
 *
 * 用于返回 错误信息及请求头
 **/
public class ErrorMessage {
    private int code;
    private String message;

    public ErrorMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public byte[] getBytes() {
        StringBuffer sb = new StringBuffer("HTTP/1.1 "+code+" "+message+"\n");
        sb.append("Date:"+new Date()+"\n");
        sb.append("Content-Length:"+0+"\n\r\n\r\n");
        return sb.toString().getBytes();
    }
}
