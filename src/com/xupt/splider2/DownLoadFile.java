package com.xupt.splider2;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.*;

/**
 * @author 梁峻磊
 * 网页下载并处理
 */
public class DownLoadFile {
    /**
     * 根据url和网页类型生成需要保存的网页的文件名，去除url中的非文件名字符
     * @param url
     * @param contentType
     * @return
     */
    public String getFileNameByUrl(String url,String contentType){

        //去除url中的 http://
        url = url.substring(7);
        //text/html 类型
        if (contentType.indexOf("html") != -1){
            url = url.replaceAll("[\\?/:*|<>\"]","_")+".html";
            return url;
        }
        //如PDF类型
        else {
            return url.replaceAll("[\\?/:*|<>\"]","_")+"."
                    +contentType.substring(contentType.lastIndexOf("/")+1);
        }
    }

    /**
     * 保存网页字节数组到本地
     * @param data
     * @param filePath 要保存文件的相对地址
     */
    private void saveToLocal(byte[] data,String filePath) throws IOException {
        DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(filePath)));
        for (int i=0;i<data.length;i++){
            out.write(data[i]);
        }
        out.flush();
        out.close();
    }

    /**
     * 下载url指向的网页
     * @param url
     * @return
     */
    public String downLoadFile(String url){
        String filePath = null;
        //1.
        HttpClient httpClient = new HttpClient();
        //设置http连接超时5S
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        //2.
        GetMethod getMethod = new GetMethod();
        //设置get请求超市5S
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,5000);
        //设置请求重新处理
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler());

        //3.执行HTTP GET 请求
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode == HttpStatus.SC_OK){
                System.out.println("Method failed:" + getMethod.getStatusLine());
                filePath = null;
            }

            //4.处理HTTP响应内容
            byte[] responseBody = getMethod.getResponseBody();
            filePath = "temp\\"+getFileNameByUrl(url,
                    getMethod.getRequestHeader("Content-Type").getValue());
            saveToLocal(responseBody,filePath);
        } catch (HttpException e) {
            System.out.println("Please check your provided http address!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            getMethod.releaseConnection();
        }
        return filePath;
    }

}
