/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */

package com.jeeplus.modules.echarts.web.pie;

import com.github.abel533.echarts.Label;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.code.Y;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.style.ItemStyle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jeeplus
 */
@Controller
@RequestMapping(value = "${adminPath}/echarts/pie/sample1")
public class PieSample1 {

    @RequestMapping(value = {"index", ""})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("dataURL", "/echarts/pie/sample1/option");
        return "modules/common/echarts";
    }

    @ResponseBody
    @RequestMapping("option")
    public GsonOption getOption(){
        GsonOption option = new GsonOption();
        ItemStyle dataStyle = new ItemStyle();
        dataStyle.normal().label(new Label().show(false)).labelLine().show(false);

        ItemStyle placeHolderStyle = new ItemStyle();
        placeHolderStyle.normal().color("rgba(0,0,0,0)").label(new Label().show(false)).labelLine().show(false);
        placeHolderStyle.emphasis().color("rgba(0,0,0,0)");

        option.title().text("你幸福吗？")
                .subtext("From ExcelHome")
                .sublink("http://e.weibo.com/1341556070/AhQXtjbqh")
                .x(X.center)
                .y(Y.center)
                .itemGap(20)
                .textStyle().color("rgba(30,144,255,0.8)")
                .fontFamily("微软雅黑")
                .fontSize(35)
                .fontWeight("bolder");
        option.tooltip().show(true).formatter("{a} <br/>{b} : {c} ({d}%)");
        option.legend().orient(Orient.vertical)
                .x("(function(){return document.getElementById('main').offsetWidth / 2;})()")
                .y(56)
                .itemGap(12)
                .data("68%的人表示过的不错", "29%的人表示生活压力很大", "3%的人表示“我姓曾”");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage);

        Pie p1 = new Pie("1");
        p1.clockWise(false).radius(125, 150).itemStyle(dataStyle)
                .data(new Data("68%的人表示过的不错", 68), new Data("invisible", 32).itemStyle(placeHolderStyle));

        Pie p2 = new Pie("2");
        p2.clockWise(false).radius(100, 125).itemStyle(dataStyle)
                .data(new Data("29%的人表示生活压力很大", 29), new Data("invisible", 71).itemStyle(placeHolderStyle));

        Pie p3 = new Pie("3");
        p3.clockWise(false).radius(75, 100).itemStyle(dataStyle)
                .data(new Data("3%的人表示“我姓曾”", 3), new Data("invisible", 97).itemStyle(placeHolderStyle));

        option.series(p1, p2, p3);
        return option;
    }
}
