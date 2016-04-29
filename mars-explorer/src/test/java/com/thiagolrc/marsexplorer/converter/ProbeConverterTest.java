package com.thiagolrc.marsexplorer.converter;

import org.junit.Assert;
import org.junit.Test;

import com.thiagolrc.marsexplorer.converter.ProbeConverter;
import com.thiagolrc.marsexplorer.domain.CardinalDirection;
import com.thiagolrc.marsexplorer.domain.Plateau;
import com.thiagolrc.marsexplorer.domain.Position;
import com.thiagolrc.marsexplorer.domain.Probe;
import com.thiagolrc.marsexplorer.dto.ProbeDTO;

public class ProbeConverterTest {

	private ProbeConverter converter = new ProbeConverter();

	@Test
	public void fromDTOTest() {
		ProbeDTO dto = new ProbeDTO();
		dto.setId(1);
		dto.setX(2);
		dto.setY(3);
		dto.setDirection(CardinalDirection.N);
		Plateau plateu = new Plateau(4,4);
		Probe probe = converter.fromDTO(dto, plateu);
		Assert.assertEquals(1, probe.getId());
		Assert.assertEquals(2, probe.getPosition().getX());
		Assert.assertEquals(3, probe.getPosition().getY());
		Assert.assertEquals(CardinalDirection.N, probe.getPosition().getDirection());
		Assert.assertEquals(plateu, probe.getPlateau());
	}

	@Test
	public void toDTOTest() {
		Probe probe = new Probe(1, new Position(2, 3, CardinalDirection.N), new Plateau(4, 4));
		
		ProbeDTO probeDTO = converter.toDTO(probe);
		Assert.assertEquals(1, probeDTO.getId());
		Assert.assertEquals(2, probeDTO.getX());
		Assert.assertEquals(3, probeDTO.getY());
		Assert.assertEquals(CardinalDirection.N, probeDTO.getDirection());
	}

}
