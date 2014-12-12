package metro.util;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import metro.data.*;

public class DataReader {
	
	ArrayList<Station> stations;
	ArrayList<Line_seg> lines;
	
	public DataReader(ArrayList<Station> s, ArrayList<Line_seg> l){
		this.stations = s;
		this.lines = l;
	}
	
	public void readData(String filename){
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			//String line = "NODE:label=Hamilton E. Holmes:x=-84.4698:y=33.755";
			//String line = "EDGE:label=: color=255 ,171 ,25: adjlist=Peachtree Center , Civic Center";
			String line = null;
			while((line = br.readLine())!= null){
				if(line.startsWith("NODE")){
					String label = line.substring(line.indexOf("label=", 0)+6, line.indexOf(":", line.indexOf("label=", 0)));
					double x = Double.parseDouble(line.substring(line.indexOf("x=", 0)+2, line.indexOf(":", line.indexOf("x=", 0))));
					double y = Double.parseDouble(line.substring(line.indexOf("y=", 0)+2));
					//System.out.println(label+" "+x+" "+y);
					Station s = new Station(label, x, y);
					stations.add(s);
				}
				else if(line.startsWith("EDGE")){
					String label = line.substring(line.indexOf("label=", 0)+6, line.indexOf(":", line.indexOf("label=", 0)));
					String colorstring = line.substring(line.indexOf("color=")+6, line.indexOf(":", line.indexOf("color=")+6));
					String adjstring = line.substring(line.indexOf("adjlist=")+8);
					StringTokenizer st = new StringTokenizer(colorstring, " ,\t");
					int r = Integer.parseInt(st.nextToken());
					int g = Integer.parseInt(st.nextToken());
					int b = Integer.parseInt(st.nextToken());
					String s1 = adjstring.substring(0, adjstring.indexOf(",")-1);
					String s2 = adjstring.substring(adjstring.indexOf(",")+2);
					//System.out.println(r+" "+g+" "+b+" "+s1+"-"+s2);
					Station st1 = null, st2 = null;
					for(int i = 0; i < stations.size(); i++){
						if(s1.equals(stations.get(i).getLabel())){
							st1 = stations.get(i);
						}
						if(s2.equals(stations.get(i).getLabel())){
							st2 = stations.get(i);
						}
					}
					
					if(st1 != null && st2 != null && st1 != st2){
						Line_seg line_seg = new Line_seg(r, g, b, st1, st2, label);
						lines.add(line_seg);
						st1.addEdge(line_seg);
						st2.addEdge(line_seg);
					}
					else{
						System.out.println("Error, can't find edge's adjlist.");
					}
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String [] args){
		//DataReader dr = new DataReader(null, null);
		//dr.readData("");
		/*ArrayList<Station> stations = new ArrayList<Station>();
		ArrayList<Line_seg> lines = new ArrayList<Line_seg>();
		stations.add(new Station("S1", 0, 0));
		stations.add(new Station("S2", 2, 0));
		stations.add(new Station("S3", 2, 2));
		stations.add(new Station("S4", 0, 2));
		stations.add(new Station("S5", -2, 2));
		stations.add(new Station("S6", -2, 0));
		stations.add(new Station("S7", -2, -2));
		stations.add(new Station("S8", 0, -2));
		stations.add(new Station("S9", 2, -2));
		lines.add(new Line_seg(0, 0, 0, stations.get(3), stations.get(0)));
		stations.get(0).addEdge(lines.get(lines.size()-1));
		lines.add(new Line_seg(0, 0, 0, stations.get(0), stations.get(4)));
		stations.get(0).addEdge(lines.get(lines.size()-1));
		lines.add(new Line_seg(0, 0, 0, stations.get(5), stations.get(0)));
		stations.get(0).addEdge(lines.get(lines.size()-1));
		lines.add(new Line_seg(0, 0, 0, stations.get(6), stations.get(0)));
		stations.get(0).addEdge(lines.get(lines.size()-1));
		lines.add(new Line_seg(0, 0, 0, stations.get(0), stations.get(7)));
		stations.get(0).addEdge(lines.get(lines.size()-1));
		lines.add(new Line_seg(0, 0, 0, stations.get(0), stations.get(1)));
		stations.get(0).addEdge(lines.get(lines.size()-1));
		lines.add(new Line_seg(0, 0, 0, stations.get(2), stations.get(0)));
		stations.get(0).addEdge(lines.get(lines.size()-1));
		lines.add(new Line_seg(0, 0, 0, stations.get(0), stations.get(8)));
		stations.get(0).addEdge(lines.get(lines.size()-1));
		System.out.println();*/
	}
	
}
