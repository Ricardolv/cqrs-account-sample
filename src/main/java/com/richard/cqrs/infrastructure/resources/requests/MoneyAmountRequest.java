package com.richard.cqrs.infrastructure.resources.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class MoneyAmountRequest {
    private BigDecimal amount;
}
