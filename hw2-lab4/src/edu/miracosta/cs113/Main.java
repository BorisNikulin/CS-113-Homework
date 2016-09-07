
package edu.miracosta.cs113;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application
{

	public LineGraphController lineGraphController;
	
	@Override
	public void start (Stage stage) throws Exception
	{
		FXMLLoader loader = new FXMLLoader (); // making an instance is needed to get a controller reference (static method wont do)
		Pane root = loader.<Pane>load(this.getClass ().getResourceAsStream ("/layouts/LineGraph.fxml"));
		lineGraphController = loader.<LineGraphController>getController ();
		Scene scene = new Scene (root);
		stage.setScene (scene);
		stage.setTitle ("Comparing Two Functions");
		
		lineGraphController.populateLineGraph();
		
		stage.show ();
	}
	


	public static void main (String[] args)
	{
		launch (args);
	}

}
