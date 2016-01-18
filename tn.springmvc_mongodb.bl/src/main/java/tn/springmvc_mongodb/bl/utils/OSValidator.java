package tn.springmvc_mongodb.bl.utils;

public class OSValidator {

	private static String OS = System.getProperty("os.name").toLowerCase();

	public static String getArialuniFont() {
		/*
		 * download and put font in specific folder accordingly to OS
		 * https://ipwn.googlecode.com/files/arialuni.ttf
		 */

		if (OSValidator.isWindows())
			return "c:/windows/fonts/arialuni.ttf";
		else if (OSValidator.isUnix())
			return "/usr/fonts/arialuni.ttf";
		else
			return null;

	}

	public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		return (OS.indexOf("mac") >= 0);

	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS
				.indexOf("aix") > 0);

	}

	public static boolean isSolaris() {

		return (OS.indexOf("sunos") >= 0);

	}
}
