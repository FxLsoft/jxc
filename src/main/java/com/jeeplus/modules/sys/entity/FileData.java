/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;

import com.google.common.collect.Lists;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;

/**
 * Created by 刘高峰 on 2018/3/18.
 */
public class FileData {
    private String id;
    private String value;
    private  boolean open;
    private String type;
    private Long date;
    private String icon;
    private String size;
    private String pId;
    private List<FileData> data = Lists.newArrayList();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public List<FileData> getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }


}
