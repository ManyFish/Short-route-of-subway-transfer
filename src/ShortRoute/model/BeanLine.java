package ShortRoute.model;

import java.util.List;

public class BeanLine {

	private String lineName; //线名
	private List<BeanStation> stations; //线上的站
	
	public String getLineName() {
		return lineName;
	}
	
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	
	public List<BeanStation> getStations() {
		return stations;
	}
	
	public void setStations(List<BeanStation> stations) {
		this.stations = stations;
	}
	
	public void add(BeanStation station) {
		this.stations.add(station);
		
	}
	
	
}
