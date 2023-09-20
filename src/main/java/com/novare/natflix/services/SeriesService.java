package com.novare.natflix.services;

import com.novare.natflix.dao.content.IContentDao;
import com.novare.natflix.exceptions.ResourceNotFoundException;
import com.novare.natflix.models.content.Content;
import com.novare.natflix.models.content.Movie;
import com.novare.natflix.models.content.Series;
import com.novare.natflix.payloads.ContentDto;
import com.novare.natflix.payloads.MovieDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeriesService {
    @Autowired
    private ContentService contentService;

    @Autowired
    IContentDao iContentDao;

    public List<ContentDto> getSeries() {
        List<Content> contentList = contentService.getContentsByType("series");
        return contentList.stream()
                .filter(content -> content instanceof Series)
                .map(content -> convertToDto((Series) content))
                .collect(Collectors.toList());
    }

    public Content update(long id, ContentDto payload) {
        Series current = (Series) iContentDao.get(id);
        if(current == null) {
            throw new ResourceNotFoundException("Movie", "id", String.valueOf(id));
        }
        current.setCommonProperties(payload, iContentDao.findGenreById(payload.getGenreId()));

        return iContentDao.update(current);

    }

    public ContentDto convertToDto(Content content) {
        ContentDto responseDto = new ContentDto();

        responseDto.setId(content.getId());
        responseDto.setTitle(content.getTitle());
        responseDto.setSummary(content.getSummary());
        responseDto.setContentTypeId(content.getContentType().getId());
        responseDto.setGenreId(content.getGenre().getId());
        responseDto.setBannerUrl(content.getBannerUrl());
        responseDto.setThumbUrl(content.getThumbUrl());

        return responseDto;
    }
}
