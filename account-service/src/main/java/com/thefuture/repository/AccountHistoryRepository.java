package com.thefuture.repository;

import com.thefuture.entity.AccountHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountHistoryRepository extends JpaRepository<AccountHistoryEntity, Long> {

    @Query(value = "SELECT * FROM account_history WHERE public_id = :publicId ORDER BY change_time DESC", nativeQuery = true)
    List<AccountHistoryEntity> findHistoryByPublicId(@Param("publicId") String publicId);
}
