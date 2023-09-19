package com.novare.natflix.payloads;

import lombok.Data;

@Data
public class MovieDto extends ContentDto {
    private String director;
    private String videoCode;
}