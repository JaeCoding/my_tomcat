package top.jaecoding.server.api;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 彭文杰
 * @create: 2018-07-05 15:30
 **/
public class Session {
    private Map<Object,Object> values = new HashMap<>();
    public void setAttribute(Object key,Object value){
        this.values.put(key,value);
    }
    public Object getAttribute(Object key){
        return values.get(key);
    }
}
