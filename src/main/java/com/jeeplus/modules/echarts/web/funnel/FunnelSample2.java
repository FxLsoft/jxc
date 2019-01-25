/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */

package com.jeeplus.modules.echarts.web.funnel;

import com.github.abel533.echarts.Label;
import com.github.abel533.echarts.LabelLine;
import com.github.abel533.echarts.code.Position;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Funnel;
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
@RequestMapping(value = "${adminPath}/echarts/funnel/sample2")
public class FunnelSample2{
    @RequestMapping(value = {"index", ""})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("dataURL", "/echarts/funnel/sample2/option");
        return "modules/common/echarts";
    }

    @ResponseBody
    @RequestMapping("option")
    public GsonOption getOption(){
        GsonOption option = new GsonOption();
        option.color("rgba(255, 69, 0, 0.5)",
                "rgba(255, 150, 0, 0.5)",
                "rgba(255, 200, 0, 0.5)",
                "rgba(155, 200, 50, 0.5)",
                "rgba(55, 200, 100, 0.5)");
        option.title().text("漏斗图").subtext("纯属虚构");
        option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c}%");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage);
        option.legend("展现", "点击", "访问", "咨询", "订单");
        option.calculable(true);

        Funnel funnel = new Funnel("预期");
        funnel.x("10%").y(60).width("80%");
        funnel.itemStyle().normal().label(new Label().formatter("{b}预期")).
                labelLine(new LabelLine().show(false));
        funnel.itemStyle().emphasis().label(new Label().formatter("{b}预期 : {c}%").position(Position.inside)).
                labelLine(new LabelLine().show(true));

        funnel.data(new Data().value(60).name("访问"),
                new Data().value(40).name("咨询"),
                new Data().value(20).name("订单"),
                new Data().value(80).name("点击"),
                new Data().value(100).name("展现")
        );

        Funnel funnel2 = new Funnel("实际");
        funnel2.x("10%").y(60).width("80%").maxSize("80%");
        funnel2.itemStyle().normal().label(new Label().formatter("{c}%").position(Position.inside).textStyle(new TextStyle().color("#fff"))).
                borderColor("#fff").borderWidth(2);
        funnel2.itemStyle().emphasis().label(new Label().formatter("{b}实际 : {c}%").position(Position.inside)).
                labelLine(new LabelLine().show(true));

        funnel2.data(new Data().value(30).name("访问"),
                new Data().value(10).name("咨询"),
                new Data().value(5).name("订单"),
                new Data().value(50).name("点击"),
                new Data().value(80).name("展现")
        );

        option.series(funnel,funnel2);
        return option;
    }
}
