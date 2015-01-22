package com.smarthandongi;

import java.io.Serializable;

/**
 * Created by Hyeonmin on 2014-12-27.
 */
public class Carrier implements Serializable {
    private boolean logged_in = false;
    private String nickname = "not_logged_in";
    private String id = "000000";

    public boolean isLogged_in() {
        return logged_in;
    }

    public void setLogged_in(boolean logged_in) {
        this.logged_in = logged_in;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




}
