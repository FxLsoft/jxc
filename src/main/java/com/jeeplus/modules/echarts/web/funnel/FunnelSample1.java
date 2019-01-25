/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */

package com.jeeplus.modules.echarts.web.funnel;

import com.github.abel533.echarts.Label;
import com.github.abel533.echarts.LabelLine;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Funnel;
import com.github.abel533.echarts.style.LineStyle;
import com.github.abel533.echarts.style.TextStyle;
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
@RequestMapping(value = "${adminPath}/echarts/funnel/sample1")
public class FunnelSample1 {

    @RequestMapping(value = {"index", ""})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("dataURL", "/echarts/funnel/sample1/option");
        return "modules/common/echarts";
    }

    @ResponseBody
    @RequestMapping("option")
    public GsonOption getOption(){
        GsonOption option = new GsonOption();
        option.title().text("漏斗图").subtext("纯属虚构");
        option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c}%");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage);
        option.legend("展现", "点击", "访问", "咨询", "订单");
        option.calculable(true);

        Funnel funnel = new Funnel("漏斗图");
        funnel.x("10%").y(60).width("80%").
                min(0).max(100).
                minSize("0%").maxSize("100%").
                sort(Sort.descending).
                gap(10);
        funnel.itemStyle().normal().borderColor("#fff").borderWidth(1).
                label(new Label().show(true).position(Position.inside)).
                labelLine(new LabelLine().show(false).length(10).lineStyle(new LineStyle().width(1).type(LineType.solid)));
        funnel.itemStyle().emphasis().borderColor("red").borderWidth(5).
                label(new Label().show(true).formatter("{b}:{c}%").textStyle(new TextStyle().fontSize(20))).
                labelLine(new LabelLine().show(true));

        funnel.data(new Data().value(60).name("访问"),
                new Data().value(40).name("咨询"),
                new Data().value(20).name("订单"),
                new Data().value(80).name("点击"),
                new Data().value(100).name("展现")
        );

        option.series(funnel);
        return option;
    }
}
