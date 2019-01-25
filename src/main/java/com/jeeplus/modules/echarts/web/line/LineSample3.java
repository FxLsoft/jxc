/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */

package com.jeeplus.modules.echarts.web.line;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;
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
@RequestMapping(value = "${adminPath}/echarts/line/sample3")
public class LineSample3 {

    @RequestMapping(value = {"index", ""})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("dataURL", "/echarts/line/sample3/option");
        return "modules/common/echarts";
    }

    @ResponseBody
    @RequestMapping("option")
    public GsonOption getOption(){
        GsonOption option = new GsonOption();
        option.legend("高度(km)与气温(°C)变化关系");

        option.toolbox().show(true).feature(
                Tool.mark,
                Tool.dataView,
                new MagicType(Magic.line, Magic.bar),
                Tool.restore,
                Tool.saveAsImage);

        option.calculable(true);
        option.tooltip().trigger(Trigger.axis).formatter("Temperature : <br/>{b}km : {c}°C");

        ValueAxis valueAxis = new ValueAxis();
        valueAxis.axisLabel().formatter("{value} °C");
        option.xAxis(valueAxis);

        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.axisLine().onZero(false);
        categoryAxis.axisLabel().formatter("{value} km");
        categoryAxis.boundaryGap(false);
        categoryAxis.data(0, 10, 20, 30, 40, 50, 60, 70, 80);
        option.yAxis(categoryAxis);

        Line line = new Line();
        line.smooth(true).name("高度(km)与气温(°C)变化关系")
                .data(15, -50, -56.5, -46.5, -22.1, -2.5, -27.7, -55.7, -76.5)
                .itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
        option.series(line);
       return option;
    }
}
