package com.xupt.splider2;

import java.util.LinkedList;

/**
 * @author 梁峻磊
 * 队列，保存将要访问的url
 */
public class Queue {
    //使用链表实现队列
    private LinkedList queue = new LinkedList();
    //入队
    public void enQueue(Object o){
        queue.addLast(o);
    }
    //出队
    public Object deQueue(){
        return queue.removeFirst();
    }
    //判断队列是否为空
    public Boolean isQueueEmpty(){
        return queue.isEmpty();
    }
    //判断队列是否包含object
    public Boolean contians(Object o){
        return queue.contains(o);
    }
    public Boolean empty(){
        return queue.isEmpty();
    }
}
