package com.novare.natflix.payloads;

import lombok.Data;

@Data
public class ContentDto {
    private long id;
    private String title;
    private String summary;
    private String genre;
    private String contentType;
    private String bannerUrl;
    private String thumbUrl;
}
