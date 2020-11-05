package ShortRoute.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ShortRoute.model.BeanLine;
import ShortRoute.model.BeanStation;


public class GetData {
	
	public static int linenum=0;
	
	public static ArrayList<BeanLine> lineSet = new ArrayList<BeanLine>(); 
	
	public GetData(String path) throws IOException{
		
		File file = new File(path);
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		//读取信息
		try {
			String in = "";
			while((in=br.readLine())!=null){
				linenum++;
				
				String[] stationPart = in.split(" ");
				BeanLine line = new BeanLine();
				List<BeanStation> stations =new ArrayList<BeanStation>();
				line.setLineName(stationPart[0]);
				//提取线路名、站点
				for(int i = 1;i<stationPart.length;i++)
				{
					BeanStation station = new BeanStation();
					List<String> lines = new ArrayList<String>();
					List<BeanStation> neighborStation = new ArrayList<BeanStation>();
					
					station.setStationName(stationPart[i]);
					if(i==stationPart.length-1 && stationPart[i].equals(stationPart[1]))
					{
						continue;//判断是否是否是环路
				}
					int ischange=0;
					int linenum;
					int stationnum;
					//是否是换乘站
					int posline=0,posstation=0;
					int flag=0;//确定站点是否存在
					for(int j=0;j<lineSet.size();j++)
					{
						for(int k=0;k<lineSet.get(j).getStations().size();k++)
						{
							if(stationPart[i].equals(lineSet.get(j).getStations().get(k).getStationName()))
							{
								posline=j;
								posstation=k;
								station=lineSet.get(j).getStations().get(k);
								lines=station.getLineName();
								lines.add(line.getLineName());//加入新的换乘线
								neighborStation=station.getNeighborStation();
								flag=1;
							}
						}
					}
					if(flag==0)//不存在
					{
						lines.add(line.getLineName());
					}
	
					//加入点信息
					station.setLineName(lines);
					stations.add(station);
					if(flag==1)
					{
						List<BeanStation> updatestations = new ArrayList<BeanStation>();
						updatestations = lineSet.get(posline).getStations();
						updatestations.set(posstation, station);
						lineSet.get(posline).setStations(updatestations);
					}
					
					line.setStations(stations);
					}
	
				lineSet.add(line);
				}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void getstationinfo(List<BeanLine> lines) {
		
		for(int i = 0 ;i<lines.size();i++)
		{
			for(int j=0;j<lines.get(i).getStations().size();j++) {
				List<BeanStation> neighborStation = new ArrayList<BeanStation>();
				if(j>0)
					neighborStation.add(lines.get(i).getStations().get(j-1));
				if(j<lines.get(i).getStations().size()-1)
					neighborStation.add(lines.get(i).getStations().get(j+1));
				lines.get(i).getStations().get(j).setNeighborStation(neighborStation);
			}
		}
	}
		
}
