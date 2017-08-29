package com.xupt.splider2;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 梁峻磊
 * 提取url
 */
public class HtmlParserTool {

    /**
     * 用来获取一个网站上的链接，filter用来过滤链接
     * @param url
     * @param filter
     * @return
     */
    public static Set<String> extracLinks(String url,LinkFilter filter) throws ParserException {
        Set<String> links = new HashSet<String>();

        Parser parser = null;
        try {
            parser = new Parser(url);
            parser.setEncoding("gb2312");
        } catch (ParserException e) {
            e.printStackTrace();
        }

        //过滤<frame>标签的filter，用来提取frame标签中的src属性
        NodeFilter frameFilter = new NodeFilter() {
            @Override
            public boolean accept(Node node) {
                if (node.getText().startsWith("frame src=")){
                    return true;
                }else {
                    return false;
                }
            }
        };
        //OrFilter来设置过滤<a>和<frame>标签
        OrFilter linkFilter = new OrFilter(new NodeClassFilter(LinkTag.class),frameFilter);
        //得到所有  经过过滤的标签
        NodeList nodeList = parser.extractAllNodesThatMatch(linkFilter);
        for (int i=0;i<nodeList.size();i++){
            Node tag = nodeList.elementAt(i);
            if (tag instanceof LinkTag){ //<a>标签
                LinkTag link = (LinkTag) tag;
                String linkUrl = link.getLink(); //url
                if (filter.accpet(linkUrl)){
                    links.add(linkUrl);
                }
            }else { // <frame> 标签
                String frame = tag.getText();
                int start = frame.indexOf("src=");
                frame = frame.substring(start);
                int end = frame.indexOf(" ");
                if (end == -1){
                    end = frame.indexOf(">");
                }
                String frameUrl = frame.substring(5,end-1);
                if (filter.accpet(frameUrl)){
                    links.add(frameUrl);
                }
            }
        }
        return links;
    }
}
