package com.uade.tpo.marketplace.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.marketplace.entity.Category;


public interface CategoryService {
    public Page<Category> getCategories(PageRequest pageRequest);

    public Category createCategory(String name);

    public Category editCategory(Long id, Category category);
    public void deleteCategory(Long id);
}
