package com.project.restful.Repository.interfacesLogic;

import com.project.restful.models.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionDao {

    Optional<Transaction> findTransactionByPublication(Long idPublication);

    List<Transaction> getAllTransactionOfUser(Long idUser);
}
