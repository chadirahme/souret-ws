package com.souret.service;

import com.souret.model.Category;
import com.souret.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
    CategoryRepository  repo;

    public List<Category> listAll() {
        return repo.findAll();
    }


    public synchronized boolean addUCategory(Category category){
        List<Category> list = repo.findByName(category.getName());
        if (list.size() > 0) {
            return false;
        } else {
            repo.save(category);
            return true;
        }
    }


    public void save(Category category) {
        repo.save(category);
    }

    public Category get(Integer id) {
        return repo.findById(id).get();
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

}
