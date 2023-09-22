package com.novare.natflix.dao.user;

import com.novare.natflix.models.user.User;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public class UserDao implements IUserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAll() {
        String sql = "FROM User as u";
        List<User> userList = (List<User>) entityManager.createQuery(sql).getResultList();
        return userList;
    }

    @Override
    public User get(long id) {
        return entityManager.find(User.class, id);
    }
    @Override
    public User findByEmail(String email){
        String sql = "SELECT u FROM User u WHERE u.email = :email";

        try {
            return entityManager.createQuery(sql, User.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException exception) {
            return null;
        }
    }
    @Override
    public User register(User user){
        entityManager.persist(user);
        return user;
    }
}
