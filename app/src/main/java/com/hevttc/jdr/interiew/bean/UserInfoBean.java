package com.hevttc.jdr.interiew.bean;

/**
 * Created by hegeyang on 2017/12/22.
 */

public class UserInfoBean {

    /**
     * id : 1
     * username : jdr
     * password : null
     * nickname : 小九
     * sex : 女
     * birth : null
     * education : null
     * avastar : null
     */

    private int id;
    private String username;
    private String password;
    private String nickname;
    private String sex;
    private String birth;
    private String education;
    private String avastar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getAvastar() {
        return avastar;
    }

    public void setAvastar(String avastar) {
        this.avastar = avastar;
    }
}
