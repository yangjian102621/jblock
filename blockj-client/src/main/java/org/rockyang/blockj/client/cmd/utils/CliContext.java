package org.rockyang.blockj.client.cmd.utils;

import org.apache.commons.lang3.StringUtils;
import org.rockyang.blockj.base.utils.CmdArgsParser;

import java.math.BigDecimal;

/**
 * @author yangjian
 */
public class CliContext {
	private String[] args;
	private final CmdArgsParser parser;

	public CliContext(String[] args)
	{
		this.args = args;
		this.parser = CmdArgsParser.getInstance(args);
	}

	public String[] getArgs()
	{
		return args;
	}

	public void setArgs(String[] args)
	{
		this.args = args;
	}

	public String getArg(int index)
	{
		return parser.getArgs().get(index);
	}

	public String getOption(String key)
	{
		return parser.getOption(key);
	}

	public String getOption(String key, String defaultValue)
	{
		return parser.getOption(key, defaultValue);
	}

	public Integer getInt(String key)
	{
		return parser.getIntOption(key);
	}

	public Integer getInt(String key, Integer defaultValue)
	{
		return parser.getIntOption(key, defaultValue);
	}

	public Boolean getBool(String key)
	{
		return parser.getBoolOption(key);
	}

	public Boolean getBool(String key, boolean defaultValue)
	{
		return parser.getBoolOption(key, defaultValue);
	}

	public BigDecimal getBigDecimal(String key)
	{
		String option = getOption(key);
		if (StringUtils.isBlank(option)) {
			return null;
		}

		return BigDecimal.valueOf(Long.valueOf(option));
	}

	public BigDecimal getBigDecimal(String key, BigDecimal defaultValue)
	{
		String option = getOption(key);
		if (StringUtils.isBlank(option)) {
			return defaultValue;
		}

		try {
			return BigDecimal.valueOf(Long.valueOf(option));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public Long getLong(String key)
	{
		String option = getOption(key);
		if (StringUtils.isBlank(option)) {
			return null;
		}

		return Long.valueOf(option);
	}

	public Long getLong(String key, Long defaultValue)
	{
		String option = getOption(key);
		if (StringUtils.isBlank(option)) {
			return null;
		}

		return Long.valueOf(option);
	}
}
