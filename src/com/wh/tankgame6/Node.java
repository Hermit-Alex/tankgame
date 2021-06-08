package com.wh.tankgame6;

import java.io.Serializable;

/**
 * @author :Hermit
 * @description :
 * @create :2021-06-03 23:35:00
 */
public class Node implements Serializable {

    private int x;
    private int y;
    private int dir;

    public Node(int x, int y, int dir) {
        this.x = x;
        this.y = y;
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

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }
}
