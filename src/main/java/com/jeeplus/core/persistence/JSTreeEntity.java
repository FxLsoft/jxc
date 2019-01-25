/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.core.persistence;

import java.util.List;

/**
 * 数据Entity类
 * 
 * @author jeeplus
 * @version 2017-05-16
 */
public abstract class JSTreeEntity<T> extends TreeEntity<T> {

	private static final long serialVersionUID = 1L;

	// private String id;
	private String icon;
	private String text;
	private String type;
	@SuppressWarnings("rawtypes")
	private List<JSTreeEntity> children;
	private State state;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@SuppressWarnings("rawtypes")
	public List<JSTreeEntity> getChildren() {
		return children;
	}

	public void setChildren(@SuppressWarnings("rawtypes") List<JSTreeEntity> children) {
		this.children = children;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}

class State {
	boolean opened; // is the node open
	boolean disabled; // is the node disabled
	boolean selected; // is the node selected

	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}