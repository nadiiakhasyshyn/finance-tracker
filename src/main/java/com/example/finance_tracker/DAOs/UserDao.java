package com.example.finance_tracker.DAOs;

import com.example.finance_tracker.entities.User;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveUser(User user){
        sessionFactory.getCurrentSession().persist(user);
    }

     public Optional<User> findByEmail(String email){
        return sessionFactory.getCurrentSession()
                .createQuery("FROM User WHERE email =:email", User.class)
                .setParameter("email", email)
                .uniqueResultOptional();
    }

    public Optional<User> getUserById(Long id){
        return sessionFactory.getCurrentSession()
                .createQuery("FROM User WHERE id=:id", User.class)
                .setParameter("id", id)
                .uniqueResultOptional();
    }

    public List<User> getAllUsers(){
        return sessionFactory.getCurrentSession()
                .createQuery("FROM User", User.class)
                .getResultList();
    }

    public void deleteUserById(Long id){
        sessionFactory.getCurrentSession()
                .createQuery("DELETE FROM User WHERE id =:id")
                .setParameter("id",id)
                .executeUpdate();
    }

    public void updateUser(Long id, String firstName, String lastName, String email, String encodedPassword){
       User user = sessionFactory.getCurrentSession().find(User.class, id);
       if(user!=null){
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(encodedPassword);
       }
    }

    public boolean emailExists(String email){
        Long count = sessionFactory.getCurrentSession()
                .createQuery("SELECT COUNT(u) FROM User u WHERE u.email =:email", Long.class)
                .setParameter("email",email)
                .uniqueResult();
        return count>0 && count != null;
    }
}

