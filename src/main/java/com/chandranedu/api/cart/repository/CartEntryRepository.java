package com.chandranedu.api.cart.repository;


import com.chandranedu.api.cart.beans.CartEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartEntryRepository extends JpaRepository<CartEntry, Long> {

    @Query("SELECT c FROM CartEntry c where c.code = :code")
    Optional<CartEntry> findCartEntryByCode(@Param("code") String code);
}
