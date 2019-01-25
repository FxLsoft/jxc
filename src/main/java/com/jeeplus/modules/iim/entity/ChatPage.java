package com.jeeplus.modules.iim.entity;

import com.jeeplus.core.persistence.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/5/18.
 */
public class ChatPage<T> extends Page<T> {
    protected int length = 3;// 显示页面长度
    public ChatPage(HttpServletRequest request, HttpServletResponse response) {
        super(request, response, -2);
    }

    /**
     * 默认输出当前分页标签
     * <div class="page">${page}</div>
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"row\">");
        sb.append("<div class=\"col-sm-12\">");


        sb.append("<div style=\"text-align:center\" class=\"dataTables_paginate paging_simple_numbers\">");

        sb.append("<ul class=\"pagination\">");

        if (pageNo == first) {// 如果是首页
            sb.append("<li class=\"paginate_button previous disabled\"><a href=\"javascript:\">上一页</a></li>\n");
        } else {
            sb.append("<li class=\"paginate_button previous\"><a href=\"javascript:\" onclick=\"" + funcName + "(" + prev + "," + pageSize + ",'" + funcParam + "');\">上一页</a></li>\n");
        }

        int begin = pageNo - (length / 2);

        if (begin < first) {
            begin = first;
        }

        int end = begin + length - 1;

        if (end >= last) {
            end = last;
            begin = end - length + 1;
            if (begin < first) {
                begin = first;
            }
        }

        if (begin > first) {
            int i = 0;
            for (i = first; i < first + slider && i < begin; i++) {
                sb.append("<li class=\"paginate_button \"><a href=\"javascript:\" onclick=\"" + funcName + "(" + i + "," + pageSize + ",'" + funcParam + "');\">"
                        + (i + 1 - first) + "</a></li>\n");
            }
            if (i < begin) {
                sb.append("<li class=\"paginate_button disabled\"><a href=\"javascript:\">...</a></li>\n");
            }
        }

        for (int i = begin; i <= end; i++) {
            if (i == pageNo) {
                sb.append("<li class=\"paginate_button active\"><a href=\"javascript:\">" + (i + 1 - first)
                        + "</a></li>\n");
            } else {
                sb.append("<li class=\"paginate_button \"><a href=\"javascript:\" onclick=\"" + funcName + "(" + i + "," + pageSize + ",'" + funcParam + "');\">"
                        + (i + 1 - first) + "</a></li>\n");
            }
        }

        if (last - end > slider) {
            sb.append("<li class=\"paginate_button disabled\"><a href=\"javascript:\">...</a></li>\n");
            end = last - slider;
        }

        for (int i = end + 1; i <= last; i++) {
            sb.append("<li class=\"paginate_button \"><a href=\"javascript:\" onclick=\"" + funcName + "(" + i + "," + pageSize + ",'" + funcParam + "');\">"
                    + (i + 1 - first) + "</a></li>\n");
        }

        if (pageNo == last) {
            sb.append("<li class=\"paginate_button next disabled\"><a href=\"javascript:\">下一页 </a></li>\n");
        } else {
            sb.append("<li class=\"paginate_button next\"><a href=\"javascript:\" onclick=\"" + funcName + "(" + next + "," + pageSize + ",'" + funcParam + "');\">"
                    + "下一页</a></li>\n");
        }
        sb.append("</ul>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");

        return sb.toString();
    }

}