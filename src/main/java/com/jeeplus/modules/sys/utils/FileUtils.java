/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.utils;

import com.google.common.collect.Lists;
import com.jeeplus.core.mapper.JsonMapper;
import com.jeeplus.modules.sys.entity.FileData;

import java.io.File;
import java.util.List;

/**
 * Created by 刘高峰 on 2018/3/18.
 */
public class FileUtils {
    public static List<FileData> getFileList(File[] files) {
        List fileDataList = Lists.newArrayList();
        if (files != null) {
            for (File file : files) {
                FileData fileData = new FileData();
                if (file.isDirectory()) { // 判断是文件还是文件夹
                    fileData.setId(file.getName());
                    fileData.setType("folder");
                    fileData.setOpen(true);
                    fileData.setValue(file.getName());
                    fileData.setData(getFileList(file.listFiles()));
                } else  { // 判断文件名是否以.avi结尾
                    fileData.setId(file.getName());
                    fileData.setType("file");
                    fileData.setSize(String.valueOf(file.getTotalSpace()));
                    fileData.setValue(file.getName());
                }
                fileDataList.add(fileData);
            }

        }
        return fileDataList;
    }
    public static void main(String[] args){
        File dir = new File("D:\\jeeplus\\jeeplus-ani-menu-big\\jeeplus-maven\\target\\jeeplus\\userfiles");
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        List<FileData> a = getFileList(files);
       System.out.print(JsonMapper.toJsonString(a));
    }
}
