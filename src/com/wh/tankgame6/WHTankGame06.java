package com.wh.tankgame6;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * @author :Hermit
 * @description :
 * @create :2021-05-25 21:55:00
 */
public class WHTankGame06 extends JFrame {

    MyPanel mp = null;
    public static void main(String[] args) {


        WHTankGame06 whTankGame01 = new WHTankGame06();



    }

    public WHTankGame06(){
        AePlayWave openBGM = new AePlayWave("src\\TankBgm.wav");
        openBGM.setCanPlay(true);
        AePlayWave BGM = new AePlayWave("src\\BGM.wav");
        BGM.setCanPlay(true);
        new Thread(openBGM).start();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请选择 1：继续上局游戏 2：开始新游戏");
        String key = scanner.next();
        //初始化窗口
        mp = new MyPanel(key);
        //将mp放入到Thread中并启动
        Thread t1 = new Thread(mp);
        t1.setName("绘图线程");
        t1.start();
        openBGM.setCanPlay(false);

        this.add(mp);//把面板放入到窗口
        this.addKeyListener(mp);//添加监听器
        this.setSize(1280,750);//设置窗口大小
        //当关闭窗口时程序完全退出
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置默认关闭操作
        this.setVisible(true);//设置为可视
        new Thread(BGM).start();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                BGM.setCanPlay(false);
                System.out.println("窗口关闭");
                Recorder.keepRecord();
            }
        });
    }
}
