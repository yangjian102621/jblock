package org.rockyang.jblock.client.cmd;

import org.rockyang.jblock.client.cmd.utils.CliContext;

/**
 * @author yangjian
 */
public class Send extends Command {

	public Send()
	{
		this.name = "send";
		this.usage = "Send funds between accounts";
	}

	@Override
	public void action(CliContext context)
	{

	}
}
