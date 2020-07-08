package com.souret.service;


import com.souret.model.User;
import com.souret.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UsersRepository repo;

    public List<User> listAll() {
        return repo.findAll();
    }

    public List<User> findUsersToFollow(int userId) {
        return repo.findUsersToFollow(userId);
    }


    public synchronized boolean addUser(User user){
        List<User> list = repo.findByEmail(user.getEmail());
        if (list.size() > 0) {
            return false;
        } else {
            user.setCreationdate(LocalDateTime.now());
            repo.save(user);
            return true;
        }
    }


    public void save(User user) {
        repo.save(user);
    }

    public User get(Integer id) {
        return repo.findById(id).get();
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public User loginUser(User user){
        return repo.findByEmailAndPassword(user.getEmail(), user.getPassword());
    }
}
