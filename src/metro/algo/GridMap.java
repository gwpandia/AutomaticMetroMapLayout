package metro.algo;

import java.awt.Point;
import java.util.ArrayList;

import metro.data.Station;

public class GridMap {
	private ArrayList<Station> stations;
	private int g;
	private Station [][] map;
	private double minY;
	private double maxY;
	private double minX;
	private double maxX;
	
	public GridMap(ArrayList<Station> s, int g){
		this.g = g;
		this.stations = s;
		findBoundary();
		snapToGrid();
	}
	
	private void findBoundary(){
		minY = Double.MAX_VALUE;
		maxY = Double.MIN_VALUE;
		minX = Double.MAX_VALUE;
		maxX = Double.MIN_VALUE;
		
		for(int i = 0; i < stations.size(); i++){
			if(stations.get(i).getX() > maxX){
				maxX = stations.get(i).getX();
			}
			if(stations.get(i).getX() < minX){
				minX = stations.get(i).getX();
			}
			if(stations.get(i).getY() > maxY){
				maxY = stations.get(i).getY();
			}
			if(stations.get(i).getY() < minY){
				minY = stations.get(i).getY();
			}
		}
		
		//int width = (int) Math.round((maxX-minX)/(double)g) + 10;
		//int height = (int) Math.round((maxY-minY)/(double)g) + 10;
		
		int width = stations.size();
		int height = stations.size();
		
		if(minX <= 0){
			for(int i = 0; i < stations.size(); i++){
				stations.get(i).setX(stations.get(i).getX() + minX);
			}
		}
		
		if(minY <= 0){
			for(int i = 0; i < stations.size(); i++){
				stations.get(i).setY(stations.get(i).getY() + minY);
			}
		}
		
		map = new Station[height][width];
		System.out.println(width + " " + height);
	}
	
	private void snapToGrid(){
		for(int i = 0; i < stations.size(); i++){
			double x = stations.get(i).getX() / (double)g;
			double y = stations.get(i).getY() / (double)g;
			int [] position_x = new int [16];//{(int)x, (int)x, ((int)x)+1, ((int)x)+1, ((int)x)-1, ((int)x), ((int)x)+1, ((int)x)+2};
			int [] position_y = new int [16];//{(int)y, ((int)y)+1, (int)y, ((int)y)+1};
			
			for(int j = 0; j < 4; j++){
				position_x[j*4] = ((int)x) - 1; 
				position_x[j*4+1] = ((int)x);
				position_x[j*4+2] = ((int)x) + 1;
				position_x[j*4+3] = ((int)x) + 2;
				position_y[j] = ((int)y) + 2;
				position_y[j+4] = ((int)y) + 1;
				position_y[j+8] = ((int)y);
				position_y[j+12] = ((int)y) - 1;
			}
			
			double min_dist = Double.MAX_VALUE;
			int item = -1;
			for(int j = 0; j < 16; j++){
				double dist = Point.distance(x, y, position_x[j], position_y[j]);
				if(testBoundary(position_y[j], 0, map.length) && testBoundary(position_x[j], 0, map[0].length)){
					if(dist < min_dist && map[position_y[j]][position_x[j]] == null){
						min_dist = dist;
						item = j;
					}
				}
			}
			if(item >= 0){
				stations.get(i).setX(position_x[item]);
				stations.get(i).setY(position_y[item]);
				map[position_y[item]][position_x[item]] = stations.get(i);
			}
			else{
				System.out.println("Error, change g");
				return;
			}
		}
	}
	
	private boolean testBoundary(int test, int lower, int upper){
		return (test >= lower) && (test < upper);
	} 
	
}
