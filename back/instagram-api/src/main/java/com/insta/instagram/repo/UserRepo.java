package com.insta.instagram.repo;

import com.insta.instagram.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {


    public Optional<User> findByEmail(String email);
    public Optional<User> findByUsername(String username);

    @Query("select u from User u where u.id IN :users")
    public List<User> findAllUsersByUserIds(@Param("users")List<Integer> userIds);

    @Query("SELECT DISTINCT  u from User u where u.username LIKE %:query% OR u.email like %:query%")
    public List<User> findByQuery(@Param("query") String query);
}
