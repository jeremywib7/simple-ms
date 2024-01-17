package com.thefuture.accountservice.repository;

import com.thefuture.accountservice.document.AccountDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends ElasticsearchRepository<AccountDocument, String> {
    AccountDocument findByPublicId(String accountHolder);
}

