package com.project.restful.Repository.classCrud;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.Repository.interfacesLogic.ProductDao;
import com.project.restful.models.Products;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ProductDaoRepository implements DaoCrud<Products>, ProductDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Products> selectAll() {
        return List.of();
    }

    @Override
    public Optional<Products> get(Long id) {
        return Optional.ofNullable(entityManager.find(Products.class,id));
    }


    @Override
    public Optional<Products> create(Products object) {
        entityManager.persist(object);
        return Optional.ofNullable(object);
    }


    @Override
    public Optional<Products> update(Products object) {
        return Optional.ofNullable(entityManager.merge(object));
    }


    @Override
    public Optional<Products> delete(Long id) {
        Products p = get(id).orElseThrow();
        entityManager.remove(p);
        return Optional.ofNullable(p);
    }

    @Override
    public List<Products> allProductOfUser(String identification,Boolean isChanged) {
        Query query =  entityManager.createQuery("select p from Products p where p.user.identification =?1 and p.isChanged=?2",Products.class);
        query.setParameter(1, identification);
        query.setParameter(2,isChanged);
        return query.getResultList();
    }
}

