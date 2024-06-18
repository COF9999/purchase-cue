package com.project.restful.Repository.classCrud;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.Repository.interfacesLogic.CounterOfferDao;
import com.project.restful.models.CounterOffer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class CounterOfferDaoRepository implements DaoCrud<CounterOffer>, CounterOfferDao {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public List<CounterOffer> selectAll() {
        return List.of();
    }

    @Override
    public Optional<CounterOffer> get(Long id) {
        return Optional.ofNullable(entityManager.find(CounterOffer.class,id));
    }

    @Override
    public Optional<CounterOffer> create(CounterOffer object) {
        entityManager.persist(object);
        return Optional.ofNullable(object);
    }

    @Override
    public Optional<CounterOffer> update(CounterOffer object) {
        return Optional.ofNullable(entityManager.merge(object));
    }

    @Override
    public Optional<CounterOffer> delete(Long id) {
        return Optional.empty();
    }


}
