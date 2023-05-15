package com.aninfo.service;

import java.util.Collection;
import java.util.Optional;

// import javax.transaction.InvalidTransactionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    public Transaction save(Long cbu, Double amount, String type){
        Account account = accountService.unwrapAccount(accountService.findById(cbu), cbu);
        Transaction transaction = new Transaction(account, amount, type);
        return transactionRepository.save(transaction);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Collection<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public Collection<Transaction> findByAccountCbu(Long cbu) {
        return transactionRepository.findByAccountCbu(cbu);
    }
    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    public Transaction createWithdraw(Account account, Double amount) {
        if (account.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        return new Transaction(account, amount, "WITHDRAW");
    }

    public Transaction createDeposit(Account account, Double amount) {
        if (amount <= 0) {
            throw new DepositNegativeSumException("Cannot deposit negative sums");
        }
        return new Transaction(account, amount, "DEPOSIT");
    }
}
