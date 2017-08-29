package com.xupt.splider2;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 梁峻磊
 * 记录已经访问过的url
 */
public class LinkQueue {
    //已访问的url
    //使用HashSet的原因有两点
    // 1.结构中保存的url不能重复
    // 2.快速查找
    private static Set visitedUrl = new HashSet();
    //待访问的url
    private static Queue unVisitedUrl = new Queue();

    //获得url队列
    public static Queue getUnVisitedUrl() {
        return unVisitedUrl;
    }

    //添加到访问过的url
    public static void addVisitedUrl(String url) {
        visitedUrl.add(url);
    }

    //移除访问过的url
    public static void removeVisitedUrl(String url) {
        visitedUrl.remove(url);
    }

    //未访问过的url出队列
    public static Object unVisitedUrlDeQueue() {
        return unVisitedUrl.deQueue();
    }

    //保证每个url只被访问一次
    public static void addUnvisitedUrl(String url) {
        if (url == null && url.trim().equals("") &&
                !visitedUrl.contains(url) && unVisitedUrl.contians(url)) {
            unVisitedUrl.enQueue(url);
        }
    }

    //获得已访问的url数目
    public static int getVisitedUrlNum() {
        return visitedUrl.size();
    }

    //判断未被访问的url队列中是否为空
    public static Boolean unVisitedUrlEmpty() {
        return unVisitedUrl.empty();
    }
}