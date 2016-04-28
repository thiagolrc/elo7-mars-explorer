package com.elo7.marsexplorer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elo7.marsexplorer.domain.Plateau;

/**
 * {@link JpaRepository} para {@link Plateau}
 *
 */
public interface PlateauRepository extends JpaRepository<Plateau, Integer> {

}
