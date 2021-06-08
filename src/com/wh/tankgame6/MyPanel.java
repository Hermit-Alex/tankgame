package com.wh.tankgame6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/**
 * @author :Hermit
 * @description : 坦克大战绘图区
 * 监听键盘事件
 * 1 实现KeyListener
 * 2 定义事件处理函数
 * 绘制子弹：
 * 1 需要不停的运行paint（）函数
 * 2 需要将MyPanel 实现 Runnable 当做一个线程来使用
 * 敌方坦克：
 * 1 敌方坦克会有自己独特的属性 所以单独建立一个敌方坦克类
 * 2 敌方坦克数量众多，可以建立一个集合存放
 * 3 会涉及到多线程问题 所以使用 Vector（线程安全）存放敌方坦克
 * @create :2021-05-25 21:52:00
 */
public class MyPanel extends JPanel implements KeyListener,Runnable {
    PlayerTank playerTank;
    int[] bornPos = {500,650};
    Vector<EnemyTank> enemyTanks = new Vector<>();//敌方坦克群
    Vector<Node> nodes = new Vector<>();
    int enemyTanksSize = 8;
    int enemyTanksTotalSize = 50;
    int currentTanksSize = 8;
    boolean canOper = true;
    //爆炸效果对象数组
    //当子弹击中坦克时就产生一个爆炸对象
    Vector<Bomb> bombs = new Vector<>();

    //定义三张爆炸的图片 用于显示爆炸效果
    Image img1 = null;
    Image img2 = null;
    Image img3 = null;

    public MyPanel(String key) {//构造器


        File file  = new File(Recorder.getRecordFile());
        if(file.exists()){
            nodes = Recorder.getNodesAndEnemyTankRec();
        }else{
            System.out.println("文件不存在，只能开启新游戏");
            key = "2";
        }
        playerTank = new PlayerTank(bornPos[0],bornPos[1],4);//初始化用户坦克
        switch (key){//1：继续上局游戏 2：开始新游戏
            case "1":
                //初始化敌人坦克集合
                for (int i = 0; i < nodes.size() ; i++){
                    Node node = nodes.get(i);
                    int x = node.getX();
                    int y = node.getY();
                    int dir = node.getDir();
                    EnemyTank enemyTank = new EnemyTank(x,y,2);
                    enemyTank.setEnemyTanks(enemyTanks);//传入到坦克类中
                    enemyTank.setDir(dir);
                    //启动敌人坦克
                    new Thread(enemyTank).start();
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2":
                Recorder.setAllEnemyTankNum(0);
                //初始化敌人坦克集合
                for (int i = 0; i < enemyTanksSize ; i++){
                    EnemyTank enemyTank = new EnemyTank(100*(i+1),10,2);
                    enemyTank.setEnemyTanks(enemyTanks);//传入到坦克类中
                    enemyTank.setDir(2);
                    //启动敌人坦克
                    new Thread(enemyTank).start();
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                break;
        }
        Recorder.setEnemyTanks(enemyTanks);


        img1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        img2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        img3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));


    }


    public void showInfo(Graphics g){
        g.setColor(Color.BLACK);
        Font font = new Font("宋体",Font.BOLD,25);
        g.setFont(font);
        g.drawString("击毁敌方坦克数目",1020,30);
        drawTank(1020,60,g,0,1);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankNum()+"",1080,100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);
        showInfo(g);
        //画玩家坦克和子弹
        drawTank(playerTank.getX(),playerTank.getY(),g,playerTank.getDir(),0);
        if(playerTank.bullets.size()!= 0){
            for(int i = 0; i < playerTank.bullets.size();i++){
                Bullet bullet = playerTank.bullets.get(i);
                if (!bullet.isLive){
                    bombs.add(new Bomb(bullet.x-25,bullet.y-25 ));
                    playerTank.bullets.remove(bullet);//删除子弹
                }
                drawPlayerBullet(bullet.x,bullet.y,g,bullet.dir);
            }
        }
        //画敌人坦克和敌方子弹
        for (int i = 0; i < enemyTanks.size();i++){
            EnemyTank tank = enemyTanks.get(i);
            drawTank(tank.getX(),tank.getY(),g,tank.getDir(),1);
        }
        if(EnemyTank.bullets.size()!=0){//如果敌人挂了 那么打出来的子弹也会继续绘制
            for(int i = 0; i < EnemyTank.bullets.size(); i++){
                if(EnemyTank.bullets.get(i).isLive){
                    drawEnemyBullet(EnemyTank.bullets.get(i).x,
                            EnemyTank.bullets.get(i).y
                            ,g, EnemyTank.bullets.get(i).dir);
                }else{
                    bombs.add(new Bomb(EnemyTank.bullets.get(i).x-25,
                            EnemyTank.bullets.get(i).y-25 ));
                    EnemyTank.bullets.remove(i);
                }

            }
        }

        if(bombs.size()>0){

            for(int i = 0; i < bombs.size();i++) {
                Bomb bomb = bombs.get(i);
                //根据爆炸对象live值绘制
                if (bomb.life > 6) {
                    g.drawImage(img1, bomb.x, bomb.y, 50, 50, this);
                } else if (bomb.life > 3) {
                    g.drawImage(img2, bomb.x, bomb.y, 50, 50, this);
                } else{
                    g.drawImage(img3, bomb.x, bomb.y, 50, 50, this);
                }

                bomb.lifeDown();//刷新爆炸生命
                if(bomb.life ==0){
                    bombs.remove(bomb);//爆炸效果结束后删除该对象
                }

            }
        }



    }


    public void drawPlayerBullet(int x, int y, Graphics g, int dir){
        g.setColor(Color.WHITE);
        switch (dir){
            case 0:
                g.fillRect(x,y,2,10);
                break;
            case 1:
                g.fillRect(x,y,10,2);
                break;
            case 2:
                g.fillRect(x,y,2,10);
                break;
            case 3:
                g.fillRect(x,y,10,2);
                break;
            default:
                break;
        }

    }


    public void drawEnemyBullet(int x, int y, Graphics g, int dir){
        g.setColor(Color.ORANGE);
        switch (dir){
            case 0:
                g.fillRect(x,y,2,10);
                break;
            case 1:
                g.fillRect(x,y,10,2);
                break;
            case 2:
                g.fillRect(x,y,2,10);
                break;
            case 3:
                g.fillRect(x,y,10,2);
                break;
            default:
                break;
        }

    }

    /**
     *
     * @param x 左上角坐标
     * @param y
     * @param g 画笔
     * @param dir 坦克方向 0上 1右 2下 3左
     * @param type 坦克类型 敌我坦克
     */
    public void drawTank(int x,int y,Graphics g,int dir,int type){
        //根据不同的类型设置颜色
        switch (type){
            case 0:
                g.setColor(Color.cyan);//青色
                break;
            case 1:
                g.setColor(Color.RED);
                break;
            case 2:
                g.setColor(Color.ORANGE);
                break;
            case 3:
                g.setColor(Color.YELLOW);
                break;
        }
        //方向
        switch (dir){
            case 0://上
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x + 30,y,10,60,false);
                g.fill3DRect(x + 10,y+10,20,40,false);
                g.fillOval(x+10,y+20,20,20);
                g.drawLine(x+20,y+30,x+20,y);
                break;
            case 1://右
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x ,y+30,60,10,false);
                g.fill3DRect(x + 10,y+10,40,20,false);
                g.fillOval(x+20,y+10,20,20);
                g.drawLine(x+30,y+20,x+60,y+20);
                break;
            case 2://下
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x + 30,y,10,60,false);
                g.fill3DRect(x + 10,y+10,20,40,false);
                g.fillOval(x+10,y+20,20,20);
                g.drawLine(x+20,y+30,x+20,y+60);
                break;
            case 3://左
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x ,y+30,60,10,false);
                g.fill3DRect(x + 10,y+10,40,20,false);
                g.fillOval(x+20,y+10,20,20);
                g.drawLine(x+30,y+20,x,y+20);
                break;
            default:
                System.out.println("没有处理");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //处理wdsa按键按下
    @Override
    public void keyPressed(KeyEvent e) {
        if(canOper){
            if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP ){//按下w键
                playerTank.setDir(0);//方向向上
                playerTank.moveUp();
            }else if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT){
                playerTank.setDir(1);//方向向右
                playerTank.moveRight();
            }else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN){
                playerTank.setDir(2);//方向向下
                playerTank.moveDwon();
            }else if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT){
                playerTank.setDir(3);//方向向左
                playerTank.moveLeft();
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE){
                playerTank.shot();
            }
        }
        repaint();//刷新
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {//每隔50ms刷新整个绘图区
        while(true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //判断玩家子弹击中敌方坦克
            if(playerTank.bullets!=null && playerTank.bullets.size()>0){
                for (int i = 0; i < playerTank.bullets.size();i++){
                    for (int j = 0;j<enemyTanks.size();j++){
                        hitEnemyTank(playerTank.bullets.get(i),enemyTanks.get(j));
                    }
                }
            }

            //判断玩家是否被击中
            if(EnemyTank.bullets!=null && EnemyTank.bullets.size()>0){
                for (int i = 0; i< EnemyTank.bullets.size(); i++){
                    hitPlayerTank(EnemyTank.bullets.get(i),playerTank);
                }
            }
            if (!playerTank.isLive()){
                countDownPlayerRelive();
                playerTank.setX(bornPos[0]);
                playerTank.setY(bornPos[1]);
                playerTank.setDir(0);
                playerTank.setLive(true);
            }
            this.repaint();
        }
    }



    public void hitEnemyTank(Bullet bullet, EnemyTank tank){
        switch (tank.getDir()){
            case 0:
            case 2:
                if(bullet.x > tank.getX()
                && bullet.x < tank.getX()+40
                && bullet.y > tank.getY()
                && bullet.y < tank.getY()+60){
                    bullet.isLive = false;//子弹生命结束
                    tank.setLive(false);
                    enemyTanks.remove(tank);
                    reBornEnemyTank();
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);//被击中产生爆炸对象
                    Recorder.addAllEnemyTankNum();
                    bombSound();
                    System.out.println("敌方坦克被击中");
                }
                break;
            case 1:
            case 3:
                if(bullet.x > tank.getX()
                && bullet.x < tank.getX()+60
                && bullet.y > tank.getY()
                && bullet.y < tank.getY()+40){
                    bullet.isLive = false;//子弹生命结束
                    tank.setLive(false);
                    enemyTanks.remove(tank);
                    reBornEnemyTank();
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);//被击中产生爆炸对象
                    Recorder.addAllEnemyTankNum();
                    bombSound();
                    System.out.println("敌方坦克被击中");
                }
                break;
            default:
                break;

        }
    }

    public void hitPlayerTank(Bullet bullet, PlayerTank playerTank){
        switch (playerTank.getDir()){
            case 0:
            case 2:
                if(bullet.x > playerTank.getX()
                        && bullet.x < playerTank.getX()+40
                        && bullet.y > playerTank.getY()
                        && bullet.y < playerTank.getY()+60){
                    bullet.isLive = false;//子弹生命结束
                    EnemyTank.bullets.remove(bullet);
                    playerTank.setLive(false);
                    Bomb bomb = new Bomb(playerTank.getX(), playerTank.getY());
                    bombs.add(bomb);//被击中产生爆炸对象
                    bombSound();
                    System.out.println("玩家坦克被击中");
                }
                break;
            case 1:
            case 3:
                if(bullet.x > playerTank.getX()
                        && bullet.x < playerTank.getX()+60
                        && bullet.y > playerTank.getY()
                        && bullet.y < playerTank.getY()+40){
                    bullet.isLive = false;//子弹生命结束
                    EnemyTank.bullets.remove(bullet);
                    playerTank.setLive(false);
                    Bomb bomb = new Bomb(playerTank.getX(), playerTank.getY());
                    bombs.add(bomb);//被击中产生爆炸对象
                    bombSound();
                    System.out.println("玩家坦克被击中");
                }
                break;
            default:
                break;

        }
    }

    public void countDownPlayerRelive(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                canOper = false;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                canOper = true;
            }
        }).start();
    }

    public void reBornEnemyTank(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(currentTanksSize <= enemyTanksTotalSize){
                    int i = (int) (10 * Math.random());
                    EnemyTank enemyTank = new EnemyTank(100* i ,10,2);
                    enemyTank.setEnemyTanks(enemyTanks);//传入到坦克类中
                    enemyTank.setDir(2);
                    currentTanksSize++;
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //启动敌人坦克
                    new Thread(enemyTank).start();
                    enemyTanks.add(enemyTank);
                }


            }
        }).start();
    }

    public void bombSound(){
        AePlayWave bombSound = new AePlayWave("src\\11440.wav");
        bombSound.setCanPlay(true);
        new Thread(bombSound).start();
    }



}
