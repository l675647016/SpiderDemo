package com.xupt.spider;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

import java.io.IOException;

/**
 * @author 梁峻磊
 */
public class Demo {
    @Test
    public void test() throws IOException {
        //创建一个客户端，相当于打开一个浏览器
        HttpClient httpClient = new HttpClient();

        //创建一个get方法，相当于输入url
        GetMethod getMethod = new GetMethod("http://www.baidu.com");

        //回车，获取相应状态码
        int statusCode = httpClient.executeMethod(getMethod);

        //创建post方法
        PostMethod postMethod = new PostMethod();

        //使用数组传递参数
        NameValuePair[] postData = new NameValuePair[3];

        postData[0] = new NameValuePair("1","a");
        postData[1] = new NameValuePair("2","b");

        //查看命中情况
        System.out.println("response" +     getMethod.getResponseBodyAsString());

        //释放
        getMethod.releaseConnection();
    }

}
