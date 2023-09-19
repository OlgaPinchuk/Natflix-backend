package com.novare.natflix.payloads;

import lombok.Data;

@Data
public class EpisodeDto extends ContentDto {
    private int seasonNumber;
    private int episodeNumber;
    private String videoCode;
}
