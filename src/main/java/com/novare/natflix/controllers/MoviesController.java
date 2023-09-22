package com.novare.natflix.controllers;

import com.novare.natflix.models.content.*;
import com.novare.natflix.payloads.*;
import com.novare.natflix.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/media/movies")
public class MoviesController {
    @Autowired
    ContentService contentService;

    @Autowired
    private MovieService movieService;


    @GetMapping
    public ResponseEntity<List<MovieDto>> getMovies() {
        List<MovieDto> movies = movieService.getMovies();
        return ResponseEntity.ok(movies);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMovies(@RequestBody MovieDto movieDto) {
        Genre genre = contentService.findGenreById(movieDto.getGenre_id());

        if (movieDto.getTitle() == null || genre == null) {
            return ResponseEntity.badRequest().body("Required fields are missing");
        }

        Movie newMovie = new Movie();
        newMovie.setCommonProperties(movieDto, genre);
        newMovie.setContentType(contentService.findContentTypeByName("Movies"));
        newMovie.setDirector(movieDto.getDirector());
        newMovie.setVideoCode(movieDto.getVideoCode());
        newMovie.setRating(movieDto.getRating());
        contentService.create(newMovie);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("/{id}/update")
    public ResponseEntity<String> updateMovie(@RequestBody MovieDto payload, @PathVariable(name="id") long id) {
        movieService.update(id, payload);
        return ResponseEntity.ok("Success");
    }

}