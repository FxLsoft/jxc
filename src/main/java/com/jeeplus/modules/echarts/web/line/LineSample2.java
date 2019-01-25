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
import org.junit.Test;
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
@RequestMapping(value = "${adminPath}/echarts/line/sample2")
public class LineSample2 {

    @RequestMapping(value = {"index", ""})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("dataURL", "/echarts/line/sample2/option");
        return "modules/common/echarts";
    }

    @ResponseBody
    @RequestMapping("option")
    public GsonOption getOption(){
        GsonOption option = new GsonOption();
        option.title("某楼盘销售情况", "纯属虚构");
        option.tooltip().trigger(Trigger.axis);
        option.legend("意向", "预购", "成交");
        option.toolbox().show(true).feature(Tool.mark,
                Tool.dataView,
                new MagicType(Magic.line, Magic.bar, Magic.stack, Magic.tiled),
                Tool.restore,
                Tool.saveAsImage).padding(20);
        option.calculable(true);
        option.xAxis(new CategoryAxis().boundaryGap(false).data("周一", "周二", "周三", "周四", "周五", "周六", "周日"));
        option.yAxis(new ValueAxis());

        Line l1 = new Line("成交");
        l1.smooth(true).itemStyle().normal().areaStyle().typeDefault();
        l1.data(10, 12, 21, 54, 260, 830, 710);

        Line l2 = new Line("预购");
        l2.smooth(true).itemStyle().normal().areaStyle().typeDefault();
        l2.data(30, 182, 434, 791, 390, 30, 10);

        Line l3 = new Line("意向");
        l3.smooth(true).itemStyle().normal().areaStyle().typeDefault();
        l3.data(1320, 1132, 601, 234, 120, 90, 20);

        option.series(l1, l2, l3);
        return option;
    }
}
