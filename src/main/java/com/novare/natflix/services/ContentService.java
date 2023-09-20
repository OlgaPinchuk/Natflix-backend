package com.novare.natflix.services;

import com.novare.natflix.dao.content.IContentDao;
import com.novare.natflix.exceptions.ResourceNotFoundException;
import com.novare.natflix.models.content.*;
import com.novare.natflix.payloads.ContentDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ContentService {

    @Autowired
    IContentDao iContentDao;


    public Content get(long id) {
        return iContentDao.get(id);
    }

    public Content create(Content newContent) {
        return iContentDao.create(newContent);
    }

    public Content update(long id, ContentDto payload) {
        return iContentDao.update(id, payload);
    }

    public void delete(long id) {
        Content existingContent = iContentDao.get(id);
        if (existingContent == null) {
            throw new ResourceNotFoundException("Content", "id", String.valueOf(id));
        }
        iContentDao.delete(existingContent);
    }

    public List<Content> getContentsByType(String type) {
        ContentType contentType = iContentDao.findContentTypeByName(type);

        if (contentType != null) {
            return iContentDao.getContentByType(contentType);
        } else {
//          throw new NotFoundException("Content type not found: " + contentType);
            return null;
        }
    }

    public List<Content> getContentsByTypeId(long typeId) {
        ContentType contentType = iContentDao.findContentTypeById(typeId);

        if (contentType != null) {
            return iContentDao.getContentByType(contentType);
        } else {
//          throw new NotFoundException("Content type not found: " + contentType);
            return null;
        }
    }

    public Genre findGenreByName(String name) {
        return iContentDao.findGenreByName(name);
    }

    public Genre findGenreById(long id) {
        return iContentDao.findGenreById(id);
    }

    public ContentType findContentTypeByName(String name) {
        return iContentDao.findContentTypeByName(name);
    }

    public Series findSeriesById(long seriesId) {

        Content content = get(seriesId);

        if (content instanceof Series) {
            return (Series) content;
        }
        else {
            throw new ResourceNotFoundException("Series", "id", String.valueOf(seriesId));
        }
    }

    public Set<Episode> findEpisodesBySeriesId(long seriesId) {
        Series series = findSeriesById(seriesId);
        return series.getEpisodes();
    }
}
