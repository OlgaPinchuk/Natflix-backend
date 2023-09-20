package com.novare.natflix.services;

import com.novare.natflix.dao.content.IContentDao;
import com.novare.natflix.exceptions.ResourceNotFoundException;
import com.novare.natflix.models.content.Content;
import com.novare.natflix.models.content.Movie;
import com.novare.natflix.payloads.MovieDto;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    private ContentService contentService;

    @Autowired
    IContentDao iContentDao;


    public List<MovieDto> getMovies() {
        List<Content> contentList = contentService.getContentsByType("movies");
        return contentList.stream()
                .filter(content -> content instanceof Movie)
                .map(content -> convertToDto((Movie) content))
                .collect(Collectors.toList());
    }

    public Content update(long id, MovieDto payload) {
        Movie current = (Movie) iContentDao.get(id);
        if(current == null) {
            throw new ResourceNotFoundException("Movie", "id", String.valueOf(id));
        }
        current.setCommonProperties(payload, iContentDao.findGenreById(payload.getGenreId()));
        current.setRating(payload.getRating());
        current.setDirector(payload.getDirector());
        current.setVideoCode(payload.getVideoCode());

        return iContentDao.update(current);

    }

    public MovieDto convertToDto(Movie movie) {
        MovieDto responseDto = new MovieDto();

        responseDto.setId(movie.getId());
        responseDto.setTitle(movie.getTitle());
        responseDto.setSummary(movie.getSummary());
        responseDto.setContentTypeId(movie.getContentType().getId());
        responseDto.setGenreId(movie.getGenre().getId());
        responseDto.setBannerUrl(movie.getBannerUrl());
        responseDto.setThumbUrl(movie.getThumbUrl());
        responseDto.setRating(movie.getRating());
        responseDto.setDirector(movie.getDirector());
        responseDto.setVideoCode(movie.getVideoCode());

        return responseDto;
    }

}
