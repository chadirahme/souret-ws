package com.souret.repository;

import com.souret.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "activities", path = "activities")
public interface ActivityRepository extends JpaRepository<Activity, Integer> {


    @Query(
            value = "SELECT * FROM activity u WHERE u.userid = ?1 order by creationdate desc limit 20",
            nativeQuery = true)
    List<Activity> findUserActivity(Integer userId);
}
