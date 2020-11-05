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
    
	private static Map<String,BeanStation>  StationSet=new HashMap<String, BeanStation>();//�洢վ����վ���map
    private static ArrayList<BeanLine> LineSet=GetData.lineSet;

	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);
		GetData = new GetData("C:\\Users\\xch\\Desktop\\data1.txt");
		GetData.getstationinfo(GetData.lineSet);
		System.out.println("**********************************");
		System.out.println("**********************************");
		System.out.println("**********************************");
		System.out.println("**                              **");
		System.out.println("**       ����·�߲�ѯϵͳ              **");
		System.out.println("**                              **");
		System.out.println("**********************************");
		System.out.println("**********************************");
		System.out.println("**********************************");
		
		System.out.println("����ѡ�1 �鿴������·     2 ��ѯ�������·��");
	
		int op = input.nextInt();
		while(op!=1&&op!=2) {
			System.out.println("û�����ѡ����������룡��");
			op = input.nextInt();
		}
		if(op==1) {
			System.out.println("���������·������");
			String roadname = input.next();
			searchRoad(roadname);
		}
		else if(op==2) {
			System.out.print("���������վ����");
			String start = input.next();
			System.out.print("�������յ�վ����");
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
                System.out.print(roadname+  "��");
                for(int j=0;j<line.getStations().size();j++)
                {
                	System.out.print(line.getStations().get(j).getStationName()+" ");
                }
                flag=1;
                break;
            }
        }
        if(flag==0){
            System.out.println("����·�߲�����");
        }
	}
	
	
	private static void getShortRoute(String startStation, String endStation) {
 
		BeanStation startStation1 = new BeanStation();
		startStation1 = getStart(startStation);
		if(startStation==null) {
			System.out.println("��㲻����");
			System.exit(1);
		}
		//�յ㲻�������
		BeanStation endStation1 = new BeanStation();
		endStation1 = getStart(endStation);
		if(endStation==null) {
			System.out.println("�յ㲻����");
			System.exit(1);
		}
		
		if(startStation.equals(endStation)) {
			System.out.print("���ڸ�վ�㣬����Ҫ����");
			System.exit(1);
		}
        Map<String,Integer> map=new HashMap<String, Integer>();//վ�������Ϣ
        Map<String,ArrayList<String>> path=new HashMap<String,ArrayList<String>>();//·����Ϣ
        //�洢��������Ϣ
        map.put(startStation,0);
        ArrayList<String> a=new ArrayList<String>();
        a.add(startStation);
        path.put(startStation,a);
        //�������У��������뵽������
        Queue<String> queue=new LinkedList<String>();
        queue.offer(startStation);
        //�����в�Ϊ��
        while(!queue.isEmpty()){
            String top =queue.poll();
            
            //�����в�Ϊ��ʱ���ڶ�β�������ͷ���ھ�վ��
            int dis = map.get(top)+1;
            ArrayList<String> topPath = path.get(top);
            
            for (BeanStation c : StationSet.get(top).getNeighborStation()) {

                //�ж��Ƿ��Ѿ������õ�
                if (!map.containsKey(c)){//û�б��� �������
                    map.put(c.getStationName(), dis);
                    ArrayList<String> Path = new ArrayList<String>(topPath);
                    Path.add(c.getStationName());
                    path.put(c.getStationName(),Path);
                    queue.add(c.getStationName());
                    //�����������յ�վʱ�����
                    if(c.equals(endStation)){
                        System.out.println("�ܹ�"+dis+"վ");
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

	public static void getPath(ArrayList<String> path){//·�����
        System.out.println(getexchangeline(path.get(0),path.get(1)));
        System.out.print(" "+path.get(0)+" ");
        int i;
        //�жϻ���
        for(i=0;i<path.size()-2;i++){
            System.out.print(path.get(i+1)+" ");
            if(isExchange(path.get(i),path.get(i+2))){
                System.out.println();
                System.out.println(getexchangeline(path.get(i),path.get(i+1))+" ");
                System.out.println(getexchangeline(path.get(i+1),path.get(i+2)));
                System.out.println(" "+path.get(i+1)+" ");
            }
        }
        System.out.println("->->����  "+path.get(i+1)+" ");
        System.out.println(getexchangeline(path.get(i),path.get(i+1)));
    }

    private static boolean isExchange(String string, String string2) {
		// TODO Auto-generated method stub
		return false;
	}

	//�ж���վ�Ƿ񻻳� �ǵĻ�����ture ����false
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

    //�õ����˵�·��
    public static String getexchangeline(String station1,String station2){
        for(String name:StationSet.get(station1).getLineName()){
            if(StationSet.get(station2).getLineName().contains(name))
            return name;
        }
		return null;
    }

	 }
