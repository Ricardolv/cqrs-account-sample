package com.richard.cqrs.domain;

import com.richard.cqrs.infrastructure.axon.queries.FindBankAccountQuery;
import com.richard.cqrs.infrastructure.persistence.entities.BankAccountEntity;
import lombok.AllArgsConstructor;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;
import org.axonframework.messaging.Message;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.richard.cqrs.domain.ServiceUtils.formatUuid;

@Service
@AllArgsConstructor
public class AccountQueryService {
    private final QueryGateway queryGateway;
    private final EventStore eventStore;


    public CompletableFuture<BankAccountEntity> finzdById(String accountId) {
        return this.queryGateway.query(
                new FindBankAccountQuery(formatUuid(accountId)),
                ResponseTypes.instanceOf(BankAccountEntity.class)
        );
    }

    public List<Object> listEventsForAccount(String accountId) {
        return this.eventStore
                .readEvents(formatUuid(accountId).toString())
                .asStream()
                .map(Message::getPayload)
                .collect(Collectors.toList());
    }
}
