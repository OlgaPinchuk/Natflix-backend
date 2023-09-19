package com.novare.natflix.models.content;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="series_episodes")
@JsonIgnoreProperties({"genre", "series", "contentType"})
public class Episode extends Content{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private long id;

    private int seasonNumber;

    @Column(unique = true)
    private int episodeNumber;

    @Column(name="content_id")
    private long contentId;

    private Genre genre;

    private String videoCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="series_id", nullable = false)
    private Series series;


    public Episode() {}

    public Episode(long id, int seasonNumber, int episodeNumber, long contentId, String videoCode) {
        this.id = id;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.contentId = contentId;
        this.videoCode = videoCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public long getContentId() {
        return contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public String getVideoCode() {
        return videoCode;
    }

    public void setVideoCode(String videoCode) {
        this.videoCode = videoCode;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

}
