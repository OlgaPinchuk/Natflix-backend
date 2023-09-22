package com.novare.natflix.models.content;

import jakarta.persistence.*;

@Entity
@Table(name="documentaries")
public class Documentary extends Content {

    private String narrator;
    private String videoCode;

    public Documentary() {}

    public String getNarrator() {
        return narrator;
    }

    public void setNarrator(String narrator) {
        this.narrator = narrator;
    }

    public String getVideoCode() {
        return videoCode;
    }

    public void setVideoCode(String videoCode) {
        this.videoCode = videoCode;
    }
}
