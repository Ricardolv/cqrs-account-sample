package com.richard.cqrs.infrastructure.resources.requests;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class AccountCreationRequest {
    private final BigDecimal initialBalance;
    private final String owner;
}
