/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */


package com.jeeplus.modules.echarts.web.gauge;

import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Gauge;
import com.github.abel533.echarts.series.gauge.Detail;
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
@RequestMapping(value = "${adminPath}/echarts/gauge/sample")
public class GaugeSample {

    @RequestMapping(value = {"index", ""})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("dataURL", "/echarts/gauge/sample/option");
        return "modules/common/echarts";
    }

    @ResponseBody
    @RequestMapping("option")
    public GsonOption getOption(){
        GsonOption option = new GsonOption();
        option.tooltip().formatter("{a} <br/>{b} : {c}%");
        option.toolbox().show(true).feature(Tool.mark, Tool.restore, Tool.saveAsImage);
        option.series(new Gauge("业务指标").detail(new Detail().formatter("{value}%")).data(new Data("完成率", 75)));
        return option;
    }
}
