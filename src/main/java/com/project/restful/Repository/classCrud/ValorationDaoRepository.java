package com.project.restful.Repository.classCrud;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.models.Valorations;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class ValorationDaoRepository implements DaoCrud<Valorations> {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Valorations> selectAll() {
        return List.of();
    }

    @Override
    public Optional<Valorations> get(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Valorations> create(Valorations valoration) {
        entityManager.persist(valoration);
        return Optional.of(valoration);
    }

    @Override
    public Optional<Valorations> update(Valorations object) {
        return Optional.ofNullable(entityManager.merge(object));
    }

    @Override
    public Optional<Valorations> delete(Long id) {
        return Optional.empty();
    }
}
