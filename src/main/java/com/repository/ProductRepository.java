package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bean.ProductBean;

@Repository
public interface ProductRepository extends CrudRepository<ProductBean, Integer> {

	List<ProductBean> findAll();
	//@Query(value="select * from products where product_id=?1",nativeQuery = true)
	ProductBean findByProductId(Integer productId);
}
