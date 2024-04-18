package com.richard.cqrs.infrastructure.persistence.repositories;

import com.richard.cqrs.infrastructure.persistence.entities.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankAccountEntityRepository extends JpaRepository<BankAccountEntity, UUID> {
}
