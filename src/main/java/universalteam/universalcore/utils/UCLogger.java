package universalteam.universalcore.utils;

import universalteam.universalcore.libs.ReferenceCore;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UCLogger
{
	protected Logger logger;
	protected String modID;
	protected boolean hasSubName = false;
	protected String subName;
	protected boolean canLog = true;

	public UCLogger()
	{
		this(ReferenceCore.MODID);
	}

	public UCLogger(String modID)
	{
		this.modID = modID;
		logger = Logger.getLogger(modID);
	}

	public UCLogger setSubName(String name)
	{
		this.hasSubName = true;
		this.subName = name;
		return this;
	}

	public UCLogger setCanLog(boolean value)
	{
		this.canLog = value;
		return this;
	}

	protected String getName()
	{
		String name = "[" + this.modID + "] ";
		if (hasSubName)
			name += "[" + this.subName + "] ";

		return name;
	}

	protected void log(Level level, String s)
	{
		if (this.canLog)
		{
			if (level.equals(CustomLevel.DEBUG) && ObfuscationUtil.isObfuscated())
				return;

			logger.log(level, s);
		}
	}

	public void off(String s)
	{
		log(Level.OFF, getName() + s);
	}

	public void off(String s, Object... obj)
	{
		off(String.format(s, obj));
	}

	public void severe(String s)
	{
		log(Level.OFF, getName() + s);
	}

	public void severe(String s, Object... obj)
	{
		severe(String.format(s, obj));
	}

	public void warning(String s)
	{
		log(Level.WARNING, getName() + s);
	}

	public void warning(String s, Object... obj)
	{
		warning(String.format(s, obj));
	}

	public void info(String s)
	{
		log(Level.INFO, getName() + s);
	}

	public void info(String s, Object... obj)
	{
		info(String.format(s, obj));
	}

	public void config(String s)
	{
		log(Level.CONFIG, getName() + s);
	}

	public void config(String s, Object... obj)
	{
		config(String.format(s, obj));
	}

	public void fine(String s)
	{
		log(Level.FINE, getName() + s);
	}

	public void fine(String s, Object... obj)
	{
		fine(String.format(s, obj));
	}

	public void finer(String s)
	{
		log(Level.FINER, getName() + s);
	}

	public void finer(String s, Object... obj)
	{
		finer(String.format(s, getName() + obj));
	}

	public void finest(String s)
	{
		log(Level.FINEST, getName() + s);
	}

	public void finest(String s, Object... obj)
	{
		finest(String.format(s, obj));
	}

	public void all(String s)
	{
		log(Level.ALL, getName() + s);
	}

	public void all(String s, Object... obj)
	{
		all(String.format(s, obj));
	}

	public void debug(String s)
	{
		log(CustomLevel.DEBUG, getName() + s);
	}

	public void debug(String s, Object... obj)
	{
		debug(String.format(s, obj));
	}
}


class CustomLevel extends Level
{
	public static final Level DEBUG = new CustomLevel("DEBUG", Level.ALL.intValue() + 1);

	public CustomLevel(String name, int value)
	{
		super(name, value);
	}
}
