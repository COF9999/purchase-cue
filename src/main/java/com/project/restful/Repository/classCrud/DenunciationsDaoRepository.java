package com.project.restful.Repository.classCrud;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.Repository.interfacesLogic.DenunciationDao;
import com.project.restful.models.Denunciations;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class DenunciationsDaoRepository implements DaoCrud<Denunciations>, DenunciationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Denunciations> selectAll() {
        return List.of();
    }

    @Override
    public Optional<Denunciations> get(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Denunciations> create(Denunciations denunciations) {
        entityManager.persist(denunciations);
        return Optional.ofNullable(denunciations);
    }

    @Override
    public Optional<Denunciations> update(Denunciations object) {
        return Optional.empty();
    }

    @Override
    public Optional<Denunciations> delete(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Denunciations> findDenunciationsOfUser(Long idUserReported) {
        Query query = entityManager.createQuery("select d from Denunciations d where d.userReported.id = ?1");
        System.out.println(idUserReported);
        query.setParameter(1,idUserReported);
        return query.getResultList();
    }
}
