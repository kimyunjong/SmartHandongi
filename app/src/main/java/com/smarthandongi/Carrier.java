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
    private int post_id;
    private String group_code = "";
    private String group_name = "";
    private String title = null;
    private String content = null;
    private String big_category = "0";
    private String category = "";
    private String image_name = null;
    private String link = null;
    private String posting_date = null;
    private String start_date = null;
    private String end_date = null;
    private String regid = null;
    private int selector = 0;
    private int edit_count = 0;
    private int has_pic = 0;
    private int push = 0;
    private int group_id;
    //youngmin variable ends


    private String Group_pw = null;
    private String upload_url=null;
    private int fromPostDetail =0;
    private int fromSMPcomment=0;
    private int fromSMP=0;
    private int post_position_num;

    public void setGroup_id(int group_id) {this.group_id=group_id;}

    public int getGroup_id() {return group_id;}

    public void setFromSMPcomment(int fromSMP) {this.fromSMPcomment = fromSMP;}

    public int getFromSMPcomment() {return fromSMPcomment;}

    public void setFromSMP(int fromSMP) {this.fromSMP=fromSMP;}

    public int getFromSMP() {return fromSMP; }

    public void setUpload_url(String url) {upload_url=url;}

    public String getUpload_url() {return upload_url;}

    public boolean isLogged_in() { return logged_in; }

    public void setLogged_in(boolean logged_in) {
        this.logged_in = logged_in;
    }

    public String getNickname() { return nickname;}

    public void setNickname(String nickname) { this.nickname = nickname;}

    public String getId() { return id;}

    public void setId(String id) { this.id = id;}

    public String getGroup_code() { return group_code; }

    public void setGroup_code(String group_code) { this.group_code = group_code; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

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

    public String getRegid() { return regid;}

    public void setRegid(String regid) { this.regid = regid;}

    public String getGroup_pw() { return Group_pw;}

    public void setGroup_pw(String group_pw) { Group_pw = group_pw;}

    public int getSelector() { return selector;}

    public void setSelector(int selector) { this.selector = selector;}

    public String getBig_category() { return big_category;}

    public void setBig_category(String big_category) { this.big_category = big_category;}

    public int getEdit_count() { return edit_count;}

    public void setEdit_count(int edit_count) { this.edit_count = edit_count;}

    public int getHas_pic() { return has_pic;}

    public void setHas_pic(int has_pic) { this.has_pic = has_pic;}

    public int getPost_id() { return post_id;}

    public void setPost_id(int post_id) { this.post_id = post_id;}

    public int getFromPostDetail() { return fromPostDetail;}


    public void setFromPostDetail(int fromPostDetail) { this.fromPostDetail = fromPostDetail;}

    public int getPush() { return push;}

    public void setPush(int push) { this.push = push;}

    public void setPost_position(int num){this.post_position_num=num;}
    public int getPost_position_num(){return post_position_num;}

}
