package com.project.restful.Repository.classCrud;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.Repository.interfacesLogic.TokenDao;
import com.project.restful.models.Products;
import com.project.restful.models.Tokens;
import com.project.restful.models.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class TokenDaoRepository implements DaoCrud<Tokens>, TokenDao {

    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List<Tokens> selectAll() {
        return List.of();
    }

    @Override
    public Optional<Tokens> get(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Tokens> create(Tokens object) {
        entityManager.persist(object);
        return Optional.ofNullable(object);
    }

    @Override
    public Optional<Tokens> update(Tokens object) {
        return Optional.empty();
    }

    @Override
    public Optional<Tokens> delete(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Tokens> findByPersonaAndIsLogOut(String identification, Boolean isLogged) {
        Query query = entityManager.createQuery("select  t from Tokens t where t.users.identification=?1 and t.isLogOut=false",Tokens.class);
        query.setParameter(1, identification);
        return query.getResultList();
    }

    @Override
    public Optional<Tokens> findByToken(String token) {
        return Optional.empty();
    }
}
