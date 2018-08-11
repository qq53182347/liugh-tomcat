package com.liugh.http.reptileHttp;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class HttpServer {
  // 用于判断是否需要关闭容器
  private boolean shutdown = false;
  
  public void acceptWait() {
    ServerSocket serverSocket = null;
    try {
    	//端口号，最大链接数，ip地址
      serverSocket = new ServerSocket(8080, 1, InetAddress.getByName("127.0.0.1"));
    }
    catch (IOException e) {e.printStackTrace();System.exit(1); }
    // 等待用户发请求
    while (!shutdown) {
      try {
    	Socket socket = serverSocket.accept();
    	InputStream is = socket.getInputStream();
    	OutputStream  os = socket.getOutputStream();
        // 接受请求参数
        Request request = new Request(is);
        request.parse();
        // 创建用于返回浏览器的对象
        Response response = new Response(os);
        response.setRequest(request);
        response.sendStaticResource();
        //关闭一次请求的socket,因为http请求就是采用短连接的方式
        socket.close();
        //如果请求地址是/shutdown  则关闭容器
        if(null != request){
        	 shutdown = request.getUrL().equals("/shutdown");
        }
      }
      catch (Exception e) {e.printStackTrace();continue;}
    }
  }
  public static void main(String[] args) {
	    HttpServer server = new HttpServer();
	    server.acceptWait();
  }
}
