package com.xupt;

import java.io.*;

/***
 * @author 梁峻磊
 */
public class Utils {
    public static void wrtieToFile(InputStream inputStream,String path){
        File file = new File(path);
        System.out.println("是否是文件" + file.isFile());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > 0){
                fileOutputStream.write(buffer,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("----文件写入失败----");
        }
    }
    public static String converStreamToString(InputStream inputStream){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append("/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
}
