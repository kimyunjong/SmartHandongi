package com.smarthandongi.database;

/**
 * Created by Joel on 2015-02-14.
 */

public class BusYgrData {

    private String s1 = "", s2 = "", s3 = "", s4 = "", s5 = "";
    Boolean divider = false, hide_line = false, past = false;
    int merge_code;

    public BusYgrData( String s1, String s2, String s3, String s4, String s5, Boolean divider, Boolean hide_line, int merge_code, boolean past) {

        super();

        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.s4 = s4;
        this.s5 = s5;
        this.divider = divider;
        this.hide_line = hide_line;
        this.merge_code = merge_code;
        this.past = past;
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    public String getS3() {
        return s3;
    }

    public void setS3(String s3) {
        this.s3 = s3;
    }

    public String getS4() {
        return s4;
    }

    public void setS4(String s4) {
        this.s4 = s4;
    }

    public String getS5() {
        return s5;
    }

    public void setS5(String s5) {
        this.s5 = s5;
    }

    public Boolean getDivider() {
        return divider;
    }

    public void setDivider(Boolean divider) {
        this.divider = divider;
    }

    public Boolean getHide_line() {
        return hide_line;
    }

    public void setHide_line(Boolean hide_line) {
        this.hide_line = hide_line;
    }

    public int getMerge_code() {
        return merge_code;
    }

    public void setMerge_code(int merge_code) {
        this.merge_code = merge_code;
    }

    public Boolean getPast() {
        return past;
    }

    public void setPast(Boolean past) {
        this.past = past;
    }


}
