package io.dimitris.duix;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class PollSlide extends Slide {
	
	protected DefaultCategoryDataset dataset;
	protected HashMap<String, Integer> options;
	protected String title;
	protected String xAxis;
	protected String yAxis;
	
	public PollSlide(String title, String xAxis, String yAxis, HashMap<String, Integer> options) {
		this.title = title;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.options = options;
		dataset = createDataset();
	}
	
	@Override
	public void attach(SlidePanel slidePanel) {
        JFreeChart chart = createChart(dataset, title, xAxis, yAxis);
        final ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setFont(new Font("Monaco", Font.PLAIN, 14));
        for (int i=0;i<9;i++) {
        	final int index = i;
        	final int keyEvent = KeyEvent.VK_1 + i;
	        addKeyAction(chartPanel, new AbstractAction() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ArrayList<String> optionNames = new ArrayList<String>(options.keySet());
					options.put(optionNames.get(index), options.get(optionNames.get(index)) + 1);
					dataset.addValue((double)options.get(optionNames.get(index)), optionNames.get(index), "");
					chartPanel.updateUI();
				}
			}, keyEvent);
        }
        slidePanel.add(chartPanel, BorderLayout.CENTER);
        
	}
	
	@Override
	public void detach(SlidePanel slidePanel) {
		slidePanel.removeAll();
	}
	
	public void addKeyAction(ChartPanel chartPanel, Action action, int... keyEvents) {
		for (int keyEvent : keyEvents) {
			KeyStroke keyStroke = KeyStroke.getKeyStroke(keyEvent, 0);
			chartPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, keyEvent + "");
			chartPanel.getActionMap().put(keyEvent + "", action);			
		}
	}
	
	private DefaultCategoryDataset createDataset() {
        dataset = new DefaultCategoryDataset();
        for (String option : options.keySet()) {
        	dataset.addValue(options.get(option), option, "");
        }
        return dataset;
    }

    private static JFreeChart createChart(CategoryDataset dataset, String title, String xaxis, String yaxis) {
        JFreeChart chart = ChartFactory.createBarChart/*3D*/(title, null, yaxis, dataset);
        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardBarPainter());
        //renderer.setDrawBarOutline(false);
        //renderer.setSeriesPaint(1, Color.BLACK);
        chart.getLegend().setFrame(BlockBorder.NONE);
        
        return chart;
    }
    
}

