package com.bean;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "categories")
public class CategoryBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer categoryId;
	String categoryName;
	
	@JsonIgnore
	@OneToMany(mappedBy="categories")
	List<SubCategoryBean> subCategories;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<SubCategoryBean> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<SubCategoryBean> subCategories) {
		this.subCategories = subCategories;
	}

	
	
}
