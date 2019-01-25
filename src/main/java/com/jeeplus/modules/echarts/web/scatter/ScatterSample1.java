/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */

package com.jeeplus.modules.echarts.web.scatter;

import com.github.abel533.echarts.AxisPointer;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.LineType;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.ScatterData;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Scatter;
import com.github.abel533.echarts.style.LineStyle;
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
@RequestMapping(value = "${adminPath}/echarts/scatter/sample1")
public class ScatterSample1 {

    @RequestMapping(value = {"index", ""})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("dataURL", "/echarts/scatter/sample1/option");
        return "modules/common/echarts";
    }

    @ResponseBody
    @RequestMapping("option")
    public GsonOption getOption(){
        GsonOption option = new GsonOption();
        option.tooltip(new Tooltip()
                .trigger(Trigger.axis)
                .showDelay(0)
                .axisPointer(new AxisPointer().type(PointerType.cross)
                        .lineStyle(new LineStyle()
                                .type(LineType.dashed).width(1))));
        option.legend("scatter1", "scatter2");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataZoom, Tool.dataView, Tool.restore, Tool.saveAsImage);
        ValueAxis valueAxis = new ValueAxis().power(1).splitNumber(4).scale(true);
        option.xAxis(valueAxis);
        option.yAxis(valueAxis);
        //注：这里的结果是一种圆形一种方形，是因为默认不设置形状时，会循环形状数组
        option.series(
                new Scatter("scatter1").symbolSize("function (value){" +
                        "                return Math.round(value[2] / 5);" +
                        "            }").data(randomDataArray())
                , new Scatter("scatter2").symbolSize("function (value){" +
                        "                return Math.round(value[2] / 5);" +
                        "            }").data(randomDataArray()));
       return option;
    }

    private ScatterData[] randomDataArray() {
        ScatterData[] scatters = new ScatterData[100];
        for (int i = 0; i < scatters.length; i++) {
            scatters[i] = new ScatterData(random(), random(), Math.abs(random()));
        }
        return scatters;
    }

    private int random() {
        int i = (int) Math.round(Math.random() * 100);
        return (i * (i % 2 == 0 ? 1 : -1));
    }
}
