package metro.data;

import java.util.ArrayList;
import java.util.Iterator;

import metro.algo.GridMap;
import metro.interfaces.MapStateListener;
import metro.util.DataReader;

public class SimpleModel {
	private ArrayList<MapStateListener> msl;
	private ArrayList<Station> stations;
	private ArrayList<Line_seg> lines;
	
	public SimpleModel(){
		int GRIDWIDTH = 15;
		int SCALE = 10;
		stations = new ArrayList<Station>();
		lines = new ArrayList<Line_seg>();
		msl = new ArrayList<MapStateListener>();
		DataReader dr = new DataReader(stations, lines);
		dr.readData("data.txt");
		Station.setG(GRIDWIDTH);
		Station.setScale(SCALE);
		Line_seg.setGridWidth(GRIDWIDTH);
		Line_seg.setScale(SCALE);
		GridMap gm = new GridMap(stations, GRIDWIDTH);
		this.notifyMapStateListener();
	}
	
	public void addMapStateListener(MapStateListener m){
		this.msl.add(m);
	}
	
	public void removeMapStateListener(MapStateListener m){
		int a = msl.indexOf(m);
		if(a >= 0){
			msl.remove(a);
		}
	}
	
	public void notifyMapStateListener(){
		Iterator<MapStateListener> it = msl.iterator();
		while(it.hasNext()){
			MapStateListener t = it.next();
			t.updateImage(stations, lines);
		}
	}

	public ArrayList<Station> getStations() {
		return stations;
	}

	public ArrayList<Line_seg> getLines() {
		return lines;
	}
	
}
