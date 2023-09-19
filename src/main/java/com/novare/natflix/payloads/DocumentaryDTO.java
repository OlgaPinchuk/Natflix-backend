package com.novare.natflix.payloads;

import lombok.Data;

@Data
public class DocumentaryDTO extends ContentDto {
    private String narrator;
    private String videoCode;
}
