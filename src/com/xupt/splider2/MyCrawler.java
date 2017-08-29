package com.xupt.splider2;

import org.htmlparser.util.ParserException;

import java.util.Set;

/**
 * @author 梁峻磊
 * 宽度爬虫主程序
 */
public class MyCrawler {
    /**
     * 使用种子初始化url队列
     * @param seeds
     */
    private void initCrawlerWithSeeds(String[] seeds){
        for (int i=0;i<seeds.length;i++) {
            LinkQueue.addUnvisitedUrl(seeds[i]);
        }
    }

    public void crawling(String[] seeds) throws ParserException {
        //定义过滤器，提取以http://www.lietu.com开头的链接
        LinkFilter filter = new LinkFilter(){
          public boolean accpet(String url){
              if (url.startsWith("http://www.lietu.com")){
                  return true;
              }else {
                  return false;
              }
          }
        };
        //初始化url队列
        initCrawlerWithSeeds(seeds);

        //循环条件：待抓取的链接不为空且抓取的网页不多于1000
        while (!LinkQueue.unVisitedUrlEmpty() && LinkQueue.getVisitedUrlNum()<=1000){
            //对头url出队列
            String visitedUrl = (String) LinkQueue.unVisitedUrlDeQueue();
            if (visitedUrl == null)
                continue;
            DownLoadFile downLoadFile = new DownLoadFile();
            //下载网页
            downLoadFile.downLoadFile(visitedUrl);
            //该url放入已访问的url中
            LinkQueue.addVisitedUrl(visitedUrl);
            //提取出下载网页中的url
            Set<String> links = HtmlParserTool.extracLinks(visitedUrl,filter);
            //新的未访问的url入队
            for (String link:links) {
                LinkQueue.addUnvisitedUrl(link);
            }
        }
    }

    public static void main(String[] args) throws ParserException {
        MyCrawler crawler = new MyCrawler();
        crawler.crawling(new String[]{"http://www.leitu.com"});
    }
}
