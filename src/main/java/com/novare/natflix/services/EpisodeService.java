package com.novare.natflix.services;

import com.novare.natflix.dao.content.IContentDao;
import com.novare.natflix.exceptions.ResourceNotFoundException;
import com.novare.natflix.models.content.*;
import com.novare.natflix.payloads.EpisodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EpisodeService {

    @Autowired
    private ContentService contentService;

    @Autowired
    IContentDao iContentDao;

    public List<EpisodeDto> getEpisodes(long seriesId) {
        Series series = (Series) contentService.get(seriesId);
        Set<Episode> episodes = series.getEpisodes();

        return episodes.stream()
                .filter(Objects::nonNull)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Content update(long id, EpisodeDto payload) {
        Episode current = (Episode) iContentDao.get(id);
        if(current == null) {
            throw new ResourceNotFoundException("Episode", "id", String.valueOf(id));
        }
        current.setCommonProperties(payload);
        current.setVideoCode(payload.getVideoCode());
        current.setSeasonNumber(payload.getSeasonNumber());
        current.setEpisodeNumber(payload.getEpisodeNumber());

        return iContentDao.update(current);

    }

    public EpisodeDto convertToDto(Episode episode) {
        EpisodeDto responseDto = new EpisodeDto();

        responseDto.setId(episode.getId());
        responseDto.setTitle(episode.getTitle());
        responseDto.setSummary(episode.getSummary());
        responseDto.setBannerUrl(episode.getBannerUrl());
        responseDto.setThumbUrl(episode.getThumbUrl());
        responseDto.setSeasonNumber(episode.getSeasonNumber());
        responseDto.setEpisodeNumber(episode.getEpisodeNumber());
        responseDto.setVideoCode(episode.getVideoCode());

        return responseDto;
    }
}
