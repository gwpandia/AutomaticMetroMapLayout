package metro.app;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

import metro.data.Line_seg;
import metro.data.SimpleModel;
import metro.data.Station;
import metro.ui.MapPanel;
import metro.util.DataReader;

public class Test {
	
	public static void main(String [] args){
		SimpleModel sm = new SimpleModel();
		MapPanel mp = new MapPanel(sm);
		sm.addMapStateListener(mp);
		JFrame frame = new JFrame("Test");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(mp, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
}
