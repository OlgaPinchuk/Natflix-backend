package com.novare.natflix.dao.content;

import com.novare.natflix.models.content.Content;
import com.novare.natflix.models.content.ContentType;
import com.novare.natflix.models.content.Genre;
import com.novare.natflix.payloads.ContentDto;

import java.util.List;

public interface IContentDao {

    Content get(long id);

    Content create(Content newContent);

    Content update(long id, ContentDto payload);
    Content update(Content content);

    void delete(Content content);

    List<Content> getContentByType(ContentType type);
    ContentType findContentTypeById(long id);

    Genre findGenreByName(String name);

    Genre findGenreById(long id);
    ContentType findContentTypeByName(String name);
}
