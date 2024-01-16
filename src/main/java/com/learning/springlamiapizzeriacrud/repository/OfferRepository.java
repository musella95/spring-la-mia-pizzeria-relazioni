package com.learning.springlamiapizzeriacrud.repository;

import com.learning.springlamiapizzeriacrud.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
}
