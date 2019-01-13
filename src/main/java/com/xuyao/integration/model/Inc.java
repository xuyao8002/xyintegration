package com.xuyao.integration.model;

public class Inc {
    private Integer id;

    private String type;

    private Integer num;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Inc{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", num=" + num +
                '}';
    }
}