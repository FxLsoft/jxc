/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */

package com.jeeplus.modules.echarts.web.other;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.SplitArea;
import com.github.abel533.echarts.axis.ValueAxis;
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
@RequestMapping(value = "${adminPath}/echarts/other/sample")
public class OtherSample {
    @RequestMapping(value = {"index", ""})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("dataURL", "/echarts/other/sample/option");
        return "modules/common/echarts";
    }

    @ResponseBody
    @RequestMapping("option")
    public GsonOption getOption(){
        GsonOption option = new GsonOption();
        option.title("tubiao").animation(false);

        option.xAxis(new CategoryAxis().data("周一", "周二", "周三", "周四", "周五", "周六", "周日"));
        option.yAxis(new ValueAxis().splitArea(new SplitArea().show(true)));

        Line line = new Line("蒸发量");
        line.data(12, 5, 4, 10, 15, 7, 13).smooth(true);

        Line line2 = new Line("降水量");
        line2.data(10, 15, 7, 13, 12, 5, 3);

        option.series(line, line2);
       return option;
    }
}
