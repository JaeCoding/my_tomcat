package top.jaecoding.server.api;


import lombok.Getter;

/**
 * @author: 彭文杰
 * @create: 2018-07-05 15:18
 **/
public class Cookie {
    @Getter
    private String key;
    @Getter
    private String value;
    public Cookie(String key,String value){
        this.key = key;
        this.value = value;
    }
}
