package com.repository;

import org.springframework.data.repository.CrudRepository;

import com.bean.SubCategoryBean;

public interface SubCategoryRepository extends CrudRepository<SubCategoryBean, Integer>{

	SubCategoryBean findBySubCategoryId(Integer subCategoryId);

}
