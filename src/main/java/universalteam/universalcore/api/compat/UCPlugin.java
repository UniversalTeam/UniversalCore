package universalteam.universalcore.api.compat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UCPlugin
{
	/**
	 * The mod ID of the mod this plugin is targeted on.
	 */
	String targetID();

	/**
	 * The author of this plugin
	 */
	String author() default "UNKNOWN";

	/**
	 * The required mod IDs that are needed for this plugin to load.
	 * If one of the required mod IDs is not loaded already, this plugin won't load.
	 * Multiple mod IDs are separated by a ';' token.
	 */
	String requires() default "";

	/**
	 * All contents of the method that has this annotation, will get loaded and handled.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface Handle
	{

	}

	/**
	 * All contents of the method that has this annotation, will get loaded and handled.
	 * This will only happen client side.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface HandleClient
	{

	}
}
