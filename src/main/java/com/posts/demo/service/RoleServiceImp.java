package com.posts.demo.service;

import com.posts.demo.dao.RoleDao;
import com.posts.demo.entity.Role;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp implements RoleService{

    RoleDao roleDao;
    EntityManager entityManager;

    public RoleServiceImp(RoleDao roleDao, EntityManager entityManager){
        this.roleDao = roleDao;
        this.entityManager = entityManager;
    }


    @Override
    public void save(Role role) {
        roleDao.save(role);
    }
}
