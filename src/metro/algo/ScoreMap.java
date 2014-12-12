package metro.algo;

import java.util.ArrayList;

import metro.data.Line_seg;
import metro.data.Station;

public class ScoreMap {
	private ArrayList<Station> stations;
	private ArrayList<Line_seg> lines;
	private double w1;
	private double w2;
	private double w3;
	private double w4;
	private double w5;
	private double w6;
	private int l;
	private int g;
	
	public ScoreMap(ArrayList<Station> s, ArrayList<Line_seg> l, int ll, int gg){
		this.stations = s;
		this.lines = l;
		this.l = ll;
		this.g = gg;
		w1 = 1;
		w2 = 1;
		w3 = 1;
		w4 = 1;
		w5 = 1;
		w6 = 1;
	}
	
	public double getTotalScore(){
		double score = 0;
		score += w1 * this.getAngularResolution();
		score += w2 * this.getEdgeLength(l, g);
		score += w3 * this.getBalancedLength();
		score += w4 * this.getEdgeCrossing();
		score += w5 * this.getLineStaightness();
		score += w6 * this.getOctilinearity();
		return score;
	}
	
	private double getAngularResolution(){
		double ret = 0;
		
		for(int i = 0; i < stations.size(); i++){
			double resolve = 360.0 / stations.get(i).getEdges().size();
			for(int j = 0; j < stations.get(i).getEdges().size(); j++){
				double angle1 = stations.get(i).getAngle(stations.get(i).getEdges().get(j));
				double angle2 = stations.get(i).getAngle(stations.get(i).getEdges().get((j+1)%stations.get(i).getEdges().size()));
				ret += Math.toRadians(Math.abs(resolve - (angle2-angle1)));
			}
		}
		
		return ret;
	} 
	
	private double getEdgeLength(int l, int g){
		double ret = 0;
		
		for(int i = 0; i < lines.size(); i++){
			double length = lines.get(i).getLength();
			length /= (l*g);
			length -= 1;
			ret += Math.abs(length);
		}
		
		return ret;
	}
	
	private double getBalancedLength(){
		double ret = 0;
		
		for(int i = 0; i < stations.size(); i++){
			if(stations.get(i).getEdges().size() == 2){
				ret += Math.abs(stations.get(i).getEdges().get(0).getLength()-
						stations.get(i).getEdges().get(1).getLength());
			}
		}
		
		return ret;
	}
	
	private double getEdgeCrossing(){
		double ret = 0;
		
		for(int i = 0; i < lines.size(); i++){
			for(int j = i+1; j < lines.size(); j++){
				if(lines.get(i).getLine().intersectsLine(lines.get(j).getLine())){
					ret += 1;
				}
			}
		}
		
		return ret;
	}
	
	private double getLineStaightness(){
		double ret = 0;
		
		for(int i = 0; i < stations.size(); i++){
			ArrayList<Line_seg> edges = stations.get(i).getEdges();
			for(int j = 0; j < edges.size(); j++){
				for(int k = j+1; k < edges.size(); k++){
					if(edges.get(j).getLineName().equals(edges.get(k).getLineName())){
						ret += Math.abs(180.0 - Math.abs((stations.get(i).getAngle(edges.get(k)) - stations.get(i).getAngle(edges.get(k)))));
					}
				}
			}
		}
		
		return ret;
	}
	
	private double getOctilinearity(){
		double ret = 0;
		
		for(int i = 0; i < lines.size(); i++){
			ret += Math.sin(Math.toRadians(4*lines.get(i).getAngle()));
		}
		
		return ret;
	}

	public double getW1() {
		return w1;
	}

	public void setW1(double w1) {
		this.w1 = w1;
	}

	public double getW2() {
		return w2;
	}

	public void setW2(double w2) {
		this.w2 = w2;
	}

	public double getW3() {
		return w3;
	}

	public void setW3(double w3) {
		this.w3 = w3;
	}

	public double getW4() {
		return w4;
	}

	public void setW4(double w4) {
		this.w4 = w4;
	}

	public double getW5() {
		return w5;
	}

	public void setW5(double w5) {
		this.w5 = w5;
	}

	public double getW6() {
		return w6;
	}

	public void setW6(double w6) {
		this.w6 = w6;
	}
	
}
