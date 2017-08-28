package com.xupt.splider.test;

import com.xupt.splider.bean.LinkTypeData;
import com.xupt.splider.core.ExtractService;
import com.xupt.splider.rule.Rule;

import java.io.IOException;
import java.util.List;

/**
 * Created by 梁峻磊 on 2017/8/27.
 */
public class Test {

    @org.junit.Test
    public void getDatasByClass() throws IOException {
        Rule rule = new Rule(
                "http://localhost:8080/springmvc_mybatis/items/editItems.action?id=2",
                new String[] { "query.enterprisename","query.registationnumber" }, new String[] { "兴网","" },
                "cont_right", Rule.CLASS, Rule.POST);
        List<LinkTypeData> extracts = ExtractService.extract(rule);
        sout(extracts);
    }
    @org.junit.Test
    public void getDatasByCssQuery() throws IOException {
        Rule rule = new Rule("http://www.11315.com/search",
                new String[] { "name" }, new String[] { "兴网" },
                "div.g-mn div.con-model", Rule.SELECTION, Rule.GET);
        List<LinkTypeData> extracts = ExtractService.extract(rule);
        sout(extracts);
    }

    public void sout(List<LinkTypeData> datas)
    {
        for (LinkTypeData data : datas)
        {
            System.out.println(data.getLinkText());
            System.out.println(data.getLinkHref());
            System.out.println("***********************************");
        }

    }
}
