package com.smarthandongi.database;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by user on 2015-02-04.
 */
public class Picture implements Serializable {
    private Bitmap picture;
    private int no;
    public Bitmap getPicture() {
        return picture;
    }
    public void setPicture(Bitmap picture) {
        this.picture=picture;
    }
    public Picture() {
        super();
    }
}
