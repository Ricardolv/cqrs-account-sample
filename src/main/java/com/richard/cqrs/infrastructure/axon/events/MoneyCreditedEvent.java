package com.richard.cqrs.infrastructure.axon.events;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class MoneyCreditedEvent {

    private final UUID id;
    private final BigDecimal creditAmount;

}
