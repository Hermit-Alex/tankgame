package com.wh.tankgame6;

import java.util.Vector;

/**
 * @author :Hermit
 * @description : 敌方坦克类
 * @create :2021-05-26 16:29:00
 */
public class EnemyTank extends Tank implements Runnable{
    //在敌人坦克类中使用Vector 保存多颗子弹
    static Vector<Bullet> bullets = new Vector<>();
    private final static int maxBulletNum = 15;

    Vector<EnemyTank> enemyTanks = new Vector<>();//可以得到敌人坦克的所有集合





    public EnemyTank(int x, int y, int speed) {
        super(x, y, speed);

    }

    public void shot(Bullet bullet){
        if(bullets.size()<maxBulletNum){
            int curX = getX();
            int curY = getY();
            int curDir = getDir();
            switch (curDir){
                case 0://坦克方向向上
                    bullet = new Bullet(curX + 20,curY,curDir);
                    bullets.add(bullet);
                    break;
                case 1://坦克方向向右
                    bullet = new Bullet(curX + 60,curY + 20,curDir);
                    bullets.add(bullet);
                    break;
                case 2://坦克方向向下
                    bullet = new Bullet(curX + 20,curY + 60,curDir);
                    bullets.add(bullet);
                    break;
                case 3://坦克方向向左
                    bullet = new Bullet(curX,curY + 20,curDir);
                    bullets.add(bullet);
                    break;
            }
            new Thread(bullet).start();
            shotSound();
        }else{
            return;
        }
    }

    //MyPanel 的成员设置到坦克类中
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //编写一个方法返回是否碰到了其他坦克
    public boolean isTouch(){
        switch (this.getDir()){
            case 0://上
                //让当前坦克与其他比较
                for(int i = 0; i < enemyTanks.size();i++){
                    EnemyTank tank = enemyTanks.get(i);
                    //排除自己
                    if(this != tank){
                        //上下 [x,x+40] [y,y+60]
                        if(tank.getDir() ==0||tank.getDir() == 2){
                            //当前坦克左上角 [x,y] 右上角 [x+40,y]
                            if(this.getX() >= tank.getX()
                                    && this.getX() <= tank.getX() + 40
                                    && this.getY() >= tank.getY()
                                    && this.getY() <= tank.getY() + 60){
                                return true;
                            }

                            if(this.getX() + 40 >= tank.getX()
                                    && this.getX() + 40 <= tank.getX() + 40
                                    && this.getY() >= tank.getY()
                                    && this.getY() <= tank.getY() + 60){
                                return true;
                            }
                        }
                        //左右 [x,x+60] [y,y+40]
                        if(tank.getDir() ==1||tank.getDir() == 3){
                            //当前坦克左上角 [x,y] 右上角 [x+40,y]
                            if(this.getX() >= tank.getX()
                                    && this.getX() <= tank.getX() + 60
                                    && this.getY() >= tank.getY()
                                    && this.getY() <= tank.getY() + 40){
                                return true;
                            }

                            if(this.getX() + 40 >= tank.getX()
                                    && this.getX() + 40 <= tank.getX() + 60
                                    && this.getY() >= tank.getY()
                                    && this.getY() <= tank.getY() + 40){
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1://右
                //让当前坦克与其他比较
                for(int i = 0; i < enemyTanks.size();i++){
                    EnemyTank tank = enemyTanks.get(i);
                    //排除自己
                    if(this != tank){
                        //上下 [x,x+40] [y,y+60]
                        if(tank.getDir() ==0||tank.getDir() == 2){
                            //当前坦克右上角 [x+60,y] 右下角[x+60,y+40]
                            if(this.getX()+60 >= tank.getX()
                                    && this.getX()+60 <= tank.getX() + 40
                                    && this.getY() >= tank.getY()
                                    && this.getY() <= tank.getY() + 60){
                                return true;
                            }

                            if(this.getX() + 60 >= tank.getX()
                                    && this.getX() + 60 <= tank.getX() + 40
                                    && this.getY() + 40 >= tank.getY()
                                    && this.getY() + 40 <= tank.getY() + 60){
                                return true;
                            }
                        }
                        //左右 [x,x+60] [y,y+40]
                        if(tank.getDir() ==1||tank.getDir() == 3){
                            //当前坦克右上角 [x+60,y] 右下角[x+60,y+40]
                            if(this.getX() +60 >= tank.getX()
                                    && this.getX() + 60 <= tank.getX() + 60
                                    && this.getY() >= tank.getY()
                                    && this.getY() <= tank.getY() + 40){
                                return true;
                            }

                            if(this.getX() + 60 >= tank.getX()
                                    && this.getX() + 60 <= tank.getX() + 60
                                    && this.getY() + 40 >= tank.getY()
                                    && this.getY() + 40 <= tank.getY() + 40){
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2://下
                //让当前坦克与其他比较
                for(int i = 0; i < enemyTanks.size();i++){
                    EnemyTank tank = enemyTanks.get(i);
                    //排除自己
                    if(this != tank){
                        //上下 [x,x+40] [y,y+60]
                        if(tank.getDir() ==0||tank.getDir() == 2){
                            //当前坦克左下角 [x,y+60] 右下角[x+40,y+60]
                            if(this.getX() >= tank.getX()
                                    && this.getX() <= tank.getX() + 40
                                    && this.getY() + 60 >= tank.getY()
                                    && this.getY() + 60<= tank.getY() + 60){
                                return true;
                            }

                            if(this.getX() + 40 >= tank.getX()
                                    && this.getX() + 40 <= tank.getX() + 40
                                    && this.getY() + 60 >= tank.getY()
                                    && this.getY() + 60 <= tank.getY() + 60){
                                return true;
                            }
                        }
                        //左右 [x,x+60] [y,y+40]
                        if(tank.getDir() ==1||tank.getDir() == 3){
                            //当前坦克左下角 [x,y+60] 右下角[x+40,y+60]
                            if(this.getX() >= tank.getX()
                                    && this.getX() <= tank.getX() + 60
                                    && this.getY() + 60 >= tank.getY()
                                    && this.getY() + 60<= tank.getY() + 40){
                                return true;
                            }

                            if(this.getX() + 40 >= tank.getX()
                                    && this.getX() + 40 <= tank.getX() + 60
                                    && this.getY() + 60 >= tank.getY()
                                    && this.getY() + 60 <= tank.getY() + 40){
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3://左
                //让当前坦克与其他比较
                for(int i = 0; i < enemyTanks.size();i++){
                    EnemyTank tank = enemyTanks.get(i);
                    //排除自己
                    if(this != tank){
                        //上下 [x,x+40] [y,y+60]
                        if(tank.getDir() ==0||tank.getDir() == 2){
                            //当前坦克左上角 [x,y] 左下角[x,y+40]
                            if(this.getX() >= tank.getX()
                                    && this.getX() <= tank.getX() + 40
                                    && this.getY() >= tank.getY()
                                    && this.getY() <= tank.getY() + 60){
                                return true;
                            }

                            if(this.getX()  >= tank.getX()
                                    && this.getX()  <= tank.getX() + 40
                                    && this.getY() + 40 >= tank.getY()
                                    && this.getY() + 40 <= tank.getY() + 60){
                                return true;
                            }
                        }
                        //左右 [x,x+60] [y,y+40]
                        if(tank.getDir() ==1||tank.getDir() == 3){
                            //当前坦克左上角 [x,y] 左下角[x,y+40]
                            if(this.getX() >= tank.getX()
                                    && this.getX() <= tank.getX() + 60
                                    && this.getY() >= tank.getY()
                                    && this.getY() <= tank.getY() + 40){
                                return true;
                            }

                            if(this.getX() >= tank.getX()
                                    && this.getX()<= tank.getX() + 60
                                    && this.getY() + 40 >= tank.getY()
                                    && this.getY() + 40 <= tank.getY() + 40){
                                return true;
                            }
                        }
                    }
                }
                break;

        }
        return false;
    }



    @Override
    public void run() {//一旦开始多线程就要考虑多线程什么时候结束

        try {
            Thread.sleep(1000);//休眠一秒后开始移动
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //坦克移动 改坐标和方向
        int dirChangeIndex = 0;
        boolean canMove = true;
//        int dir = (int) (10*Math.random()) %  4;//也可以用以下写法
        while (true){
            canMove = true;
            switch (getDir()){
                case 0://向上
                    canMove = moveUp();
                    canMove &= !isTouch();
                    break;
                case 1://向右
                    canMove = moveRight();
                    canMove &= !isTouch();
                    break;
                case 2://向下
                    canMove = moveDwon();
                    canMove &= !isTouch();
                    break;
                case 3://向左
                    canMove = moveLeft();
                    canMove &= !isTouch();
                    break;
                default:
                    break;
            }
            if ((dirChangeIndex=dirChangeIndex%30)==0||!canMove){
                dirChangeIndex = 0;
                setDir((int)(Math.random()*4));
            }
            if(Math.random()>0.5 && dirChangeIndex%10==0){
                Bullet bullet = new Bullet(this.getX(),this.getY(),this.getDir());
                shot(bullet);
            }

            try {
                Thread.sleep(50);
                dirChangeIndex++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(!isLive()){
                break;
            }


        }
    }

    public void shotSound(){
        AePlayWave shotSound = new AePlayWave("src\\2471.wav");
        shotSound.setCanPlay(true);
        new Thread(shotSound).start();
    }
}
