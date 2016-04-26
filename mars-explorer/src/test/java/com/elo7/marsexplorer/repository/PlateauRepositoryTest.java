package com.elo7.marsexplorer.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elo7.marsexplorer.Application;
import com.elo7.marsexplorer.probe.Plateau;

/**
 * Tetes para {@link PlateauRepository}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class PlateauRepositoryTest {

	@Autowired
	private PlateauRepository plateauRepo;

	@Test
	public void saveShouldAssignIdAndPersitProperties() {
		Plateau savedPlateau = plateauRepo.save(new Plateau(2, 3));
		Assert.assertTrue(savedPlateau.getId() > 0);
		Assert.assertEquals(2, savedPlateau.getX());
		Assert.assertEquals(3, savedPlateau.getY());
	}

}
