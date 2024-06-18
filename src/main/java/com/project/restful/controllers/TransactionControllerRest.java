package com.project.restful.controllers;

import com.project.restful.dtos.auth.TokenDto;
import com.project.restful.dtos.transaction.TransactionDto;
import com.project.restful.dtos.transaction.TransactionalResponseDto;
import com.project.restful.services.interfacesLogic.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/transaction")
public class TransactionControllerRest {

    @Autowired
    @Qualifier("transactionServiceImpl")
    private TransactionService transactionService;

    @PostMapping("/")
    public TransactionalResponseDto generateTransaction(@RequestBody TransactionDto transactionDto){
        return transactionService.generateTransaction(transactionDto);
    }

    @PostMapping("/get-all")
    public List<TransactionalResponseDto> getAllTransactionUser(@RequestBody TokenDto transactionDto){
        return transactionService.getAllTransactionUser(transactionDto);
    }
}
