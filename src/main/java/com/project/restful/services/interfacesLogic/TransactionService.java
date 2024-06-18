package com.project.restful.services.interfacesLogic;

import com.project.restful.dtos.auth.TokenDto;
import com.project.restful.dtos.transaction.TransactionDto;
import com.project.restful.dtos.transaction.TransactionalResponseDto;
import com.project.restful.models.Transaction;

import java.util.List;

public interface TransactionService {

    TransactionalResponseDto generateTransaction(TransactionDto transactionDto);

    List<TransactionalResponseDto> getAllTransactionUser(TokenDto tokenDto);

    TransactionalResponseDto convert(Transaction transaction);
}
