package com.aninfo.service;

import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Collection<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Long cbu) {
        return accountRepository.findById(cbu);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public void deleteById(Long cbu) {
        accountRepository.deleteById(cbu);
    }

    @Transactional
    public Transaction withdraw(Long cbu, Double sum) {
        Account account = accountRepository.findAccountByCbu(cbu);
        Transaction transaction = transactionService.createWithdraw(account, sum);

        account.setBalance(account.getBalance() - sum);
        accountRepository.save(account);
        transactionService.save(transaction);
        return transaction;
    }

    @Transactional
    public Transaction deposit(Long cbu, Double sum) {
        Account account = accountRepository.findAccountByCbu(cbu);
        Transaction transaction = transactionService.createDeposit(account, sum);

        if (sum >= 2000) {
            sum += sum * 0.1 > 500 ? 500 : sum * 0.1;
            transaction.setAmount(sum);
        }
        account.setBalance(account.getBalance() + sum);
        accountRepository.save(account);
        transactionService.save(transaction);

        return transaction;
    }

    public Account unwrapAccount(Optional<Account> optAccount, Long cbu) {
        if (optAccount.isPresent()) return optAccount.get();
        else throw new RuntimeException("Invalid cbu");
    }

}
