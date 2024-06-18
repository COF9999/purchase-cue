package com.project.restful.Repository.classCrud;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.Repository.interfacesLogic.TransactionDao;
import com.project.restful.models.Products;
import com.project.restful.models.Transaction;
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
public class TransactionDaoRepository implements DaoCrud<Transaction>,TransactionDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<Transaction> findTransactionByPublication(Long idPublication) {
        try {
            Query query =  entityManager.createQuery("select t from Transaction t where t.offers.publications.id =?1",Transaction.class);
            query.setParameter(1,idPublication);
            return Optional.ofNullable((Transaction) query.getSingleResult());
        }catch (NoResultException e){
           return Optional.empty();
        }

    }

    @Override
    public List<Transaction> getAllTransactionOfUser(Long idUser) {
        Query query =  entityManager.createQuery("select t from Transaction t where t.userSeller.id =?1 or t.offers.users.id = ?1",Transaction.class);
        query.setParameter(1,idUser);
        return query.getResultList();
    }

    @Override
    public List<Transaction> selectAll() {
        return List.of();
    }

    @Override
    public Optional<Transaction> get(Long id) {
        return Optional.ofNullable(entityManager.find(Transaction.class,id));
    }

    @Override
    public Optional<Transaction> create(Transaction transaction) {
        entityManager.persist(transaction);
        return Optional.ofNullable(transaction);
    }

    @Override
    public Optional<Transaction> update(Transaction object) {
        return Optional.ofNullable(entityManager.merge(object));
    }

    @Override
    public Optional<Transaction> delete(Long id) {
        return Optional.empty();
    }
}
