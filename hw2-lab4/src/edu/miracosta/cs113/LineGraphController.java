package edu.miracosta.cs113;

import java.util.function.IntFunction;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class LineGraphController {

	private XYChart.Series<Number, Number> series1 = new XYChart.Series<Number, Number>();
	private XYChart.Series<Number, Number> series2 = new XYChart.Series<Number, Number>();
	
    @FXML
    private LineChart<Number, Number> lineGraph;

    @FXML
    void initialize() {
        assert lineGraph != null : "fx:id=\"lineGraph\" was not injected: check your FXML file 'LineGraph.fxml'.";
        
        series1.setName ("F(n) = 100 * n + 10");
        series2.setName ("F(n) = 5 * n * n + 2 ");
        lineGraph.getData ().add (series1);
        lineGraph.getData ().add (series2);
    }
    
	public void populateLineGraph()
	{
		IntFunction<Integer> func1 = x -> 100 * x + 10;
		IntFunction<Integer> func2 = x -> 5 * x * x + 2;
		
		ObservableList<XYChart.Data<Number, Number>> dataSet1 = getSeries1 ().getData ();
		ObservableList<XYChart.Data<Number, Number>> dataSet2 = getSeries2 ().getData ();
		
		for(int i = 0; i <= 100; i += 10)
		{
			dataSet1.add (new XYChart.Data<Number, Number>(i, func1.apply (i)));
			dataSet2.add (new XYChart.Data<Number, Number>(i, func2.apply (i)));
		}
	}

	public XYChart.Series<Number, Number> getSeries1 ()
	{
		return series1;
	}

	public void setSeries1 (XYChart.Series<Number, Number> series1)
	{
		this.series1 = series1;
	}

	public XYChart.Series<Number, Number> getSeries2 ()
	{
		return series2;
	}

	public void setSeries2 (XYChart.Series<Number, Number> series2)
	{
		this.series2 = series2;
	}
}
