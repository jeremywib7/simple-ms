package com.thefuture.repository;

import com.thefuture.entity.AccountEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountEntityRepository extends ListCrudRepository<AccountEntity, String> {
    AccountEntity findByPublicId(String accountHolder);
}

