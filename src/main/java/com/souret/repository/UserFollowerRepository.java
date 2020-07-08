package com.souret.repository;


import com.souret.model.UserFollower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "userfollowers", path = "userfollowers")
public interface UserFollowerRepository extends JpaRepository<UserFollower, Integer> {
}


//To get the top 10 Twitter users with the most followers, the following query can be used:
//SELECT TOP 10 B.[AccountID], B.[FullName], COUNT(*) AS [Followers]
//        FROM [dbo].[Follower] A INNER JOIN [dbo].[Account] B
//        ON A.[AccountID2] = B.[AccountID]
//        GROUP BY B.[AccountID], B.[FullName]
//        ORDER BY COUNT(*) DESC