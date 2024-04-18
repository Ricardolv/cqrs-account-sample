package com.richard.cqrs.infrastructure.resources;

import com.richard.cqrs.domain.AccountQueryService;
import com.richard.cqrs.infrastructure.persistence.entities.BankAccountEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/accounts")
@AllArgsConstructor
public class AccountQueryResource {

    private final AccountQueryService accountQueryService;

    @GetMapping("/{accountId}")
    public CompletableFuture<BankAccountEntity> findById(@PathVariable("accountId") String accountId) {
        return this.accountQueryService.finzdById(accountId);
    }

    @GetMapping("/{accountId}/events")
    public List<Object> listEventsForAccount(@PathVariable(value = "accountId") String accountId) {
        return this.accountQueryService.listEventsForAccount(accountId);
    }
}
