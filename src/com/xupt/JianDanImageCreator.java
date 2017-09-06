package com.xupt;

import java.io.*;
import java.net.URL;

/**
 * @author 梁峻磊
 */
public class JianDanImageCreator implements Runnable {

    private static int count = 0;
    private String imageUrl;
    private int page;
    private static final String basePath = "E;/jiandan";

    public JianDanImageCreator(String imageUrl, int page) {
        this.imageUrl = imageUrl;
        this.page = page;
    }

    @Override
    public void run() {
        File dir = new File(basePath);
        if (!dir.exists()){
            dir.mkdirs();
            System.out.println("图片存放与" + basePath + "于目录下");
        }
        String imageName = imageUrl.substring(imageUrl.lastIndexOf("/")+1);

        try {
            File file = new File(basePath + "/" + page + "- -" + imageName);
            OutputStream outputStream = new FileOutputStream(file);
            URL url = new URL(imageUrl);
            InputStream inputStream = url.openStream();
            byte[] buff = new byte[1024];
            while (true){
                int readed = inputStream.read(buff);
                if (readed == -1){
                    break;
                }
                byte[] temp = new byte[readed];
                System.arraycopy(buff,0,temp,0,readed);
                outputStream.write(temp);
            }
            System.out.println("第" + (count++) + "张图片"+file.getAbsolutePath());
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
