package com.richard.cqrs.infrastructure.persistence.projections;

import com.richard.cqrs.application.exceptions.AccountNotFoundException;
import com.richard.cqrs.infrastructure.axon.events.AccountCreatedEvent;
import com.richard.cqrs.infrastructure.axon.events.MoneyCreditedEvent;
import com.richard.cqrs.infrastructure.axon.events.MoneyDebitedEvent;
import com.richard.cqrs.infrastructure.axon.queries.FindBankAccountQuery;
import com.richard.cqrs.infrastructure.persistence.entities.BankAccountEntity;
import com.richard.cqrs.infrastructure.persistence.repositories.BankAccountEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class BankAccountProjection {

    private final BankAccountEntityRepository repository;

    @EventHandler
    public void on(AccountCreatedEvent event) {
        log.debug("Handling a Bank Account creation command {}", event.getId());
        BankAccountEntity bankAccount = new BankAccountEntity(
                event.getId(),
                event.getOwner(),
                event.getInitialBalance()
        );
        this.repository.save(bankAccount);
    }

    @EventHandler
    public void on(MoneyCreditedEvent event) throws AccountNotFoundException {
        log.debug("Handling an Account Credit command {}", event.getId());
        Optional<BankAccountEntity> optionalBankAccount = this.repository.findById(event.getId());
        if (optionalBankAccount.isPresent()) {
            var bankAccount = optionalBankAccount.get();
            bankAccount.setBalance(bankAccount.getBalance().add(event.getCreditAmount()));
            this.repository.save(bankAccount);
        } else {
            throw new AccountNotFoundException(event.getId());
        }
    }

    @EventHandler
    public void on(MoneyDebitedEvent event) throws AccountNotFoundException {
        log.debug("Handling an Account Debit command {}", event.getId());
        Optional<BankAccountEntity> optionalBankAccount = this.repository.findById(event.getId());
        if (optionalBankAccount.isPresent()) {
            var bankAccount = optionalBankAccount.get();
            bankAccount.setBalance(bankAccount.getBalance().subtract(event.getDebitAmount()));
            this.repository.save(bankAccount);
        } else {
            throw new AccountNotFoundException(event.getId());
        }
    }

    @QueryHandler
    public BankAccountEntity handle(FindBankAccountQuery query) {
        log.debug("Handling FindBankAccountQuery query: {}", query);
        return this.repository.findById(query.getAccountId()).orElse(null);
    }

}
