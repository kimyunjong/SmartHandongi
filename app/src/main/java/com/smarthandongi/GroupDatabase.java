package com.smarthandongi;

/**
 * Created by user on 2015-01-27.
 */
public class GroupDatabase {

    public GroupDatabase(int id, String Group_name,String nickname_list, String Group_code) {
        this.id=id;
        this.Group_name=Group_name;
        this.nickname_list=nickname_list;
        this.Group_code=Group_code;
    }

    private int id;
    private String Group_name;
    private String nickname_list;
    private String Group_code;

    public int getId() {return id;}
    public void setId(int id) {this.id=id;}
    public String getGroup_name() {return Group_name;}
    public void setGroup_name(String Group_name){this.Group_name=Group_name;}
    public String getNickname_list(){return nickname_list;}
    public String getGroup_code() { return Group_code;}
    public void setGroup_code(String Group_code) {this.Group_code = Group_code; }

}
