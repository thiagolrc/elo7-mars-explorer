package com.elo7.marsexplorer.probe;

import java.util.ArrayList;
import java.util.List;

public class CommandSequenceDTO {

	private List<NavigationCommand> commands = new ArrayList<>();

	public List<NavigationCommand> getCommands() {
		return commands;
	}

	public void setCommands(List<NavigationCommand> commands) {
		this.commands = commands;
	}

}
