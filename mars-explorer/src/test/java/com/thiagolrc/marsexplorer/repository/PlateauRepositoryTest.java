package com.thiagolrc.marsexplorer.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.thiagolrc.marsexplorer.Application;
import com.thiagolrc.marsexplorer.domain.Plateau;
import com.thiagolrc.marsexplorer.repository.PlateauRepository;

/**
 * Tetes para {@link PlateauRepository}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DirtiesContext
@DatabaseSetup("/dbunit/dataset.xml")
public class PlateauRepositoryTest {

	@Autowired
	private PlateauRepository plateauRepo;

	@Test
	public void saveShouldAssignIdAndPersitProperties() {
		Plateau savedPlateau = plateauRepo.save(new Plateau(2, 3));
		Assert.assertTrue(savedPlateau.getId() > 1);// o dataset ja tem 1 plateu
		Assert.assertEquals(2, savedPlateau.getX());
		Assert.assertEquals(3, savedPlateau.getY());
	}

	@Test

	public void findeOndeShoulReturnPlateau() {
		// somente para testar o dbunit
		Plateau plateau = plateauRepo.findOne(1);
		Assert.assertNotNull(plateau);
		Assert.assertEquals(9998, plateau.getX());
		Assert.assertEquals(9999, plateau.getY());
	}

}
