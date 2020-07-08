package com.souret.repository;

import com.souret.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UsersRepository extends JpaRepository<User, Integer> {

    List<User> findByEmail(String email);
    User findByEmailAndPassword(String email , String password);

    @Query(
            value = "SELECT * FROM users u WHERE u.id <> ?1 and u.id not in (select followerid from userfollowers) limit 10",
            nativeQuery = true)
    List<User> findUsersToFollow(Integer status);
}
