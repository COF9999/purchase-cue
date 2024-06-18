package com.project.restful.Repository.classCrud;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.Repository.interfacesLogic.UserDao;
import com.project.restful.models.Products;
import com.project.restful.models.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDaoRepository implements DaoCrud<Users> , UserDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Users> selectAll() {
        return List.of();
    }

    @Override
    public Optional<Users> get(Long id) {
        return Optional.ofNullable(entityManager.find(Users.class,id));
    }


    @Override
    public Optional<Users> create(Users object) {
        entityManager.persist(object);
        return Optional.ofNullable(object);
    }


    @Override
    public Optional<Users> update(Users object) {
        return Optional.ofNullable(entityManager.merge(object));
    }


    @Override
    public Optional<Users> delete(Long id) {
        Users user = entityManager.find(Users.class,id);
        user.setDeleted(true);
        return update(user);
    }

    @Override
    public Optional<Object> findByIdentification(String identification) {
        try {
            Query query =  entityManager.createQuery("select u from Users u where u.identification=?1", Users.class);
            query.setParameter(1, identification);
            return Optional.ofNullable(query.getSingleResult());
        }catch (NoResultException e){
            return Optional.empty();
        }
    }

}
