package com.example.cameraapp.model;

public class StudiolightModel {

    private String lightboxname, ledlightnumber, lightboxflorecentlightnumber, florecentlightname, florecentlightnumber, additionalLight;
    private boolean isSelected = false;

    public String getAdditionalLight() {
        return additionalLight;
    }

    public void setAdditionalLight(String additionalLight) {
        this.additionalLight = additionalLight;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getLightboxname() {
        return lightboxname;
    }

    public void setLightboxname(String lightboxname) {
        this.lightboxname = lightboxname;
    }

    public String getLedlightnumber() {
        return ledlightnumber;
    }

    public void setLedlightnumber(String ledlightnumber) {
        this.ledlightnumber = ledlightnumber;
    }

    public String getLightboxflorecentlightnumber() {
        return lightboxflorecentlightnumber;
    }

    public void setLightboxflorecentlightnumber(String lightboxflorecentlightnumber) {
        this.lightboxflorecentlightnumber = lightboxflorecentlightnumber;
    }

    public String getFlorecentlightname() {
        return florecentlightname;
    }

    public void setFlorecentlightname(String florecentlightname) {
        this.florecentlightname = florecentlightname;
    }

    public String getFlorecentlightnumber() {
        return florecentlightnumber;
    }

    public void setFlorecentlightnumber(String florecentlightnumber) {
        this.florecentlightnumber = florecentlightnumber;
    }
}
