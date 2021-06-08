package com.wh.tankgame6;

import java.io.*;
import java.util.Arrays;
import java.util.Vector;

/**
 * @author :Hermit
 * @description :记录游戏相关信息
 * @create :2021-06-03 21:30:00
 */
public class Recorder {
    //定义变量
    private static int allEnemyTankNum = 0;
    //定义IO对象 写数据到文件中
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile =  "src\\myRecord.txt";
    private static Vector<EnemyTank> enemyTanks = null;

    public static String getRecordFile() {
        return recordFile;
    }

    private static Vector<Node> nodes = new Vector<>();

    //读取文件 在继续上局的时候调用
    public static Vector<Node> getNodesAndEnemyTankRec(){
        Vector<Node> resNodes = new Vector<>();
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            //循环读取 生成node集合
            String line = "";
            while((line = br.readLine())!=null){
                String[] xyd = line.split(" ");
                System.out.println(Arrays.toString(xyd));
                Node node = new Node(Integer.parseInt(xyd[0]),
                        Integer.parseInt(xyd[1]),
                        Integer.parseInt(xyd[2]));
                nodes.add(node);
                resNodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null){
                try {
                    br.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return resNodes;
    }



    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    //击毁坦克加分
    public static void addAllEnemyTankNum(){
        Recorder.allEnemyTankNum++;
    }

    public static void keepRecord (){

        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum+"\r\n");


            for(int i = 0; i < enemyTanks.size();i++){
                EnemyTank enemyTank = enemyTanks.get(i);
                if(enemyTank.isLive()){
                    int x = enemyTank.getX();
                    int y = enemyTank.getY();
                    int dir = enemyTank.getDir();
                    String record = x+" "+ y+" "+dir;
                    bw.write(record+"\r\n");
                }


            }
            System.out.println("保存记录");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bw != null){
                try {
                    bw.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
