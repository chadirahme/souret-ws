package com.souret.service;


import com.souret.model.UserFollower;
import com.souret.repository.UserFollowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserFollowerService {


    @Autowired
    private UserFollowerRepository repo;

    public List<UserFollower> listAll() {
        return repo.findAll();
    }

    public synchronized boolean addFollow(UserFollower userFollower){
        userFollower.setFollowdate(LocalDateTime.now());
        repo.save(userFollower);
        return true;
    }


    public void save(UserFollower activity) {
        repo.save(activity);
    }

    public UserFollower get(Integer id) {
        return repo.findById(id).get();
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
