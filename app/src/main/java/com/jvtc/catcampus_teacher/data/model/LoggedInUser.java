package com.jvtc.catcampus_teacher.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {
    private String cookie; //教务系统cookie
    private String cookie2; //学工平台cookie
    private String account;
    private String password; //教务系统密码
    private String password2; //学工平台密码

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getCookie2() {
        return cookie2;
    }

    public void setCookie2(String cookie2) {
        this.cookie2 = cookie2;
    }

    @Override
    public String toString() {
        return "LoggedInUser{" +
                "cookie='" + cookie + '\'' +
                ", cookie2='" + cookie2 + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", password2='" + password2 + '\'' +
                '}';
    }
}