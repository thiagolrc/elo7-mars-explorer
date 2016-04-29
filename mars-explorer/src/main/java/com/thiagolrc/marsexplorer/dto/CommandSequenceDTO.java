package com.thiagolrc.marsexplorer.dto;

import java.util.ArrayList;
import java.util.List;

import com.thiagolrc.marsexplorer.domain.NavigationCommand;

public class CommandSequenceDTO {

	private List<NavigationCommand> commands = new ArrayList<>();

	private List<String> positionChangeLog = new ArrayList<>();

	public List<NavigationCommand> getCommands() {
		return commands;
	}

	public List<String> getPositionChangeLog() {
		return positionChangeLog;
	}

}
