package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bean.CategoryBean;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryBean, Integer>{

	CategoryBean findByCategoryId(Integer categoryId);

	CategoryBean findByCategoryName(String categoryName);

}
