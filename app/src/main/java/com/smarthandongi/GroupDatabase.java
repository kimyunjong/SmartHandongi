package com.smarthandongi;

/**
 * Created by user on 2015-01-27.
 */
public class GroupDatabase {

    public GroupDatabase(int id, String Group_name,String nickname_list) {
        this.id=id;
        this.Group_name=Group_name;
        this.nickname_list=nickname_list;
    }

    private int id;
    private String Group_name;
    private String nickname_list;

    public int getId() {return id;}
    public void setId(int id) {this.id=id;}
    public String getGroup_name() {return Group_name;}
    public void setGroup_name(String Group_name){this.Group_name=Group_name;}
    public String getNickname_list(){return nickname_list;}

}
