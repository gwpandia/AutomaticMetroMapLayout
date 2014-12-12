package metro.data;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import metro.interfaces.Drawable;

public class Station implements Drawable{

	public Station(String s, double x, double y){
		this.label = s;
		this.x = x;
		this.y = y;
		edges = new ArrayList<Line_seg>();
	}
	
	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		HashMap<String, Color> sets = new HashMap<String, Color>();
		for(int i = 0; i < edges.size(); i++){
			sets.put(edges.get(i).getLineName(), edges.get(i).getLineColor());
		}
		
		int test = 0;
		int strokewidth = 5;
		int radius = 2;
		g.setStroke(new BasicStroke(strokewidth));
		Set<String> s = sets.keySet();
		Iterator<String> it = s.iterator();
		g.setColor(Color.WHITE);
		g.drawOval((int)this.x*gridwidth*scale-radius-(test*strokewidth) , (int)this.y*gridwidth*scale-radius-(test*strokewidth), 
				2*(radius+strokewidth*test), 2*(radius+strokewidth*test));
		++test;
		while(it.hasNext()){
			g.setColor(sets.get(it.next()));
			g.drawOval((int)this.x*gridwidth*scale-radius-(test*strokewidth) , (int)this.y*gridwidth*scale-radius-(test*strokewidth), 
					2*(radius+strokewidth*test), 2*(radius+strokewidth*test));
			++test;
		}

		
	}
	
	public void addEdge(Line_seg edge){
		if(this != edge.getStation1() && this != edge.getStation2()){
			System.err.println("Can't add! Error Edge!");
			return;
		}
		
		double angle = getAngle(edge);
		
		for(int i = 0; i < edges.size(); i++){
			double curAngle = getAngle(edges.get(i));
			if(angle < curAngle){
				edges.add(i, edge);
				return;
			}
		}
		edges.add(edge);
	}
	
	public void recheckEdgeOrder(){
		ArrayList<Line_seg> temp = (ArrayList<Line_seg>)edges.clone();
		edges.clear();
		for(int i = 0; i < temp.size(); i++){
			addEdge(temp.get(i));
		}
	}

	public double getAngle(Line_seg edge){
		Station s = null;
		if(edge.getStation1() == this){
			s = edge.getStation2();
		}
		if(edge.getStation2() == this){
			s = edge.getStation1();
		}
		double dx = s.x - this.x;
		double dy = s.y - this.y;
		double angle = Math.atan2(dy, dx);
		while(angle < 0.0){
			angle += 360.0;
		}
		angle %= 360.0;
		return angle;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
		//recheckEdgeOrder();
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		//recheckEdgeOrder();
	}

	public String getLabel() {
		return label;
	}

	public ArrayList<Line_seg> getEdges() {
		return edges;
	}
	
	public static void setG(int g){
		gridwidth = g;
	}
	
	public static void setScale(int s){
		scale = s;
	}

	private String label;
	private double x;
	private double y;
	private ArrayList<Line_seg> edges;
	private static int gridwidth;
	private static int scale;
}
