package com.my.bubbletea;
import java.io.Serializable;
public class Note implements Serializable {
    private String text;
    private String text1;
    private String text2;
    private String text3;
    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText1() {
        return text1;
    }
    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText2() {
        return text2;
    }
    public void setText3(String text3) {
        this.text3 = text3;
    }

    public String getText3() {
        return text3;
    }


}
