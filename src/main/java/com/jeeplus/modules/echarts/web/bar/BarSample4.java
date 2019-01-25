/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */


package com.jeeplus.modules.echarts.web.bar;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
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
@RequestMapping(value = "${adminPath}/echarts/bar/sample4")
public class BarSample4 {

    @RequestMapping(value = {"index", ""})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("dataURL", "/echarts/bar/sample4/option");
        return "modules/common/echarts";
    }

    @ResponseBody
    @RequestMapping("option")
    public GsonOption getOption(){
        GsonOption option = new GsonOption();
        option.title().text("ECharts例子个数统计").subtext("Rainbow bar example")
            .link("http://echarts.baidu.com/doc/example.html").x(X.center);
        option.tooltip().trigger(Trigger.item);
        option.calculable(true);
        option.grid().borderWidth(0).y(80).y2(60);
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true), Tool.restore, Tool.saveAsImage);
        option.xAxis(new CategoryAxis().data("Line", "Bar", "Scatter", "K", "Pie", "Radar", "Chord", "Force", "Map", "Gauge", "Funnel"));
        option.yAxis(new ValueAxis().show(false));

        Bar bar = new Bar("ECharts例子个数统计");
        bar.itemStyle().normal().color("function(params) {" +
                "                        var colorList = [" +
                "                          '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B'," +
                "                           '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD'," +
                "                           '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'" +
                "                        ];" +
                "                        return colorList[params.dataIndex]" +
                "                    }")
                .label().show(true).position(Position.top).formatter("{b}\n{c}");
        bar.data(12,21,10,4,12,5,6,5,25,23,7);

        option.series(bar);
        return option;
    }
}
