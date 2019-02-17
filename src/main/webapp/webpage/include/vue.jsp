<%@ page contentType="text/html;charset=UTF-8" %>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=9,IE=10" />
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Cache-Control" content="no-store">
<script>var d = "${fns:getConfig('do'.concat('ma').concat('in').concat('.u').concat('rl'))}";</script>
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<!-- 设置浏览器图标 -->
<link rel="shortcut icon" href="${ctxStatic}/favicon.ico">
<link rel="stylesheet" href="${ctxStatic}/common/css/vendor.css" />
<script src="${ctxStatic}/common/js/vendor.js"></script>
<!-- 引入layer插件,当做独立组件使用，不使用layui模块，该版本修复了chrome下花屏的bug -->
<script src="${ctxStatic}/plugin/layui/layer/layer.js"></script>
<script src="${ctxStatic}/plugin/layui/laytpl/laytpl.js"></script>
<script src="${ctxStatic}/common/js/jeeplus.js"></script>
<script>
/*时间格式化
 *使用方式new Date().format(yyyy-MM-dd hh:mm:ss p)
 */
Date.prototype.Format = function(fmt) {
    var monthArray = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
    var weekArray = new Array('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday');
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours() < 13 ? this.getHours() : (this.getHours() - 12), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds(), //毫秒
        "p": this.getHours() > 12 ? "PM" : "AM",
        "E": monthArray[this.getMonth()],
        "W": weekArray[this.getDay()]
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/**
 * 字符串反解析成 时间戳 基于 new Date(0)
 * 与Format 对应
 * Date.parseTime("2019-01-15 04:56:30 656 Jan Tuesday PM", 'yyyy-MM-dd hh:mm:ss S E W')
 * 
 */
Date.parseTime = function(dateStr, fmt) {
    if (Object.prototype.toString.call(dateStr).slice(8, -1) !== 'String') {
        return NaN;
    }
    if (!fmt || Object.prototype.toString.call(fmt).slice(8, -1) !== 'String') {
        return Date.parse(dateStr);
    }
    
    let monthArray = new Array('Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec');
    var weekArray = new Array('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday');
    let regexMap = {
        'd+': {
            regex: '\\d{1,2}',
            value: 'day',
        },
        'M+': {
            regex: '\\d{1,2}',
            value: 'month',
        },
        'h+': {
            regex: '\\d{1,2}',
            value: 'hours',
        },
        'm+': {
            regex: '\\d{1,2}',
            value: 'minutes',
        },
        's+': {
            regex: '\\d{1,2}',
            value: 'seconds',
        },
        'q+': {
            regex: '\\d',
        },
        'S': {
            regex: '\\d{1,4}',
            value: 'milliseconds',
        },
        'p': {
            regex: 'PM|AM',
            value: 'p'
        },
        'y+': {
            regex: '\\d{1,4}',
            value: 'year',
        },
        'E': {
            regex: monthArray.join('|'),
            value: 'eMonth'
        },
        'W': {
            regex: weekArray.join('|'),
            value: 'week'
        },
    }
    let regexStr = fmt;
    for (let key in regexMap) {
        regexStr = regexStr.replace(new RegExp(key, 'g'), function(word, index, str) {
            return '(' + regexMap[key].regex + ')';
        });
    }
    let regex = new RegExp(regexStr, 'i');
    // 如果匹配成功
    if (regex.test(dateStr)) {
        let out = {};
        for (let oKey in regexMap) {
            let regexValue = fmt;
            for (let key in regexMap) {
                regexValue = regexValue.replace(new RegExp(key, 'g'), function(word, index, str) {
                    if (oKey === key) {
                        return '(' + regexMap[key].regex + ')';
                    } else if (regexMap[key].regex.indexOf('|') > -1) {
                        return '[' + regexMap[key].regex + ']+';
                    } else {
                        return regexMap[key].regex;
                    }
                });
            }
            regexValue = new RegExp(regexValue, 'i');
            let matcher = dateStr.match(regexValue)
            if (matcher && matcher[1] && regexMap[oKey].value) {
                out[regexMap[oKey].value] = matcher[1];
            }
            // console.log(oKey, dateStr.match(regexValue));
        }
        if (out.month) {
            out.month = out.month - 1;
        }
        if (out.eMonth) {
            out.month = monthArray.indexOf(out.eMonth);
        }
        if (out.p === 'PM' && date.hours < 12) {
            date.hours = date.hours + 12;
        }
        let date = new Date(out.year || new Date().getFullYear(), +out.month || 0, +out.day || 0, +out.hours || 0, +out.minutes || 0, +out.seconds || 0, +out.milliseconds || 0);
        // console.log(out, date);
        return date.getTime();
    } else {
        return NaN ;
    }
}
</script>
