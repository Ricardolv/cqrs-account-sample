package com.richard.cqrs.infrastructure.resources;

import com.richard.cqrs.domain.AccountCommandService;
import com.richard.cqrs.infrastructure.persistence.entities.BankAccountEntity;
import com.richard.cqrs.infrastructure.resources.requests.AccountCreationRequest;
import com.richard.cqrs.infrastructure.resources.requests.MoneyAmountRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/accounts")
@AllArgsConstructor
public class AccountCommandResource {

    private final AccountCommandService accountCommandService;

    @PostMapping
    @ResponseStatus(value = CREATED)
    public CompletableFuture<BankAccountEntity> createAccount(@RequestBody AccountCreationRequest request) {
        return this.accountCommandService.createAccount(request);
    }

    @PutMapping(value = "/credit/{accountId}")
    public CompletableFuture<String> creditMoneyToAccount(@PathVariable(value = "accountId") String accountId,
                                                          @RequestBody MoneyAmountRequest request) {
        return this.accountCommandService.creditMoneyToAccount(accountId, request);
    }

    @PutMapping(value = "/debit/{accountId}")
    public CompletableFuture<String> debitMoneyFromAccount(@PathVariable(value = "accountId") String accountId,
                                                           @RequestBody MoneyAmountRequest request) {
        return this.accountCommandService.debitMoneyFromAccount(accountId, request);
    }

}
