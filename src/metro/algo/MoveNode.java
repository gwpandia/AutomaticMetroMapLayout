package metro.algo;

import java.util.ArrayList;
import metro.data.Line_seg;
import metro.data.Station;

public class MoveNode {
	private ArrayList<Station> stations;
	private ArrayList<Line_seg> lines;
	private int r;
	private Station [][] map;
	private ScoreMap sm;
	
	public MoveNode(ArrayList<Station> s, ArrayList<Line_seg> l, int r, Station [][] m, int ll, int gg){
		this.stations = s;
		this.lines = l;
		this.r = r;
		this.map = m;
		sm = new ScoreMap(stations, lines, ll, gg);
	}
	
	private void MoveNodeToBestPlace(){
		
		for(int i = 0; i < stations.size(); i++){
			int [][] grid = checkStationMoveRange(stations.get(i));
			double score = sm.getTotalScore();
			double X = stations.get(i).getX();
			double Y = stations.get(i).getY();
			for(int j = 0; j < grid.length; j++){
				for(int k = 0; k < grid[j].length; k++){
					if(grid[j][k] == 0){
						stations.get(i).setX(X - r + k);
						stations.get(i).setY(Y - r + j);
						double curscore = sm.getTotalScore();
						if(curscore < score){
							score = curscore;
						}
						else{
							stations.get(i).setX(X);
							stations.get(i).setY(Y);
						}
					}
				}
			}
		}
		
	}
	
	private int [][] checkStationMoveRange(Station station){
		int [][] grid = new int[2*r+1][2*r+1];
		
		// Filter out already occupied and line intersect by others and boundary.
		for(int i = (int)(station.getY()-r); i <= station.getY()+r; i++){
			for(int j = (int)(station.getX()-r); j <= station.getX()+r; j++){
				if(i < 0 || j < 0 || i >= map.length || j >= map[0].length){
					grid[i-((int)(station.getY()-r))][j-((int)(station.getX()-r))] = 1;
				}
				else{
					if(map[i][j] != null){
						grid[i-((int)(station.getY()-r))][j-((int)(station.getX()-r))] = 1;
					}
					for(int k = 0; k < lines.size(); k++){
						if(lines.get(k).getLine().contains(j, i)){
							grid[i-((int)(station.getY()-r))][j-((int)(station.getX()-r))] = 1;
						}
					}
				}
			}
		}
		
		
		// maintain coordinate position
		double minX = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		double minY = Double.MAX_VALUE;
		double maxY = Double.MIN_VALUE;
		
		for(int i = 0; i < station.getEdges().size(); i++){
			Station s = null;
			if(station.getEdges().get(i).getStation1() == station){
				s = station.getEdges().get(i).getStation2();
			}
			else if(station.getEdges().get(i).getStation2() == station){
				s = station.getEdges().get(i).getStation1();
			}
			if(s.getX() > maxX){
				maxX = s.getX();
			}
			if(s.getX() < minX){
				minX = s.getX();
			}
			if(s.getY() > maxY){
				maxY = s.getY();
			}
			if(s.getY() < minY){
				minY = s.getY();
			}
		}
		maxX -= station.getX();
		minX -= station.getX();
		maxY -= station.getY();
		minY -= station.getY();
		double bMaxX = 0;
		double bMaxY = 0;
		double bMinX = 0;
		double bMinY = 0;
		if(minY >= 0 && maxY >= 0){
			bMinY = 0;
			bMaxY = minY + r;
			if(maxX >=0 && minX >= 0){
				bMaxX = minX + r;
				bMinX = 0;
			}
			else{
				bMaxX = maxX + r;
				bMinX = minX + r;
			}
		}
		else if(minY < 0 && maxY < 0){
			bMinY = maxY + r;
			bMaxY = 2*r+1;
			if(maxX >= 0 && minX >= 0){
				bMaxX = minX + r;
				bMinX = 0;
			}
			else{
				bMaxX = maxX + r;
				bMinX = minX + r;
			}
		}
		else{
			bMaxX = maxX + r;
			bMinX = minX + r;
			bMaxY = maxY + r;
			bMinY = minY + r;
		}
		if(bMaxX > 2 * r + 1){
			bMaxX = 2 * r + 1;
		}
		if(bMaxY > 2 * r + 1){
			bMaxY = 2 * r + 1;
		}
		if(bMinX < 0){
			bMinX = 0;
		}
		if(bMinY < 0){
			bMinY = 0;
		}
		
		for(int i = 0; i < bMinY; i++){
			for(int j = 0; j < grid[i].length; j++){
				grid[i][j] = 1;
			}
		}
		for(int i = (int)bMaxY+1; i < grid.length; i++){
			for(int j = 0; j < grid[i].length; j++){
				grid[i][j] = 1;
			}
		}
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < bMinX; j++){
				grid[i][j] = 1;
			}
		}
		for(int i = 0; i < grid.length; i++){
			for(int j = (int)bMaxX+1; j < grid[i].length; j++){
				grid[i][j] = 1;
			}
		}
		
		return grid;
	}
}
