package com.novare.natflix.services;

import com.novare.natflix.dao.content.IContentDao;
import com.novare.natflix.exceptions.ResourceNotFoundException;
import com.novare.natflix.models.content.Content;
import com.novare.natflix.models.content.Documentary;
import com.novare.natflix.payloads.DocumentaryDto;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentaryService {
    @Autowired
    private ContentService contentService;

    @Autowired
    IContentDao iContentDao;


    public List<DocumentaryDto> getDocumentaries() {
        List<Content> contentList = contentService.getContentsByType("documentaries");
        return contentList.stream()
                .filter(content -> content instanceof Documentary)
                .map(content -> convertToDto((Documentary) content))
                .collect(Collectors.toList());
    }

    public Content update(long id, DocumentaryDto payload) {
        Documentary current = (Documentary) iContentDao.get(id);
        if(current == null) {
            throw new ResourceNotFoundException("Documentary", "id", String.valueOf(id));
        }
        current.setCommonProperties(payload, iContentDao.findGenreById(payload.getGenre_id()));
        current.setVideoCode(payload.getVideoCode());
        current.setNarrator(payload.getNarrator());

        return iContentDao.update(current);

    }

    public DocumentaryDto convertToDto(Documentary documentary) {
        DocumentaryDto responseDto = new DocumentaryDto();

        responseDto.setId(documentary.getId());
        responseDto.setTitle(documentary.getTitle());
        responseDto.setSummary(documentary.getSummary());
        responseDto.setContentTypeId(documentary.getContentType().getId());
        responseDto.setGenre_id(documentary.getGenre().getId());
        responseDto.setBannerUrl(documentary.getBannerUrl());
        responseDto.setThumbUrl(documentary.getThumbUrl());
        responseDto.setNarrator(documentary.getNarrator());
        responseDto.setVideoCode(documentary.getVideoCode());

        return responseDto;
    }

}
