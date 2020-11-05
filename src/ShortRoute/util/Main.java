package ShortRoute.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import ShortRoute.model.BeanLine;
import ShortRoute.model.BeanStation;

public class Main {
	
    
    public static GetData GetData;
    
	private static Map<String,BeanStation>  StationSet=new HashMap<String, BeanStation>();//存储站名与站点的map
    private static ArrayList<BeanLine> LineSet=GetData.lineSet;

	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);
		GetData = new GetData("C:\\Users\\xch\\Desktop\\data1.txt");
		GetData.getstationinfo(GetData.lineSet);
		System.out.println("**********************************");
		System.out.println("**********************************");
		System.out.println("**********************************");
		System.out.println("**                              **");
		System.out.println("**       地铁路线查询系统              **");
		System.out.println("**                              **");
		System.out.println("**********************************");
		System.out.println("**********************************");
		System.out.println("**********************************");
		
		System.out.println("功能选项：1 查看地铁线路     2 查询地铁最短路径");
	
		int op = input.nextInt();
		while(op!=1&&op!=2) {
			System.out.println("没有这个选项，请重新输入！！");
			op = input.nextInt();
		}
		if(op==1) {
			System.out.println("请输入地铁路线名：");
			String roadname = input.next();
			searchRoad(roadname);
		}
		else if(op==2) {
			System.out.print("请输入起点站名：");
			String start = input.next();
			System.out.print("请输入终点站名：");
			String end = input.next();
			getShortRoute(start, end);
		}
		input.close();
	}
	
	private static void searchRoad(String roadname) {
		// TODO Auto-generated method stub
		int flag=0;
        for(BeanLine line: LineSet){
            if(line.getLineName().equals(roadname)){
                System.out.print(roadname+  "：");
                for(int j=0;j<line.getStations().size();j++)
                {
                	System.out.print(line.getStations().get(j).getStationName()+" ");
                }
                flag=1;
                break;
            }
        }
        if(flag==0){
            System.out.println("地铁路线不存在");
        }
	}
	
	
	private static void getShortRoute(String startStation, String endStation) {
 
		BeanStation startStation1 = new BeanStation();
		startStation1 = getStart(startStation);
		if(startStation==null) {
			System.out.println("起点不存在");
			System.exit(1);
		}
		//终点不存在情况
		BeanStation endStation1 = new BeanStation();
		endStation1 = getStart(endStation);
		if(endStation==null) {
			System.out.println("终点不存在");
			System.exit(1);
		}
		
		if(startStation.equals(endStation)) {
			System.out.print("已在该站点，不需要乘坐");
			System.exit(1);
		}
        Map<String,Integer> map=new HashMap<String, Integer>();//站点距离信息
        Map<String,ArrayList<String>> path=new HashMap<String,ArrayList<String>>();//路径信息
        //存储起点距离信息
        map.put(startStation,0);
        ArrayList<String> a=new ArrayList<String>();
        a.add(startStation);
        path.put(startStation,a);
        //创建队列，把起点加入到队列中
        Queue<String> queue=new LinkedList<String>();
        queue.offer(startStation);
        //当队列不为空
        while(!queue.isEmpty()){
            String top =queue.poll();
            
            //当队列不为空时，在队尾加入队列头的邻居站点
            int dis = map.get(top)+1;
            ArrayList<String> topPath = path.get(top);
            
            for (BeanStation c : StationSet.get(top).getNeighborStation()) {

                //判断是否已经遍历该点
                if (!map.containsKey(c)){//没有遍历 加入队列
                    map.put(c.getStationName(), dis);
                    ArrayList<String> Path = new ArrayList<String>(topPath);
                    Path.add(c.getStationName());
                    path.put(c.getStationName(),Path);
                    queue.add(c.getStationName());
                    //当遍历到达终点站时，输出
                    if(c.equals(endStation)){
                        System.out.println("总共"+dis+"站");
                        getPath(path.get(c));
                        return;
                    }
                }
            } 
        }
    }

    private static BeanStation getStart(String startStation) {
		// TODO Auto-generated method stub
    	BeanStation station = new BeanStation();
		for(int i=0;i<GetData.lineSet.size();i++) {
			for(int j=0;j<GetData.lineSet.get(i).getStations().size();j++) {
				if(startStation.equals(GetData.lineSet.get(i).getStations().get(j).getStationName())) {
					station = GetData.lineSet.get(i).getStations().get(j);
					return station;
				}
			}
		}
		return null;
	}

	public static void getPath(ArrayList<String> path){//路径输出
        System.out.println(getexchangeline(path.get(0),path.get(1)));
        System.out.print(" "+path.get(0)+" ");
        int i;
        //判断换乘
        for(i=0;i<path.size()-2;i++){
            System.out.print(path.get(i+1)+" ");
            if(isExchange(path.get(i),path.get(i+2))){
                System.out.println();
                System.out.println(getexchangeline(path.get(i),path.get(i+1))+" ");
                System.out.println(getexchangeline(path.get(i+1),path.get(i+2)));
                System.out.println(" "+path.get(i+1)+" ");
            }
        }
        System.out.println("->->换乘  "+path.get(i+1)+" ");
        System.out.println(getexchangeline(path.get(i),path.get(i+1)));
    }

    private static boolean isExchange(String string, String string2) {
		// TODO Auto-generated method stub
		return false;
	}

	//判断两站是否换乘 是的话返回ture 不是false
    public static boolean isExchange(BeanStation line1,BeanStation line2) {
		int  isExchange = 1;
		for(String ln1 :line1.getLineName()) {
			for(String ln2 :line2.getLineName()) {
				if(ln1.equals(line2)) {
					isExchange = 0;
					return false;
				}
			}
		}
		return true;
    }

    //得到换乘的路线
    public static String getexchangeline(String station1,String station2){
        for(String name:StationSet.get(station1).getLineName()){
            if(StationSet.get(station2).getLineName().contains(name))
            return name;
        }
		return null;
    }

	 }
