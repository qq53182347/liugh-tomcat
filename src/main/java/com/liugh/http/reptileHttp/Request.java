package com.liugh.http.reptileHttp;

import java.io.IOException;
import java.io.InputStream;

public class Request {
  private InputStream is;
  private String url;

  public Request(InputStream input) {
    this.is = input;
  }
  public void parse() {
    //从socket中读取一个2048长度字符
    StringBuffer request = new StringBuffer(Response.BUFFER_SIZE);
    int i;
    byte[] buffer = new byte[Response.BUFFER_SIZE];
    try {
      i = is.read(buffer);
    }
    catch (IOException e) {
      e.printStackTrace();
      i = -1;
    }
    for (int j=0; j<i; j++) {
      request.append((char) buffer[j]);
    }
    //打印读取的socket中的内容
    System.out.print(request.toString());
    url = parseUrL(request.toString());
  }

  private String parseUrL(String requestString) {
    int index1, index2;
    index1 = requestString.indexOf(' ');//看socket获取请求头是否有值
    if (index1 != -1) {
      index2 = requestString.indexOf(' ', index1 + 1);
      if (index2 > index1)
        return requestString.substring(index1 + 1, index2);
    }
    return null;
  }

  public String getUrL() {
    return url;
  }

}