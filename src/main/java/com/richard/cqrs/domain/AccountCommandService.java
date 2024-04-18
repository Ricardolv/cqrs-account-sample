package com.richard.cqrs.domain;

import com.richard.cqrs.infrastructure.axon.commands.CreateAccountCommand;
import com.richard.cqrs.infrastructure.axon.commands.CreditMoneyCommand;
import com.richard.cqrs.infrastructure.axon.commands.DebitMoneyCommand;
import com.richard.cqrs.infrastructure.persistence.entities.BankAccountEntity;
import com.richard.cqrs.infrastructure.resources.requests.AccountCreationRequest;
import com.richard.cqrs.infrastructure.resources.requests.MoneyAmountRequest;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.richard.cqrs.domain.ServiceUtils.formatUuid;

@Service
@AllArgsConstructor
public class AccountCommandService {

    private final CommandGateway commandGateway;

    public CompletableFuture<BankAccountEntity> createAccount(AccountCreationRequest request) {
        return this.commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID(),
                request.getInitialBalance(),
                request.getOwner()
        ));
    }

    public CompletableFuture<String> creditMoneyToAccount(String accountId, MoneyAmountRequest request) {
        return this.commandGateway.send(new CreditMoneyCommand(
                formatUuid(accountId),
                request.getAmount()
        ));
    }

    public CompletableFuture<String> debitMoneyFromAccount(String accountId, MoneyAmountRequest request) {
        return this.commandGateway.send(new DebitMoneyCommand(
                formatUuid(accountId),
                request.getAmount()
        ));
    }
}
