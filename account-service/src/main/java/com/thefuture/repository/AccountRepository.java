package com.thefuture.repository;

import com.thefuture.document.AccountDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends ElasticsearchRepository<AccountDocument, String> {
    AccountDocument findByPublicId(String accountHolder);
}

