package com.novare.natflix.models.content;

import jakarta.persistence.*;

@Entity
@Table(name="content_details")
public class ContentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private long id;

//    @Column(name = "video_code")
//    private String videoCode;

    @OneToOne
    @JoinColumn(name = "content_id")
    private Content content;

    public ContentDetails() {}

    public ContentDetails(long id, String videoCode) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public String getVideoCode() {
//        return videoCode;
//    }
//
//    public void setVideoCode(String videoCode) {
//        this.videoCode = videoCode;
//    }
}
