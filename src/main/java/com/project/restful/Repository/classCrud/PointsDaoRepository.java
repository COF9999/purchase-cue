package com.project.restful.Repository.classCrud;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.Repository.interfacesLogic.PointsDao;
import com.project.restful.dtos.points.PointsDto;
import com.project.restful.models.Points;
import com.project.restful.models.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class PointsDaoRepository implements DaoCrud<Points>, PointsDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Points> selectAll() {
        return List.of();
    }

    @Override
    public Optional<Points> get(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Points> create(Points object) {
        entityManager.persist(object);
        return Optional.ofNullable(object);
    }

    @Override
    public Optional<Points> update(Points object) {
        return Optional.ofNullable(entityManager.merge(object));
    }

    @Override
    public Optional<Points> delete(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Points> findPointsOfUser(Long idUser) {
        try {
            Query query =  entityManager.createQuery("select points from Points points where points.user.id=?1", Points.class);
            query.setParameter(1, idUser);
            return Optional.ofNullable((Points) query.getSingleResult());
        }catch (NoResultException e){
            return Optional.empty();
        }
    }


}
