package com.novare.natflix.models.content;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie extends Content {
    private String director;

    private String videoCode;
    public Movie() {}

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getVideoCode() {
        return videoCode;
    }

    public void setVideoCode(String videoCode) {
        this.videoCode = videoCode;
    }
}
