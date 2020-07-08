package com.souret.service;

import com.souret.model.Activity;
import com.souret.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ActivityService {

    @Autowired
    private ActivityRepository repo;

    public List<Activity> listAll() {
        return repo.findAll();
    }

    public List<Activity> listUserActivity(int userId) {
            return repo.findUserActivity(userId);
    }

    public synchronized boolean addActivity(Activity activity){
            activity.setCreationdate(LocalDateTime.now());
            repo.save(activity);
            return true;
    }


    public void save(Activity activity) {
        repo.save(activity);
    }

    public Activity get(Integer id) {
        return repo.findById(id).get();
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

}
