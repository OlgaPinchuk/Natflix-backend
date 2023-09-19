package com.novare.natflix.dao.content;

import com.novare.natflix.models.content.Content;
import com.novare.natflix.models.content.ContentType;
import com.novare.natflix.models.content.Genre;
import com.novare.natflix.payloads.ContentDto;

import java.util.List;

public interface IContentDao {
    List<Content> getAll();

    Content get(long id);

    Content create(Content newContent);

    Content update(long id, ContentDto payload);

    void delete(Content content);

    List<Content> getContentByType(ContentType type);

    Genre findGenreByName(String name);
    ContentType findContentTypeByName(String name);
}
