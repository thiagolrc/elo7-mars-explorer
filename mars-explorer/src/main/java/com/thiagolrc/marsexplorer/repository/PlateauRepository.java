package com.thiagolrc.marsexplorer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thiagolrc.marsexplorer.domain.Plateau;

/**
 * {@link JpaRepository} para {@link Plateau}
 *
 */
public interface PlateauRepository extends JpaRepository<Plateau, Integer> {

}
