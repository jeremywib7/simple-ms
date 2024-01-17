package com.thefuture.transactionservice.repository;

import com.thefuture.transactionservice.document.TransactionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<TransactionDocument, String> {
}
