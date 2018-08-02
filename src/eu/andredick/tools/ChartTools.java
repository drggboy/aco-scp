package eu.andredick.tools;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.VectorGraphicsEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.MatlabTheme;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ChartTools {

    private XYChart chart;

    public ChartTools(String title, String xAxisTitle, String yAxisTitle) {
        int width = 600;
        int height = 350;
        this.chart = new XYChart(width, height);
        if (title != null) this.chart.setTitle(title);
        this.chart.setXAxisTitle(xAxisTitle);
        this.chart.setYAxisTitle(yAxisTitle);

        chart.getStyler().setTheme(new MatlabTheme());
        chart.getStyler().setAxisTitlesVisible(true);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);

        //chart.getStyler().setYAxisMin(150.);
        //chart.getStyler().setYAxisMax(550.);

        new SwingWrapper(chart).displayChart();
    }


    public void addDataSeries(String name, float[] yData) {

        double[] doubleArray = new double[yData.length];
        for (int i = 0; i < doubleArray.length; i++) {
            doubleArray[i] = yData[i];
        }

        XYSeries series1 = chart.addSeries(name, null, doubleArray);
        series1.setMarker(SeriesMarkers.NONE);
        series1.setLineWidth(1.2f);
    }


    public void updateDataSeries(String name, List<Float> yData) {
        XYSeries series1 = chart.updateXYSeries(name, null, yData, null);
    }

    public void saveChartAsSVG(File file) {
        try {
            VectorGraphicsEncoder.saveVectorGraphic(chart, file.getCanonicalPath(), VectorGraphicsEncoder.VectorGraphicsFormat.SVG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveChartAsPDF(File file) {
        try {
            VectorGraphicsEncoder.saveVectorGraphic(chart, file.getCanonicalPath(), VectorGraphicsEncoder.VectorGraphicsFormat.PDF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
