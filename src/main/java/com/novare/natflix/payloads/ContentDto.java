package com.novare.natflix.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ContentDto {
    protected long id;
    protected String title;
    protected String summary;
    @JsonProperty("genre_id")
    protected long genreId;
    @JsonProperty("media_type_id")
    protected long contentTypeId;
    @JsonProperty("banner_url")
    protected String bannerUrl;
    @JsonProperty("thumbnail_url")
    protected String thumbUrl;
}
