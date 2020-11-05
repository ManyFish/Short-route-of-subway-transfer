package ShortRoute.model;

import java.util.ArrayList;
import java.util.List;

public class BeanStation {
	private String stationName;
	private List<String> lineName;
	private List<BeanStation> neighborStation;
	
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public List<String> getLineName() {
		return lineName;
	}
	public void setLineName(List<String> lineName) {
		this.lineName = lineName;
	}
	public List<BeanStation> getNeighborStation() {
		return neighborStation;
	}
	public void setNeighborStation(List<BeanStation> neighborStation) {
		this.neighborStation = neighborStation;
	}
	
	
}
