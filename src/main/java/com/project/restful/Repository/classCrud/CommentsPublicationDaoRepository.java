package com.project.restful.Repository.classCrud;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.Repository.interfacesLogic.CommentsPublicationDao;
import com.project.restful.models.CommentsPublication;
import com.project.restful.models.Points;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class CommentsPublicationDaoRepository implements DaoCrud<CommentsPublication>, CommentsPublicationDao {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public List<CommentsPublication> selectAll() {
        return List.of();
    }

    @Override
    public Optional<CommentsPublication> get(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<CommentsPublication> create(CommentsPublication object) {
        entityManager.persist(object);
        return Optional.ofNullable(object);
    }

    @Override
    public Optional<CommentsPublication> update(CommentsPublication object) {
        return Optional.ofNullable(entityManager.merge(object));
    }

    @Override
    public Optional<CommentsPublication> delete(Long id) {
        return Optional.empty();
    }

    @Override
    public List<CommentsPublication> findCommentsPublicationByPublication(Long idPublication) {
        Query query =  entityManager.createQuery("select cp from CommentsPublication cp where cp.publications.id=?1", CommentsPublication.class);
        query.setParameter(1,idPublication);
        return query.getResultList();
    }
}
