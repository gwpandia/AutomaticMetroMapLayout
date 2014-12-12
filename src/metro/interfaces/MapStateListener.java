package metro.interfaces;

import java.util.ArrayList;

import metro.data.*;

public interface MapStateListener {
	public void updateImage(ArrayList<Station> s, ArrayList<Line_seg> l);
}
