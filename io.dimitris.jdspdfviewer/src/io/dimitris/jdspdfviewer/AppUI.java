package io.dimitris.jdspdfviewer;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.apache.pdfbox.pdmodel.PDDocument;

public class AppUI {
	
	public static void main(String[] args) throws Exception {
		new AppUI().run();
	}

	protected void run() throws Exception {
		
		JFrame main = new JFrame();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		main.getRootPane().setLayout(new BorderLayout());
		
		final JPanel dropTarget = new JPanel();
		dropTarget.setLayout(new BorderLayout());
		Border dashedBorder = BorderFactory.createDashedBorder(Color.GRAY, 2, 3, 2, true);
		Border activeDashedBorder = BorderFactory.createDashedBorder(Color.LIGHT_GRAY, 2, 3, 2, true);
		Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		dropTarget.setBorder(BorderFactory.createCompoundBorder(emptyBorder, dashedBorder));
		final JLabel dropTargetLabel = new JLabel("Drop PDF presentation here.", SwingConstants.CENTER);
		
		dropTarget.add(dropTargetLabel, BorderLayout.CENTER);
		main.getRootPane().add(dropTarget, BorderLayout.CENTER);
		
		new  FileDrop( dropTarget, BorderFactory.createCompoundBorder(emptyBorder, activeDashedBorder), new FileDrop.Listener()
		  {   public void  filesDropped( java.io.File[] files )
		      {   
		          if (files[0].getName().endsWith("pdf")) {
		        	  try {
			        	  PDDocument document = PDDocument.load(files[0]);
			        	  SlidePanel slidePanel = new SlidePanel(document, false, true);
			        	  dropTarget.removeAll();
			        	  dropTarget.add(slidePanel, BorderLayout.CENTER);
			        	  dropTarget.updateUI();
		        	  }
		        	  catch (Exception ex) {
		        		  ex.printStackTrace();
		        	  }
		          }
		      }   // end filesDropped
		  }); // end FileDrop.Listener
		
		main.setSize(300, 300);
		main.setLocation(800, 400);
		main.setTitle("jdspdfviewer");
		main.setVisible(true);
		
	}
	
}
