package com.xupt.spider;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by 梁峻磊 on 2017/8/29.
 */
public class RetrivePage {
    private static HttpClient httpClient = new HttpClient();
    //设置代理服务器
    static {
        httpClient.getHostConfiguration().setProxy(" 172.19.65.249",8080);
    }

    public static  Boolean downloadPage(String path)throws Exception{
        InputStream inputStream = null;
        OutputStream outputStream = null;

        PostMethod postMethod = new PostMethod();
        NameValuePair[] postData = new NameValuePair[2];
        postData[0] = new NameValuePair("name","lietu");
        postData[1] = new NameValuePair("password","******");
        postMethod.addParameters(postData);

        //执行，返回状态码
        int statusCode = httpClient.executeMethod(postMethod);
        if(statusCode == HttpStatus.SC_MULTIPLE_CHOICES || statusCode == HttpStatus.SC_MOVED_PERMANENTLY
                || statusCode == HttpStatus.SC_MOVED_TEMPORARILY || statusCode == HttpStatus.SC_SEE_OTHER
                || statusCode == HttpStatus.SC_TEMPORARY_REDIRECT){
            //读取新的url
            Header header = postMethod.getResponseHeader("location");
            if (header != null){
                String newUrl = header.getValue();
                if (newUrl == null || newUrl.equals("")){
                    newUrl = "/";
                    //使用post转向
                    PostMethod redirect = new PostMethod();
                    //发送请求，做进一步处理。。。。。。
                }
            }
        }
        //这里只处理返回值为200 的状态码
        if (statusCode == HttpStatus.SC_OK){
            inputStream = postMethod.getResponseBodyAsStream();
            //得到文件名
            String filename = path.substring(path.lastIndexOf('/')+1);
            //获得文件输出流
            outputStream = new FileOutputStream(filename);

            int timeByte = -1;
            while((timeByte = inputStream.read()) > 0){
                outputStream.write(timeByte);
            }
            if (inputStream != null){
                inputStream.close();
            }
            if (outputStream != null){
                outputStream.close();
            }

            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            RetrivePage.downloadPage("http://www.lietu.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
