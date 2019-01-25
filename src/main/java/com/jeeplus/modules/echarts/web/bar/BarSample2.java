/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */

package com.jeeplus.modules.echarts.web.bar;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.PointerType;
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
@RequestMapping(value = "${adminPath}/echarts/bar/sample2")
public class BarSample2 {

    @RequestMapping(value = {"index", ""})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("dataURL", "/echarts/bar/sample2/option");
        return "modules/common/echarts";
    }

    @ResponseBody
    @RequestMapping("option")
    public GsonOption getOption(){
        GsonOption option = new GsonOption();
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        option.legend("直接访问", "邮件营销", "联盟广告", "视频广告", "搜索引擎");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true), Tool.restore, Tool.saveAsImage);
        option.calculable(true);
        option.yAxis(new CategoryAxis().data("周一", "周二", "周三", "周四", "周五", "周六", "周日"));
        option.xAxis(new ValueAxis());

        Bar bar = new Bar("直接访问");
        bar.stack("总量");
        bar.itemStyle().normal().label().show(true).position("insideRight");
        bar.data(320, 302, 301, 334, 390, 330, 320);

        Bar bar2 = new Bar("邮件营销");
        bar2.stack("总量");
        bar2.itemStyle().normal().label().show(true).position("insideRight");
        bar2.data(320, 302, 301, 334, 390, 330, 320);

        Bar bar3 = new Bar("联盟广告");
        bar3.stack("总量");
        bar3.itemStyle().normal().label().show(true).position("insideRight");
        bar3.data(120, 132, 101, 134, 90, 230, 210);

        Bar bar4 = new Bar("视频广告");
        bar4.stack("总量");
        bar4.itemStyle().normal().label().show(true).position("insideRight");
        bar4.data(150, 212, 201, 154, 190, 330, 410);

        Bar bar5 = new Bar("搜索引擎");
        bar5.stack("总量");
        bar5.itemStyle().normal().label().show(true).position("insideRight");
        bar5.data(820, 832, 901, 934, 1290, 1330, 1320);

        option.series(bar, bar2, bar3, bar4, bar5);
        return option;
    }
}
