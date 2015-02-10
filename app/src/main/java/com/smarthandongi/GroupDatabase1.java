package com.smarthandongi;

import java.io.Serializable;

/**
 * Created by Administrator on 2015-02-03.
 */
public class GroupDatabase1 implements Serializable {
    public GroupDatabase1(int group_id, String group_name, String group_category, String introduce) {
        this.group_id = group_id;
        this.group_name = group_name;
        this.group_category = group_category;
        this.introduce = introduce;
    //    this.nickname_list=nickname_list;
    }
    //수영 추가


    private String group_category;
    private String introduce;
    private String group_name;
    private int group_id;
  //  private String nickname_list;

    public String getGroup_category(){
        return group_category;
    }
    public void setGroup_category(String group_category){this.group_name= group_category;}

    public String getIntroduce(){
        return introduce;
    }
    public void setIntroduce(String introduce){this.introduce = introduce;}
   public String getGroup_name(){return group_name; }
    public void setGroup_name(String name){this.group_name= group_name;}
   public int getGroup_id(){return group_id;}
    public void setGroup_id(int group_id){
        this.group_id= group_id;
    }
   /* public void setNickname_list(String nickname_list){
        this.nickname_list= nickname_list;
    }
    public String getNickname_list (){
        return nickname_list;
    }*/
}
