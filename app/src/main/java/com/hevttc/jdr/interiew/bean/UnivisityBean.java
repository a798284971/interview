package com.hevttc.jdr.interiew.bean;

/**
 * Created by hegeyang on 2018/4/28.
 */

public class UnivisityBean {

    /**
     * id : 1520
     * name : 河北科技大学
     * website : http://www.hebust.edu.cn/
     * provinceId : 3
     * level : 本科
     * abbreviation : hebust
     * city : 石家庄
     */

    private int id;
    private String name;
    private String website;
    private int provinceId;
    private String level;
    private String abbreviation;
    private String city;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
