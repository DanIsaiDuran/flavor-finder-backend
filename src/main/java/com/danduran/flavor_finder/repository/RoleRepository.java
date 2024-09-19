package com.danduran.flavor_finder.repository;

import org.springframework.data.repository.CrudRepository;

import com.danduran.flavor_finder.model.Role;
import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Long>{

    List<Role> findRoleByNameIn(List<String> roleNames);
}
