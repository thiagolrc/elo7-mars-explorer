package com.elo7.marsexplorer.dto;

import java.util.ArrayList;
import java.util.List;

import com.elo7.marsexplorer.domain.NavigationCommand;

public class CommandSequenceDTO {

	private List<NavigationCommand> commands = new ArrayList<>();

	public List<NavigationCommand> getCommands() {
		return commands;
	}

	public void setCommands(List<NavigationCommand> commands) {
		this.commands = commands;
	}

}
