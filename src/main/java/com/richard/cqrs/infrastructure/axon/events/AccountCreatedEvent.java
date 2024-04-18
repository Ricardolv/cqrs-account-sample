package com.richard.cqrs.infrastructure.axon.events;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data // Lombok
public class AccountCreatedEvent {
    private final UUID id;
    private final BigDecimal initialBalance;
    private final String owner;
}
