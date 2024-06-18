package com.project.restful.Repository.classCrud;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.Repository.interfacesLogic.OfferDao;
import com.project.restful.models.Offers;
import com.project.restful.models.Publications;
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
public class OfferDaoRepository implements DaoCrud<Offers>,OfferDao {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public List<Users> findUserInOffer(Long id,String identification) {
        Query query = entityManager.createQuery("select o from Offers o where o.publications.id =?1 and o.users.identification = ?2 and o.state=1");
        query.setParameter(1,id);
        query.setParameter(2,identification);
        return query.getResultList();
    }

    @Override
    public List<Offers> allOffersByPublicationId(Long id, Byte state) {
        Query query = entityManager.createQuery("select of from Offers of where of.publications.id =?1 and of.state=?2");
        query.setParameter(1,id);
        query.setParameter(2,state);
        return query.getResultList();
    }

    @Override
    public List<Offers> productInOffers(Long idProduct,Byte state) {
        Query query = entityManager.createQuery("select of from Offers of where of.product.id =?1 and of.state=?2");
        query.setParameter(1,idProduct);
        query.setParameter(2,state);
        return query.getResultList();
    }

    @Override
    public List<Offers> searchOffersOfUser(Long idUser) {
        Query query = entityManager.createQuery("select of from Offers of where of.users.id =?1");
        query.setParameter(1,idUser);


        return query.getResultList();
    }


    @Override
    public List<Offers> selectAll() {
        return List.of();
    }

    @Override
    public Optional<Offers> get(Long id) {
        return Optional.ofNullable(entityManager.find(Offers.class,id));
    }

    @Override
    public Optional<Offers> create(Offers object) {
        entityManager.persist(object);
        return Optional.ofNullable(object);
    }

    @Override
    public Optional<Offers> update(Offers object) {
        return Optional.ofNullable(object);
    }

    @Override
    public Optional<Offers> delete(Long id) {
        return Optional.empty();
    }
}
