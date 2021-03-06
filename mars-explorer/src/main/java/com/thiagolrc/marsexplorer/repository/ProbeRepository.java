package com.thiagolrc.marsexplorer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thiagolrc.marsexplorer.domain.Probe;

/**
 * {@link JpaRepository} para {@link Probe}
 *
 */
public interface ProbeRepository extends JpaRepository<Probe, Integer> {

	/**
	 * Busca uma sonda por seu ID já garantindo que a mesma pertença a um determinado plateu
	 * 
	 * @param probeId
	 * @param plateauId
	 * @return
	 */
	Probe findByIdAndPlateauId(int probeId, int plateauId);

	/**
	 * Busca todas as sondas de um planalto
	 * 
	 * @param plateauId
	 * @return
	 */
	List<Probe> findByPlateauId(int plateauId);

}
