package com.concertn.localbands.repositories;


import com.concertn.localbands.domain.entities.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BandRepository extends JpaRepository<Band, UUID> {

    Optional<Band> findByNormalizedName(String normalizedName);
}
