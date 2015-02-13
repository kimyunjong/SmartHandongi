package com.smarthandongi.database;

import java.io.Serializable;

/**
 * Created by Joel on 2015-01-22.
 */
public class PostDatabase implements Serializable{
    public PostDatabase(String title, int id, String kakao_id, String big_category,String category, String group,
                        String content, String posting_date, String link, String start_date, String end_date,
                        String has_pic,String like,int view_num, String group_name,String kakao_nick,int push)
    {

        this.title=title;
        this.id=id;
        this.kakao_id=kakao_id;
        this.category=category;
        this.content=content;
        this.posting_date=posting_date;
        this.link=link;
        this.start_date=start_date;
        this.end_date=end_date;
        this.has_pic=has_pic;
        this.group=group;
        this.like=like;
        this.big_category=big_category;
        this.view_num=view_num;
        this.group_name=group_name;
        this.kakao_nick=kakao_nick;
        this.push=push;
}
    private String title;
    private int id;
    private String kakao_id;
    private String category;
    private String group;
    private String content;
    private String posting_date;
    private String link;
    private String start_date;
    private String end_date;
    private String has_pic;
    private int dday;
    private boolean first_day=false;
    private boolean last_day=false;
    private String like="0";
    private String big_category;
    private int view_num;
    private String group_name;
    private String kakao_nick;
    private int push;

    public boolean getLastDay(){return last_day;}
    public void setLast_day_T(){this.last_day=true;}
    public void setLast_day_F(){this.last_day=false;}
    public int getId(){return  id;}
    public void setId(int id){this.id = id;}
    public String getKakao_id(){return kakao_id;}
    public String getCategory(){return category;}
    public String getGroup(){return group;}
    public String getTitle(){return title;}
    public String getContent(){return content;}
    public String getPosting_date(){return posting_date;}
    public String getlink(){return link;}
    public String getStart_date(){return start_date;}
    public String getEnd_date(){return end_date;}
    public String getHas_pic(){return has_pic;}
    public String getBig_category(){return big_category;}
    public void setFirst_day_T(){this.first_day=true;}
    public void setFirst_day_F(){this.first_day=false;}
    public String getLike(){return this.like;}
    public void setLike(String like){this.like=like;}
    public void setBig_category(String big_category){this.big_category=big_category;}
    public void setLink(String link){this.link=link;}
    public String getLink(){return link;}
    public int getView_num(){return view_num;}
    public void setView_num(int view_num){this.view_num=view_num;}
    public boolean getFirst_day(){return first_day;}
    public void setDday(int dday){this.dday=dday;}
    public int getDday(){return dday;}
    public String getGroup_name(){return group_name;}
    public void setGroup_name(String group_name){this.group_name=group_name;}
    public void setKakao_nic(String kakao_nic){this.kakao_nick=kakao_nic;}
    public String getKakao_nic(){return kakao_nick;}
    public int getPush(){return push;}


}
