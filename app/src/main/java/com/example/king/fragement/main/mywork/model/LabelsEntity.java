package com.example.king.fragement.main.mywork.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/19.
 */
public class LabelsEntity implements Serializable{
    private String labelId;
    private String useNum;
    private String line;
    private String name;

    @Override
    public String toString() {
        return "LabelsEntity{" +
                "labelId='" + labelId + '\'' +
                ", useNum='" + useNum + '\'' +
                ", line='" + line + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUseNum() {
        return useNum;
    }

    public void setUseNum(String useNum) {
        this.useNum = useNum;
    }


    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }


}
