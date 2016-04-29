package com.elo7.marsexplorer.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.util.ReflectionTestUtils;

import com.elo7.marsexplorer.Application;
import com.elo7.marsexplorer.domain.CardinalDirection;
import com.elo7.marsexplorer.domain.Plateau;
import com.elo7.marsexplorer.domain.Position;
import com.elo7.marsexplorer.domain.Probe;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

/**
 * Tetes para {@link ProbeRepository}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DirtiesContext
@DatabaseSetup("/dbunit/dataset.xml")
public class ProbeRepositoryTest {

	@Autowired
	private ProbeRepository probeRepository;
	@Autowired
	private PlateauRepository plateauRepository;

	@Test
	public void saveShouldAssignIdAndPersitProperties() {
		Position position = new Position(2, 3, CardinalDirection.N);
		Plateau plateau = plateauRepository.findOne(1);

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

	@Test(expected = DataIntegrityViolationException.class)
	public void saveShouldFailIfNoPositionIsSet() {
		Plateau plateau = plateauRepository.findOne(1);
		Probe probe = new Probe();
		ReflectionTestUtils.setField(probe, "plateau", plateau);

		probeRepository.save(probe);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void saveShouldFailIfPositionWithNullDiretionIsSet() {
		Position position = new Position(2, 3, null);
		Plateau plateau = plateauRepository.findOne(1);

		Probe probe = new Probe(position, plateau);

		probeRepository.save(probe);
	}

	@Test
	public void findByIdAndPlateauIdShoulFindProbe() {
		Assert.assertNotNull(probeRepository.findByIdAndPlateauId(2, 1));// from dataset
	}

	@Test
	public void findByPlateauIdShoulFindAllProblesFromPlateau() {
		List<Probe> probes = probeRepository.findByPlateauId(1);
		Assert.assertEquals(2, probes.size());// do dataset
	}

}
