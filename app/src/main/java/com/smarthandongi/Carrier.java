package com.smarthandongi;

import java.io.Serializable;

/**
 * Created by Hyeonmin on 2014-12-27.
 */
public class Carrier implements Serializable {
    private boolean logged_in = false;
    private String nickname = "not_logged_in";
    private String id = "000000";

    //youngmin variable begins
    private String group_code = null;
    private String group_name = null;
    private String title = null;
    private String content = null;
    private int category = 0;
    private String image_name = null;
    private String link = null;
    private String posting_date = null;
    private String start_date = null;
    private String end_date = null;
    private int selector = 0;

    private int push_sports = 0;
    private int push_nightfood = 0;
    private int push_game = 0;


    //youngmin variable ends

    //yoojin variable begins
    private String group_pw = null;
    //yoojin variable ends



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

    public String getGroup_code() { return group_code; }

    public void setGroup_code(String group_code) { this.group_code = group_code; }

    public String getGroup_pw() { return group_pw; }

    public void setGroup_pw(String group_pw) { this.group_pw = group_pw; }

    public int getCategory() { return category; }

    public void setCategory(int category) { this.category = category; }

    public String getTitle() { return title;}

    public void setTitle(String title) { this.title = title;}

    public String getContent() { return content;}

    public void setContent(String body) { this.content = body;}

    public String getImage_name() { return image_name;}

    public void setImage_name(String image_name) { this.image_name = image_name;}

    public String getLink() { return link;}

    public void setLink(String link) { this.link = link;}

    public String getPosting_date() { return posting_date;}

    public void setPosting_date(String posting_date) { this.posting_date = posting_date;}

    public String getStart_date() { return start_date;}

    public void setStart_date(String start_date) { this.start_date = start_date;}

    public String getEnd_date() { return end_date;}

    public void setEnd_date(String end_date) { this.end_date = end_date;}

    public String getGroup_name() { return group_name;}

    public void setGroup_name(String group_name) { this.group_name = group_name;}

    public int getPush_sports() { return push_sports;}

    public void setPush_sports(int push_sports) { this.push_sports = push_sports;}

    public int getPush_nightfood() { return push_nightfood;}

    public void setPush_nightfood(int push_nightfood) { this.push_nightfood = push_nightfood;}

    public int getPush_game() { return push_game;}

    public void setPush_game(int push_game) { this.push_game = push_game;}

    public int getSelector() { return selector;}

    public void setSelector(int selector) { this.selector = selector;}


}
