package com.wh.tankgame6;

/**
 * @author :Hermit
 * @description :
 * @create :2021-05-27 21:19:00
 */
public class Bomb {
    int x,y;
    int life = 9;
    boolean islive = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;

    }


    //减少生命值
    public void lifeDown(){
        if (life > 0){
            life--;
        }else {
            islive = false;
        }
    }

}
