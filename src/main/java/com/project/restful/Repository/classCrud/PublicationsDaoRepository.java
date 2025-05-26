package com.project.restful.Repository.classCrud;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.Repository.interfacesLogic.PublicationDao;
import com.project.restful.dtos.publish.PublicationDataDto;
import com.project.restful.models.Products;
import com.project.restful.models.Publications;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PublicationsDaoRepository implements DaoCrud<Publications>, PublicationDao {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Publications> selectAll() {
        Query query = entityManager.createQuery("select p from Publications p where p.active = true and p.status=?1");
        query.setParameter(1,1);
        return query.getResultList();
    }

    @Override
    public Optional<Publications> get(Long id) {
        return Optional.ofNullable(entityManager.find(Publications.class,id));
    }


    @Override
    public Optional<Publications> create(Publications object) {
        entityManager.persist(object);
        return Optional.ofNullable(object);
    }


    @Override
    public Optional<Publications> update(Publications object) {
        return Optional.ofNullable(entityManager.merge(object));
    }


    @Override
    public Optional<Publications> delete(Long id) {
        return Optional.of(null);
    }

    @Override
    public List<Products> searchProductInPublication(Long idProduct) {
        Query query = entityManager.createQuery("select p from Publications p where p.product.id = ?1 and p.active = true");
        query.setParameter(1,idProduct);
        return query.getResultList();
    }

    @Override
    public List<Publications> searchAllPublicationsUser(String identification) {
        Query query = entityManager.createQuery("select p from Publications p where p.user.identification = ?1 and p.active = true");
        query.setParameter(1,identification);
        return query.getResultList();
    }

    @Override
    public Optional<Publications> searchPublicationById(Long id) {
        return get(id);
    }

    @Override
    public List<Publications> verifyIsProductIsInPublication(Long id) {
        Query query = entityManager.createQuery("select p from Publications p where p.product.id = ?1");
        query.setParameter(1,id);
        return query.getResultList();
    }

}
