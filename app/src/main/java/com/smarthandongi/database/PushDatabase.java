package com.smarthandongi.database;

import java.io.Serializable;

/**
 * Created by LEWIS on 2015-01-29.
 */
public class PushDatabase implements Serializable {
    public PushDatabase(int id, String kakao_id, String kakao_nick, String register_id){
        this.id = id;
        this.kakao_id = kakao_id;
        this.kakao_nick = kakao_nick;
        this.register_id = register_id;
    }

    private int id;
    private String kakao_id;
    private String kakao_nick;
    private String register_id;

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public String getKakao_id() { return kakao_id;}

    public void setKakao_id(String kakao_id) { this.kakao_id = kakao_id;}

    public String getKakao_nick() { return kakao_nick;}

    public void setKakao_nick(String kakao_nick) { this.kakao_nick = kakao_nick;}

    public String getRegister_id() { return register_id;}

    public void setRegister_id(String register_id) { this.register_id = register_id;}

}
