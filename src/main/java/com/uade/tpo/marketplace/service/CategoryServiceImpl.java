package com.uade.tpo.marketplace.service;


import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.exceptions.CategoryDuplicateException;
import com.uade.tpo.marketplace.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class CategoryServiceImpl {
    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> getCategories(PageRequest pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Category createCategory(String name) throws CategoryDuplicateException {
        List<Category> categories = categoryRepository.findByName(name);
        if (categories.isEmpty())
            return categoryRepository.save(new Category(name));
        throw new CategoryDuplicateException();
    }
}
