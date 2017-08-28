package com.xupt.splider.core;

import com.xupt.splider.bean.LinkTypeData;
import com.xupt.splider.rule.Rule;
import com.xupt.splider.rule.RuleException;
import com.xupt.splider.util.TextUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 梁峻磊 on 2017/8/27.
 */
public class ExtractService {
    public static List<LinkTypeData> extract(Rule rule) throws IOException {
        validateRule(rule);
        List<LinkTypeData> datas = new ArrayList<LinkTypeData>();
        LinkTypeData data = null;

        /**
         * 解析url
         */
        String url = rule.getUrl();
        String[] params = rule.getParams();
        String[] values = rule.getValues();
        String resultTagName = rule.getResultTagName();
        int type = rule.getType();
        int requestType = rule.getRequestMoethod();

        Connection connection = Jsoup.connect(url);

        //设置查询参数
        if (params != null){
            for (int i=0;i<params.length;i++){
                connection.data(params[i],values[i]);
            }
        }

        //设置请求类型
        Document document = null;
        switch (requestType){
            case Rule.GET :
                document = connection.timeout(100000).get();
                break;
            case Rule.POST :
                document = connection.timeout(100000).post();
                break;
        }

        //处理返回数据
        Elements results = new Elements();
        switch (type){
            case Rule.CLASS :
                results = document.getElementsByClass(resultTagName);
                break;
            case Rule.ID :
                Element result = document.getElementById(resultTagName);
                results.add(result);
                break;
            case Rule.SELECTION :
                results = document.select(resultTagName);
                break;
                default:
                    //当resultTagName为空时默认去body标签
                    if (TextUtil.isEmpty(resultTagName)){
                        results = document.getElementsByTag(resultTagName);
                    }
        }

        for (Element result:results) {
            Elements links = result.getElementsByTag("a");

            for (Element link:links) {
                String linkHerf = link.attr("href");
                String linkText = link.text();

                data = new LinkTypeData();
                data.setLinkHref(linkHerf);
                data.setLinkText(linkText);

                datas.add(data);
            }
        }
        return datas;
    }

    /**
     * 对传入参数rule进行校验
     * @param rule
     */
    public static void validateRule(Rule rule){
        String url = rule.getUrl();
        if (TextUtil.isEmpty(url)){
            throw new RuleException("url不能为空");
        }
        if (!url.startsWith("http://")){
            throw new RuleException("url格式不正确");
        }
        if (rule.getParams() != null && rule.getValues() != null){
            if(rule.getParams().length != rule.getValues().length){
                throw new RuleException("参数键值对个数不匹配");
            }
        }
    }
}
