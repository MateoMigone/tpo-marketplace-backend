package com.uade.tpo.marketplace.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.repository.CategoryRepository;

//import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @SuppressWarnings("override")
    public Page<Category> getCategories(PageRequest pageable) {
        return categoryRepository.findAll(pageable);
    }

    @SuppressWarnings("override")
    public Category createCategory(String name) {
        //List<Category> categories = categoryRepository.findByName(name);
            return categoryRepository.save(new Category(name));
    }

    @SuppressWarnings("override")
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

     @SuppressWarnings("override")
    public Category editGame(Long id, Category categoryDetails) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(categoryDetails.getName());
            return categoryRepository.save(category);
        }).orElse(null);
    }
}
