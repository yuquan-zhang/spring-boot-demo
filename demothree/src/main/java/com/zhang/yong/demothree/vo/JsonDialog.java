package com.zhang.yong.demothree.vo;

public class JsonDialog {
    private int width;
    private boolean showBtn = false;
    private String onsuccess;
    private String oncancel;
    private String html;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getOncancel() {
        return oncancel;
    }

    public void setOncancel(String oncancel) {
        this.oncancel = oncancel;
    }

    public String getOnsuccess() {
        return onsuccess;
    }

    public void setOnsuccess(String onsuccess) {
        this.onsuccess = onsuccess;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isShowBtn() {
        return showBtn;
    }

    public void setShowBtn(boolean showBtn) {
        this.showBtn = showBtn;
    }
}
