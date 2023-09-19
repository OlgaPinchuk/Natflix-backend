package com.novare.natflix.models.content;

import com.novare.natflix.payloads.ContentDto;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="content")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private long id;

    @Column(nullable = false)
    private String title;
    @Column
    private String summary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    @ManyToOne
    @JoinColumn(name="type_id")
    private ContentType contentType;

    @Column(name="banner_url")
    private String bannerUrl;

    @Column(name="thumb_url")
    private String thumbUrl;

    public Content() {}

    public Content(long id, String title, String summary, Genre genre, ContentType contentType, String bannerUrl, String thumbUrl) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.genre = genre;
        this.contentType = contentType;
        this.bannerUrl = bannerUrl;
        this.thumbUrl = thumbUrl;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }


    public void setCommonProperties(ContentDto contentDto, Genre genre) {
        setTitle(contentDto.getTitle());
        setSummary(contentDto.getSummary());
        setThumbUrl(contentDto.getThumbUrl());
        setBannerUrl(contentDto.getBannerUrl());
        setGenre(genre);
    }

}
