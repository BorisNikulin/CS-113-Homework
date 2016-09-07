
package edu.miracosta.cs113;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application
{

	@Override
	public void start (Stage stage) throws Exception
	{
		Pane root = FXMLLoader.<Pane>load (this.getClass().getResource ("/layouts/LineGraph.fxml"));
		Scene scene = new Scene (root);
		stage.setScene (scene);
		stage.show ();
	}

	public static void main (String[] args)
	{
		launch (args);
	}

}
