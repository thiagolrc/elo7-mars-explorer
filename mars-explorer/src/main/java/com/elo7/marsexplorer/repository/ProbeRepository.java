package com.elo7.marsexplorer.repository;

import org.springframework.data.repository.CrudRepository;

import com.elo7.marsexplorer.probe.Plateau;
import com.elo7.marsexplorer.probe.Probe;

public interface ProbeRepository extends CrudRepository<Probe, Integer> {

}
