package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public interface CategoryService {
    public Page<Category> getCategories(PageRequest pageRequest);

    public Category createCategory(String name);
}
