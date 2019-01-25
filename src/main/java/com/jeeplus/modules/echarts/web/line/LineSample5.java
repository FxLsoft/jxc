/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */

package com.jeeplus.modules.echarts.web.line;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.LogAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
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
@RequestMapping(value = "${adminPath}/echarts/line/sample5")
public class LineSample5 {

    @RequestMapping(value = {"index", ""})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("dataURL", "/echarts/line/sample5/option");
        return "modules/common/echarts";
    }

    @ResponseBody
    @RequestMapping("option")
    public GsonOption getOption(){
        GsonOption option = new GsonOption();
        option.title().text("对数轴示例").x(X.center);


        option.legend().x(X.left).data("2的指数", "3的指数");

        option.toolbox().show(true).feature(
                Tool.mark,
                Tool.dataView,
                new MagicType(Magic.line, Magic.bar),
                Tool.restore,
                Tool.saveAsImage);
        option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c}");
        option.calculable(true);

        option.yAxis(new LogAxis());
        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.name("x").splitLine().show(false);
        categoryAxis.data("一", "二", "三", "四", "五", "六", "七", "八", "九");

        option.xAxis(categoryAxis);

        ValueAxis valueAxis = new ValueAxis();
        valueAxis.axisLabel().formatter("{value} °C");
        option.xAxis(valueAxis);

        Line line = new Line("3的指数");
        line.data(1, 3, 9, 27, 81, 247, 741, 2223, 6669);
        Line line2 = new Line("2的指数");
        line2.data(1, 2, 4, 8, 16, 32, 64, 128, 256);
        option.series(line, line2);
        return option;
    }
}
