package com.aninfo.controller;
import com.aninfo.model.Transaction;
import com.aninfo.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;


import springfox.documentation.swagger2.annotations.EnableSwagger2;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@SpringBootApplication
@EnableSwagger2
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transactions")
	@ResponseStatus(HttpStatus.CREATED)
	public Transaction createTransaction(@RequestBody Transaction transaction) { // arreglar
		return transactionService.createTransaction(transaction);
	}

    @GetMapping("/transactions/account/{cbu}")
	public Collection<Transaction> getAccountTransactions(@PathVariable Long cbu) {
		return transactionService.findByAccountCbu(cbu);
	}

    @GetMapping("/transactions/{id}")
	public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) {
		Optional<Transaction> transactionOptional = transactionService.findById(id);
		return ResponseEntity.of(transactionOptional);
	}

    @DeleteMapping("/transactions/{id}")
	public void deleteTransaction(@PathVariable Long id) {
		transactionService.deleteById(id);
	}
}
