package com.green.acamatch.academyCost;

import com.green.acamatch.entity.academyCost.AcademyCost;
import com.green.acamatch.entity.academyCost.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface AcademyCostRepository extends JpaRepository<AcademyCost, Long> {
        @Modifying
        @Transactional
        @Query("UPDATE AcademyCost a SET a.cost_status = :status WHERE a.tId = :tid")
        void updateCostStatus(@Param("status") int status, @Param("tid") String tid);


        @Query("SELECT a FROM AcademyCost a WHERE a.createdAt >= :oneMonthAgo")
        List<AcademyCost> findRecentPayments(@Param("oneMonthAgo") LocalDateTime oneMonthAgo);

        @Query("SELECT a FROM AcademyCost a WHERE a.userId = :userId AND a.createdAt >= :oneMonthAgo")
        List<AcademyCost> findRecentPaymentsByUserId(@Param("userId") Long userId,
                                                     @Param("oneMonthAgo") LocalDateTime oneMonthAgo);

        @Query("SELECT a FROM AcademyCost a WHERE a.tId = :tId")
        List<AcademyCost> findByTId(@Param("tId") String tId);

        @Modifying
        @Transactional
        @Query("DELETE FROM AcademyCost ac WHERE ac.cost_status = 0 AND ac.createdAt < :timeLimit")
        int deleteExpiredPayments(@Param("timeLimit") LocalDateTime timeLimit);

}
