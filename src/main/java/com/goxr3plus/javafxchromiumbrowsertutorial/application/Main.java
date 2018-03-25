package main.java.com.goxr3plus.javafxchromiumbrowsertutorial.application;

import java.io.File;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserCore;
import com.teamdev.jxbrowser.chromium.internal.Environment;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.java.com.goxr3plus.javafxchromiumbrowsertutorial.tools.Util;
import main.java.com.goxr3plus.javafxchromiumbrowsertutorial.tools.Util.FileType;

public class Main extends Application {
	
	//================Variables================
	
	Browser browser = new Browser();
	
	static {
		
		//Extract Location
		System.setProperty("jxbrowser.chromium.dir", Util.getBasePathForClass(Main.class) + File.separator + "Chromium");
	}
	
	//---------------------------------------------------------------------
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Create Chromium Folder
		if (!Util.createFileOrFolder(Util.getBasePathForClass(Main.class) + File.separator + "Chromium", FileType.DIRECTORY)) {
			System.out.println("Failed to create chromium folder");
			System.exit(-1);
		}
		
		Browser browser = new Browser();
		BrowserView browserView = new BrowserView(browser);
		browser.loadURL("https://www.youtube.com/");
		
		StackPane pane = new StackPane();
		pane.getChildren().add(browserView);
		Scene scene = new Scene(pane, 1300, 900);
		primaryStage.setTitle("JxBrowser: JavaFX - Hello World");
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(exit -> Util.terminateApplication(0, browser));
		primaryStage.show();
		
	}
	
	//-------------------------------------------------------------------------------------------------------------------------------
	
	public static void main(String[] args) {
		
		launch(args);
	}
	
	@Override
	public void init() throws Exception {
		// On Mac OS X Chromium engine must be initialized in non-UI thread.
		if (Environment.isMac()) {
			BrowserCore.initialize();
		}
	}
	
}
