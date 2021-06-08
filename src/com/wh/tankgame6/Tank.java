package com.wh.tankgame6;

/**
 * @author :Hermit
 * @description :
 * @create :2021-05-25 21:50:00
 */
public class Tank {
    private int x;//坦克横纵坐标
    private int y;
    private int dir;//坦克方向 0上 1右 2下 3左
    private int speed = 2;
    private boolean isLive = true;

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }



    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean moveUp(){
        if(y>0){
            y-=speed;
            return true;
        }
        return false;

    }
    public boolean moveDwon(){
        if(y<750-100){
            y+=speed;
            return true;
        }
        return false;
    }

    public boolean moveRight(){
        if(x<1000-100){
            x+=speed;
            return true;
        }
        return false;
    }

    public boolean moveLeft(){
        if(x>0){
            x-=speed;
            return true;
        }
        return false;
    }


    public Tank(int x, int y,int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
