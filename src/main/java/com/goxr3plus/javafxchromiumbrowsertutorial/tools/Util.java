package main.java.com.goxr3plus.javafxchromiumbrowsertutorial.tools;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.teamdev.jxbrowser.chromium.Browser;

import javafx.application.Platform;

public class Util {
	
	public enum OS {
		WINDOWS, LINUX, MAC, SOLARIS
	}// Operating systems.
	
	private static OS os = null;
	private static final String OPERATING_SYSTEM = System.getProperty("os.name").toLowerCase();
	
	public static OS getOS() {
		if (os == null) {
			if (OPERATING_SYSTEM.contains("win"))
				os = OS.WINDOWS;
			else if (OPERATING_SYSTEM.contains("nix") || OPERATING_SYSTEM.contains("nux") || OPERATING_SYSTEM.contains("aix")) {
				os = OS.LINUX;
			} else if (OPERATING_SYSTEM.contains("mac"))
				os = OS.MAC;
			else if (OPERATING_SYSTEM.contains("sunos"))
				os = OS.SOLARIS;
		}
		return os;
	}
	
	public static boolean isWindows() {
		return OPERATING_SYSTEM.contains("win");
	}
	
	public static boolean isLinux() {
		return ( OPERATING_SYSTEM.contains("nix") || OPERATING_SYSTEM.contains("nux") || OPERATING_SYSTEM.contains("aix") );
	}
	
	public static boolean isMac() {
		return OPERATING_SYSTEM.contains("mac");
	}
	
	public static boolean isSolaris() {
		return OPERATING_SYSTEM.contains("sunos");
	}
	
	/**
	 * Returns the absolute path of the current directory in which the given class file is.
	 * 
	 * @param classs
	 *            * @return The absolute path of the current directory in which the class file is. <b>[it ends with File.Separator!!]</b>
	 * @author GOXR3PLUS[StackOverFlow user] + bachden [StackOverFlow user]
	 */
	public static final String getBasePathForClass(Class<?> classs) {
		
		// Local variables
		File file;
		String basePath = "";
		boolean failed = false;
		
		// Let's give a first try
		try {
			file = new File(classs.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			
			basePath = ( file.isFile() || file.getPath().endsWith(".jar") || file.getPath().endsWith(".zip") ) ? file.getParent() : file.getPath();
		} catch (URISyntaxException ex) {
			failed = true;
			Logger.getLogger(classs.getName()).log(Level.WARNING, "Cannot firgue out base path for class with way (1): ", ex);
		}
		
		// The above failed?
		if (failed)
			try {
				file = new File(classs.getClassLoader().getResource("").toURI().getPath());
				basePath = file.getAbsolutePath();
				
				// the below is for testing purposes...
				// starts with File.separator?
				// String l = local.replaceFirst("[" + File.separator +
				// "/\\\\]", "")
			} catch (URISyntaxException ex) {
				Logger.getLogger(classs.getName()).log(Level.WARNING, "Cannot firgue out base path for class with way (2): ", ex);
			}
		
		// fix to run inside Eclipse
		if (basePath.endsWith(File.separator + "lib") || basePath.endsWith(File.separator + "bin") || basePath.endsWith("bin" + File.separator)
				|| basePath.endsWith("lib" + File.separator)) {
			basePath = basePath.substring(0, basePath.length() - 4);
		}
		// fix to run inside NetBeans
		if (basePath.endsWith(File.separator + "build" + File.separator + "classes")) {
			basePath = basePath.substring(0, basePath.length() - 14);
		}
		// end fix
		if (!basePath.endsWith(File.separator))
			basePath += File.separator;
		
		return basePath;
	}
	
	/**
	 * The Type of File
	 * 
	 * @author GOXR3PLUS
	 *
	 */
	public enum FileType {
		DIRECTORY, FILE;
	}
	
	/**
	 * Creates the given File or Folder if not exists and returns the result
	 * 
	 * @param absoluteFilePath
	 *            The absolute path of the File|Folder
	 * @param fileType
	 *            Create DIRECTORY OR FILE ?
	 * @return True if exists or have been successfully created , otherwise false
	 */
	public static boolean createFileOrFolder(String absoluteFilePath , FileType fileType) {
		return createFileOrFolder(new File(absoluteFilePath), fileType);
	}
	
	/**
	 * Creates the given File or Folder if not exists and returns the result
	 * 
	 * @param absoluteFilePath
	 *            The absolute path of the File|Folder
	 * @param fileType
	 *            Create DIRECTORY OR FILE ?
	 * @return True if exists or have been successfully created , otherwise false
	 */
	public static boolean createFileOrFolder(File file , FileType fileType) {
		//Already exists?
		if (file.exists())
			return true;
		//Directory?
		if (fileType == FileType.DIRECTORY)
			return file.mkdir();
		//File?
		try {
			return file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Use this code to terminate XR3Player
	 * 
	 * @param code
	 */
	public static void terminateApplication(int code , Browser browser) {
		
		System.out.println("Dis All->" + Util.getOS());
		switch (Util.getOS()) {
			case WINDOWS:
				new Thread(() -> {
					//Disposing all Browsers...
					browser.dispose();
					System.exit(code);
				}).start();
				break;
			case LINUX:
			case MAC:
				Platform.runLater(() -> {
					//Disposing all Browsers...
					browser.dispose();
					System.exit(code);
				});
				break;
			default:
				System.out.println("Can't dispose browser instance!!!");
				break;
		}
		
	}
}
