package com.xupt;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 梁峻磊
 */
public class SimpleSpider {

    private static final int page = 1538;
    public static void main(String[] args) {
        //HttpClient 超时配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD)
                .setConnectionRequestTimeout(12000)
                .setConnectTimeout(12000).build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();
        System.out.println("5s后开始抓取图片");

        for (int i = page;i>1500;i--){
            //创建一个GET请求
            HttpGet httpGet = new HttpGet("http://jandan.net/ooxx/page-" + i);
            httpGet.addHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                            "Chrome/42.0.2311.152 Safari/537.36");
            try {
                Thread.sleep(5000);
                //发送请求，并执行
                CloseableHttpResponse response = httpClient.execute(httpGet);
                InputStream inputStream = response.getEntity().getContent();
                String html = Utils.converStreamToString(inputStream);
                new Thread(new JianDanHtmlParser(html,i)).start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送get请求
     */
    public static void get(){

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            //创建get请求
            HttpGet httpGet = new HttpGet("http://jandan.net/ooxx/page-1538");
            System.out.println("executing request" + httpGet.getURI());
            //执行get请求
            CloseableHttpResponse response = httpClient.execute(httpGet);

            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                System.out.println("----------------------------------");

                // 打印响应状态
                System.out.println(response.getStatusLine());

                if (entity != null){
                    // 打印响应内容长度
                    System.out.println("Response content length:" + entity.getContentLength());
                    // 打印响应内容
                    System.out.println("Response content: " + EntityUtils.toString(entity));
                }
                System.out.println("----------------------------------");
            }finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

