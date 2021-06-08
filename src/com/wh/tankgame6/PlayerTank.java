package com.wh.tankgame6;

import java.util.Vector;

/**
 * @author :Hermit
 * @description :玩家坦克
 * @create :2021-05-25 21:51:00
 */
public class PlayerTank extends Tank {
    private final static int maxBulletNum = 10;
    Bullet bullet = null;
    Vector<Bullet> bullets = new Vector<>();
    public PlayerTank(int x, int y,int speed) {
        super(x, y,speed);
    }

    public void shot(){
        if(bullets.size()<maxBulletNum){
            int curX = getX();
            int curY = getY();
            int curDir = getDir();
            switch (curDir){
                case 0://坦克方向向上
                    bullet = new Bullet(curX + 20,curY,curDir);
                    bullet.speed = 6;
                    bullets.add(bullet);
                    break;
                case 1://坦克方向向右
                    bullet = new Bullet(curX + 60,curY + 20,curDir);
                    bullet.speed = 6;
                    bullets.add(bullet);
                    break;
                case 2://坦克方向向下
                    bullet = new Bullet(curX + 20,curY + 60,curDir);
                    bullet.speed = 6;
                    bullets.add(bullet);
                    break;
                case 3://坦克方向向左
                    bullet = new Bullet(curX,curY + 20,curDir);
                    bullet.speed = 6;
                    bullets.add(bullet);
                    break;
            }
            shotSound();
            new Thread(bullet).start();
            System.out.println(bullets.size());
        }else{
            return;
        }
    }


    public void shotSound(){
        AePlayWave shotSound = new AePlayWave("src\\2471.wav");
        shotSound.setCanPlay(true);
        new Thread(shotSound).start();
    }
}
