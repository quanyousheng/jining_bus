package com.whpe.qrcode.shandong_jining.view;

/**
 * Created by yang on 2018/7/18.
 */
public class WordTouchBean {

    private float start;
    private float end;
    private String wordText;

    public WordTouchBean(float start, String wordText) {
        super();
        this.start = start;
        this.wordText = wordText;
    }

    public float getStart() {
        return start;
    }

    public float getEnd() {
        return end;
    }

    public String getWordText() {
        return wordText;
    }

    public void setStart(float start) {
        this.start = start;
    }

    public void setEnd(float end) {
        this.end = end;
    }

    public void setWordText(String wordText) {
        this.wordText = wordText;
    }
}