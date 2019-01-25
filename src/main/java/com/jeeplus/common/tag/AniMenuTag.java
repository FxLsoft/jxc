package com.jeeplus.common.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.sys.entity.Menu;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 
 * 类描述：菜单标签
 * 
 * 刘高峰
 * 
 * @date： 日期：2015-1-23 时间：上午10:17:45
 * 
 * @version 1.0
 */
public class AniMenuTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	protected Menu menu;// 菜单Map

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		try {
			JspWriter out = this.pageContext.getOut();
			String menu = (String) this.pageContext.getSession().getAttribute("menu");
			if (menu != null) {
				out.print(menu);
			} else {
				menu = end().toString();
				out.print(menu);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public StringBuffer end() {
		StringBuffer sb = new StringBuffer();
		sb.append(getChildOfTree(menu, 0, UserUtils.getMenuList()));

		return sb;

	}

	private static String getChildOfTree(Menu menuItem, int level, List<Menu> menuList) {
		StringBuffer menuString = new StringBuffer();
		String href = "";
		if (!menuItem.hasPermisson())
			return "";
		if (level > 0) {// level 为0是功能菜单

			ServletContext context = SpringContextHolder.getBean(ServletContext.class);
			if (menuItem.getHref() != null && menuItem.getHref().length() > 0) {// 如果是子节点

				if (menuItem.getHref().startsWith("http://") || menuItem.getHref().startsWith("https://")) {// 如果是互联网资源
					href = menuItem.getHref();
				} else if (menuItem.getHref().endsWith(".html")) {// 如果是静态资源并且不是ckfinder.html，直接访问不加adminPath
					href = context.getContextPath() + menuItem.getHref();
				} else {
					href = context.getContextPath() + Global.getAdminPath() + menuItem.getHref();
				}
				if(menuItem.getTarget()!=null && !menuItem.getTarget().equals("")){
					menuString.append("<li><a class=\"J_menuItem\"  href=\"" + href +"\" target=\""+menuItem.getTarget()+"\" "+ "><i class=\"fa " + menuItem.getIcon() + "\"></i>&nbsp;&nbsp;"
							+ menuItem.getName() + "</a></li>\n");
				}else{
					menuString.append("<li><a class=\"J_menuItem\" href=\"" + href + "\"><i class=\"fa " + menuItem.getIcon() + "\"></i>&nbsp;&nbsp;"
							+ menuItem.getName() + "</a></li>\n");
				}
				
			}
		}

		if ((menuItem.getHref() == null || menuItem.getHref().trim().equals("")) && menuItem.getIsShow().equals("1")) {// 如果是父节点且显示
			if (level == 0) {// 如果是功能菜单
				for (Menu child : menuList) {
					if (child.getParentId().equals(menuItem.getId()) && child.getIsShow().equals("1")) {
						menuString.append(getChildOfTree(child, level + 1, menuList));
					}
				}
			}

			if (level > 0) {// 不是功能菜单
				menuString.append("<li class=\"panel\">\n");
				menuString.append("<a  data-toggle=\"collapse\" data-parent=\"#"+menuItem.getParentId()+"\" class=\"collapsed\" href=\"#" + menuItem.getId()
						+ "\"><i class=\"fa " + menuItem.getIcon() + "\"></i>&nbsp;&nbsp;"
						+ menuItem.getName() + "<span class=\"pull-right fa fa-angle-toggle\"></span></a>\n");
				menuString.append(
						"<ul id=\"" + menuItem.getId() + "\"" + " class=\"nav collapse\">\n");

				for (Menu child : menuList) {
					if (child.getParentId().equals(menuItem.getId()) && child.getIsShow().equals("1")) {
						menuString.append(getChildOfTree(child, level + 1, menuList));
					}
				}
				menuString.append("</ul>\n");
				menuString.append("</li>\n");
			}

		}

		return menuString.toString();
	}

}
