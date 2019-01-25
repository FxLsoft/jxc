/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */

package com.jeeplus.modules.echarts.web.scatter;

import com.github.abel533.echarts.axis.TimeAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Scatter;
import org.junit.Ignore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jeeplus
 */
@Ignore("由于时间轴data中的时间必须是时间类型，这里由于只能生成字符串，所以会没有效果，解决办法就是在js中将日期字符串转化为日期类型")
@Controller
@RequestMapping(value = "${adminPath}/echarts/scatter/sample3")
public class ScatterSample3 {

    @RequestMapping(value = {"index", ""})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("dataURL", "/echarts/scatter/sample3/option");
        return "modules/common/echarts";
    }

    @ResponseBody
    @RequestMapping("option")
    public GsonOption getOption(){
        GsonOption option = new GsonOption();
        option.title("时间坐标散点图", "dataZoom支持");
        option.tooltip().trigger(Trigger.axis).axisPointer()
                .show(true)
                .type(PointerType.cross).lineStyle().type(LineType.dashed).width(1);
        option.legend("series1");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage);
        option.dataZoomNew().show(true).start(30).end(70);
        option.dataRange().min(0).max(100).orient(Orient.horizontal).x(30).y(Y.center).color("lightgreen", "orange").splitNumber(5);
        option.grid().y2(80);
        option.xAxis(new TimeAxis().splitNumber(10));
        option.yAxis(new ValueAxis());
        option.animation(false);

        Scatter series1 = new Scatter("series1");
        series1.tooltip().formatter("function(params){" +
                "                    var date = new Date(params.value[0]);" +
                "                    return params.seriesName " +
                "                           + ' （'" +
                "                           + date.getFullYear() + '-'" +
                "                           + (date.getMonth() + 1) + '-'" +
                "                           + date.getDate() + ' '" +
                "                           + date.getHours() + ':'" +
                "                           + date.getMinutes()" +
                "                           +  '）<br/>'" +
                "                           + params.value[1] + ', ' " +
                "                           + params.value[2];" +
                "                }");
        series1.symbolSize("function (value){" +
                "                return Math.round(value[2]/10);" +
                "            }");
        series1.data(getData().toArray());

        option.series(series1);
       return option;
    }

    public List<Object[]> getData(){
        List<Object[]> dataList = new ArrayList<Object[]>(1500);
        for (int i = 0; i < 1500; i++) {
            dataList.add(new Object[]{getDateStr(new Date(114,9,1,0,(int)Math.round(Math.random()*10000))),
                            (int)(round(Math.random()*30) - 0),
                            (int)(round(Math.random()*100) - 0)
            });
        }
        return dataList;
    }

    public String getDateStr(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    public Double round(Double d) {
        BigDecimal bigDecimal = new BigDecimal(d.toString());
        bigDecimal = bigDecimal.round(new MathContext(2, RoundingMode.HALF_UP));
        return bigDecimal.doubleValue();
    }
}
