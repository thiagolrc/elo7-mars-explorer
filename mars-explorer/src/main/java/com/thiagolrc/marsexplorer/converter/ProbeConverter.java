package com.thiagolrc.marsexplorer.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.thiagolrc.marsexplorer.domain.Plateau;
import com.thiagolrc.marsexplorer.domain.Position;
import com.thiagolrc.marsexplorer.domain.Probe;
import com.thiagolrc.marsexplorer.dto.ProbeDTO;

/**
 * Conversor de {@link Probe} para {@link ProbeDTO} e vice-versa.
 * <p>
 * Usado para converter um objeto de domínio para uma versão simplificada para uso nos serviços
 * </p>
 *
 */
@Component
public class ProbeConverter {

	/**
	 * Cria o objeto de domínio a partid do DTO
	 * 
	 * @param dto
	 *            {@link ProbeDTO}
	 * @param plateu
	 *            {@link Plateau}
	 * @return {@link Probe}
	 */
	public Probe fromDTO(ProbeDTO dto, Plateau plateu) {
		Position position = new Position(dto.getX(), dto.getY(), dto.getDirection());
		Probe probe = new Probe(dto.getId(), position, plateu);
		return probe;
	}

	/**
	 * Cria um DTO a partir do objeto de domínio
	 * 
	 * @param probe
	 *            {@link Probe}
	 * @return {@link ProbeDTO}
	 */
	public ProbeDTO toDTO(Probe probe) {
		ProbeDTO dto = new ProbeDTO();
		BeanUtils.copyProperties(probe, dto);// copia id
		BeanUtils.copyProperties(probe.getPosition(), dto);// copia coordenadas
		return dto;
	}
}
