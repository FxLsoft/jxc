/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */

package com.jeeplus.modules.echarts.web.bar;

import com.github.abel533.echarts.axis.*;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
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
@RequestMapping(value = "${adminPath}/echarts/bar/sample3")
public class BarSample3 {

    @RequestMapping(value = {"index", ""})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("dataURL", "/echarts/bar/sample3/option");
        return "modules/common/echarts";
    }

    @ResponseBody
    @RequestMapping("option")
    public GsonOption getOption(){
        GsonOption option = new GsonOption();
        option.title("ECharts2 vs ECharts1", "Chrome下测试数据");
        option.tooltip(Trigger.axis);
        option.legend(
                "ECharts1 - 2k数据", "ECharts1 - 2w数据", "ECharts1 - 20w数据", "",
                "ECharts2 - 2k数据", "ECharts2 - 2w数据", "ECharts2 - 20w数据");
        option.toolbox().show(true)
                .feature(
                        Tool.mark, Tool.dataView,
                        new MagicType(Magic.line, Magic.bar),
                        Tool.restore, Tool.saveAsImage);
        option.calculable(true);
        option.grid().y(70).y2(30).x2(20);
        option.xAxis(
                new CategoryAxis().data("Line", "Bar", "Scatter", "K", "Map"),
                new CategoryAxis()
                        .axisLine(new AxisLine().show(false))
                        .axisTick(new AxisTick().show(false))
                        .axisLabel(new AxisLabel().show(false))
                        .splitArea(new SplitArea().show(false))
                        .axisLine(new AxisLine().show(false))
                        .data("Line", "Bar", "Scatter", "K", "Map")
        );
        option.yAxis(new ValueAxis().axisLabel(new AxisLabel().formatter("{value} ms")));

        Bar b1 = new Bar("ECharts2 - 2k数据");
        b1.itemStyle().normal().color("rgba(193,35,43,1)").label().show(true);
        b1.data(40, 155, 95, 75, 0);

        Bar b2 = new Bar("ECharts2 - 2w数据");
        b2.itemStyle().normal().color("rgba(181,195,52,1)").label().show(true).textStyle().color("#27727B");
        b2.data(100, 200, 105, 100, 156);

        Bar b3 = new Bar("ECharts2 - 20w数据");
        b3.itemStyle().normal().color("rgba(252,206,16,1)").label().show(true).textStyle().color("#E87C25");
        b3.data(906, 911, 908, 778, 0);

        Bar b4 = new Bar("ECharts1 - 2k数据");
        b4.itemStyle().normal().color("rgba(193,35,43,0.5)").label().show(true).formatter("function(a,b,c){return c>0 ? (c +'\n'):'';}");
        b4.data(96, 224, 164, 124, 0).xAxisIndex(1);

        Bar b5 = new Bar("ECharts1 - 2w数据");
        b5.itemStyle().normal().color("rgba(181,195,52,0.5)").label().show(true);
        b5.data(491, 2035, 389, 955, 347).xAxisIndex(1);

        Bar b6 = new Bar("ECharts1 - 20w数据");
        b6.itemStyle().normal().color("rgba(252,206,16,0.5)").label().show(true).formatter("function(a,b,c){return c>0 ? (c +'+'):'';}");
        b6.data(3000, 3000, 2817, 3000, 0, 1242).xAxisIndex(1);

        option.series(b1, b2, b3, b4, b5, b6);
        return option;
    }
}
