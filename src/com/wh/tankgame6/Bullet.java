package com.wh.tankgame6;

/**
 * @author :Hermit
 * @description :实现射击动作
 * @create :2021-05-26 20:12:00
 */
public class Bullet implements Runnable {
    int x;
    int y;
    int dir;//子弹方向
    int speed = 6;//子弹速度
    boolean isLive = true;

    public Bullet(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    @Override
    public void run() {//不断地更新x y
        while (true){
            try {
                Thread.sleep(50);//如果不休眠则看不到子弹的轨迹
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //根据方向改变x y
            switch (dir){
                case 0://向上
                    y -= speed;
                    break;
                case 1://向右
                    x += speed;
                    break;
                case 2://向下
                    y += speed;
                    break;
                case 3://向左
                    x -= speed;
                    break;
                default:
                    break;
            }
//            System.out.println("子弹x:"+x+" y:"+y);
            if (!(x >= 0 && x<= 960 && y >= 0 && y<= 700)||!isLive){//超出范围或者子弹凉了
                isLive = false;//子弹凉了
//                System.out.println("子弹线程结束");
                break;//结束线程
            }
        }

    }
}
