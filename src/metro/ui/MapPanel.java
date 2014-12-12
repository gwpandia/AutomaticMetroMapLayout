package metro.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JComponent;

import metro.data.Line_seg;
import metro.data.SimpleModel;
import metro.data.Station;
import metro.interfaces.MapStateListener;

public class MapPanel extends JComponent implements MapStateListener  {

	private ArrayList<Station> stations;
	private ArrayList<Line_seg> lines; 
	static protected Graphics2D g2;
	
	public MapPanel(SimpleModel sm){
		this.stations = sm.getStations();
		this.lines = sm.getLines();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		for(int i = 0; i < lines.size(); i++){
			lines.get(i).draw(g2);
		}
		
		for(int i = 0; i < stations.size(); i++){
			stations.get(i).draw(g2);
		}
		
	}
	@Override
	public void updateImage(ArrayList<Station> s, ArrayList<Line_seg> l) {
		this.stations = s;
		this.lines = l;
	}

}
