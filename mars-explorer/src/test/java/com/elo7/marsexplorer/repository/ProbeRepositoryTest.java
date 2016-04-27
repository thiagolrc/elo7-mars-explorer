package com.elo7.marsexplorer.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elo7.marsexplorer.Application;
import com.elo7.marsexplorer.domain.CardinalDirection;
import com.elo7.marsexplorer.domain.Plateau;
import com.elo7.marsexplorer.domain.Position;
import com.elo7.marsexplorer.domain.Probe;

/**
 * Tetes para {@link ProbeRepository}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class ProbeRepositoryTest {

	@Autowired
	private ProbeRepository probeRepository;
	@Autowired
	private PlateauRepository plateauRepository;// TODO Trocar por um dataset para nao depender do plateauRepo funcionar

	@Test
	public void saveShouldAssignIdAndPersitProperties() {
		Position position = new Position(2, 3, CardinalDirection.N);
		Plateau plateau = plateauRepository.save(new Plateau(2, 3));

		Probe probe = new Probe(position, plateau);

		Probe savedProbe = probeRepository.save(probe);
		Assert.assertTrue(savedProbe.getId() > 0);
		Assert.assertEquals(2, savedProbe.getPosition().getX());
		Assert.assertEquals(3, savedProbe.getPosition().getY());
		Assert.assertEquals(CardinalDirection.N, savedProbe.getPosition().getDirection());
		Assert.assertEquals(plateau, savedProbe.getPlateau());
	}

	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void saveShouldFailIfNoPersitedPlateauIsSet() {
		Position position = new Position(2, 3, CardinalDirection.N);
		Probe probe = new Probe(position, new Plateau(2, 3));

		probeRepository.save(probe);
	}

}
