package top.jaecoding.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import top.jaecoding.server.servlet.Context;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author: 彭文杰
 * @create: 2017-07-04 12:37
 **/
public class NioServerHandler implements Runnable {
    public static final Log log = LogFactory.getLog(NioServerHandler.class);
    private int port;
    private Selector selector;//能够检测多个NIO通道，并能够 知晓通道是否为诸如读写事件做好准备 的组件,管理多个网络连接
    private ServerSocketChannel serverSocketChannel;//是一个连接到TCP网络套接字的通道

    public NioServerHandler(int port){
        this.port = port;
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);//绑定端口
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        SelectionKey key = null;  //代表SelectableChannel与Selector的注册的令牌。二者之间的桥梁
        log.info("连接已经建立");
        while (true){
            try {
                selector.select(1000);//轮询等待时间
                Set<SelectionKey> keySet = selector.selectedKeys(); //selector目前所选 keyset？
                Iterator<SelectionKey> it = keySet.iterator();
                while (it.hasNext()){
                    key = it.next();
                    it.remove();
                    handlerKey(key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过SelectionKey将 对应的 SocketChannel注册到selector中，
     * @param key
     */
    private void handlerKey(SelectionKey key) {
        if(key.isValid()){
            if(key.isAcceptable()){
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();//根据令牌 取得channel()
                try {
                    SocketChannel sc = ssc.accept();//类似ServerSocket和Socket
                    sc.configureBlocking(false);
                    sc.register(selector,SelectionKey.OP_READ);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(key.isReadable()){
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer requestBuffer = ByteBuffer.allocate(ContextProperties.BUFFER_SIZE);
                try {
                    int req = sc.read(requestBuffer);
                    if(req>0){
                        //contextRequest是个Map，SocketChannel为key，new ArrayList为Value
                        Context.contextRequest.putIfAbsent(System.identityHashCode(sc),new ArrayList<>());
                        requestBuffer.flip();
                        byte[] request = new byte[requestBuffer.remaining()];
                        requestBuffer.get(request);
                        Context.contextRequest.get(System.identityHashCode(sc)).add(request);
                    }
                    if(req<ContextProperties.BUFFER_SIZE){
                        int id = System.identityHashCode(sc);
                        List<byte[]>  completeRequest = Context.contextRequest.get(id);
                        Context.contextRequest.remove(id);
                        Context.es.execute(()-> RequestHandler.process(completeRequest,sc));//线程池执行 详细的RequestHandler
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
