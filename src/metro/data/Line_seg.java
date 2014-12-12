package metro.data;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import metro.interfaces.Drawable;

public class Line_seg implements Drawable {

	public Line_seg(int r, int g, int b, Station s1, Station s2, String ln){
		lineColor = new Color(r, g, b);
		station1 = s1;
		station2 = s2;
		lineName = ln;
	}
	
	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(lineColor);
		int strokewidth = 3;
		g.setStroke(new BasicStroke(strokewidth));
		g.drawLine((int)station1.getX()*gridwidth*scale, (int)station1.getY()*gridwidth*scale, 
				(int)station2.getX()*gridwidth*scale, (int)station2.getY()*gridwidth*scale);
	}
	
	public static void setGridWidth(int g){
		gridwidth = g;
	}
	
	public Station getStation1() {
		return station1;
	}

	public Station getStation2() {
		return station2;
	}
	
	public Line2D.Double getLine(){
		return new Line2D.Double(station1.getX(), station1.getY(), station2.getX(), station2.getY());
	}

	public double getLength(){
		double x1 = getLine().x1;
		double x2 = getLine().x2;
		double y1 = getLine().y1;
		double y2 = getLine().y2;
		double length = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
		return length;
	}
	
	public String getLineName(){
		return lineName;
	}
	
	public double getAngle(){

		double dx = Math.abs(station1.getX() - station2.getX());
		double dy = Math.abs(station1.getY() - station2.getY());
		double angle = Math.atan2(dy, dx);
		while(angle < 0.0){
			angle += 360.0;
		}
		angle %= 360.0;
		return angle;
	}
	
	public Color getLineColor(){
		return lineColor;
	}
	
	public static void setScale(int s){
		scale = s;
	}
	
	private Color lineColor;
	private Station station1;
	private Station station2;
	private String lineName;
	private static int gridwidth;
	private static int scale;
	
}
