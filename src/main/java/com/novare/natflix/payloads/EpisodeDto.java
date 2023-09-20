package com.novare.natflix.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeDto extends ContentDto {
    @JsonProperty("season_number")
    private int seasonNumber;
    @JsonProperty("episode_number")
    private int episodeNumber;
    @JsonProperty("video_code")
    private String videoCode;
}
