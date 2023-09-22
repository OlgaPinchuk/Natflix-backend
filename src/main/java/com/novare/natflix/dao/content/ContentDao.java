package com.novare.natflix.dao.content;

import com.novare.natflix.exceptions.ResourceNotFoundException;
import com.novare.natflix.models.content.*;
import com.novare.natflix.payloads.ContentDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Transactional
@Repository
public class ContentDao implements IContentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Content get(long id) {
        return entityManager.find(Content.class, id);
    }

    @Override
    public List<Content> getContentByType(ContentType type) {
        String sql = "SELECT c FROM Content c WHERE c.contentType = :type";

        try {
            return entityManager.createQuery(sql, Content.class)
                    .setParameter("type", type)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Content create(Content newContent) {
        entityManager.persist(newContent);
        return newContent;
    }

    @Override
    public Content update(long id, ContentDto payload) {
        Content currentContent = get(id);

        if(currentContent == null) {
            throw new ResourceNotFoundException("Content", "id", String.valueOf(id));
        }

        currentContent.setCommonProperties(payload, findGenreById(payload.getGenre_id()));
        currentContent.setContentType(findContentTypeById(payload.getContentTypeId()));


        entityManager.merge(currentContent);
        return currentContent;
    }
    @Override
    public Content update(Content content) {
        entityManager.merge(content);
        return content;
    }

    @Override
    public void delete(Content content) {
        entityManager.remove(content);
    }

    @Override
    public ContentType findContentTypeByName(String name) {
        String sql = "SELECT ct FROM ContentType ct WHERE ct.name = :name";

        try {
            return entityManager.createQuery(sql, ContentType.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public ContentType findContentTypeById(long id) {
        String sql = "SELECT ct FROM ContentType ct WHERE ct.id = :id";

        try {
            return entityManager.createQuery(sql, ContentType.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Genre findGenreByName(String name) {
        String sql = "SELECT g FROM Genre g WHERE g.name = :name";
        try {
            return entityManager.createQuery(sql, Genre.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Genre findGenreById(long id) {
        String sql = "SELECT g FROM Genre g WHERE g.id = :id";
        try {
            return entityManager.createQuery(sql, Genre.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
