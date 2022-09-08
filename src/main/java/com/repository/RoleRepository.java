package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bean.RoleBean;

@Repository
public interface RoleRepository extends CrudRepository<RoleBean, Integer> {

	RoleBean findByRoleName(String string);

	
}
