package com.bean;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "subcategories")
public class SubCategoryBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer subCategoryId;
	String subCategory;
	
	@ManyToOne
	@JoinColumn(name = "categoryId" )
	CategoryBean categories;

	public Integer getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public CategoryBean getCategories() {
		return categories;
	}

	public void setCategories(CategoryBean categories) {
		this.categories = categories;
	}
	
}
