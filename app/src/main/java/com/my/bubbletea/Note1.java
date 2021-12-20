package com.my.bubbletea;

import android.media.Image;
import android.widget.ImageButton;

import java.io.Serializable;

public class Note1 implements Serializable {
    private String id;
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
