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
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Scatter;
import com.github.abel533.echarts.style.LineStyle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author jeeplus
 */
@Controller
@RequestMapping(value = "${adminPath}/echarts/scatter/sample2")
public class ScatterSample2 {

    @RequestMapping(value = {"index", ""})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("dataURL", "/echarts/scatter/sample2/option");
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
        option.legend("sin", "cos");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataZoom, Tool.dataView, Tool.restore, Tool.saveAsImage);
        ValueAxis valueAxis = new ValueAxis().power(1).precision(2).scale(true);
        option.xAxis(valueAxis);
        option.yAxis(valueAxis);

        Scatter sin = new Scatter("sin");
        sin.large(true);
        Double[][] sinData = new Double[10000][2];
        for (int i = sinData.length; i > 0; i--) {
            double x = round(Math.random() * 10) - 0;
            double y = Math.sin(x) - x * (i % 2 == 0 ? 0.1 : -0.1) * round(Math.random()) - 0;
            sinData[sinData.length - i] = new Double[]{x, y};
        }
        sin.data(sinData);

        Scatter cos = new Scatter("cos");
        cos.large(true);
        Double[][] cosData = new Double[10000][2];
        for (int i = cosData.length; i > 0; i--) {
            double x = round(Math.random() * 10) - 0;
            double y = Math.cos(x) - x * (i % 2 == 0 ? 0.1 : -0.1) * round(Math.random()) - 0;
            cosData[sinData.length - i] = new Double[]{x, y};
        }
        cos.data(cosData);
        option.series(sin, cos);
        return option;
    }

    public Double round(Double d) {
        BigDecimal bigDecimal = new BigDecimal(d.toString());
        bigDecimal = bigDecimal.round(new MathContext(3, RoundingMode.HALF_UP));
        return bigDecimal.doubleValue();
    }
}
