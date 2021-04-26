package com.andrew121410.autoscreenshoter;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    private Timer timer;
    private int number = 0;

    private GlobalKeyListener globalKeyListener;
    private GlobalMouseListener globalMouseListener;

    public static void main(String[] args) {
        new Main(args);
    }

    public Main(String[] args) {
        LogManager.getLogManager().reset();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException exception) {
            exception.printStackTrace();
        }

        this.globalKeyListener = new GlobalKeyListener(this);
        GlobalScreen.addNativeKeyListener(this.globalKeyListener);

        this.globalMouseListener = new GlobalMouseListener(this);
        GlobalScreen.addNativeMouseListener(this.globalMouseListener);
        GlobalScreen.addNativeMouseMotionListener(this.globalMouseListener);
    }

    public void start() {
        this.timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                takeScreenshot();
                for (Point point : globalMouseListener.getEventsAfterWords()) {
                    try {
                        Robot robot = new Robot();
//                        moveMouse(point.x, point.y, 50, robot);
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 3000, 3000);
    }

    public void takeScreenshot() {
        this.number++;

        Point a = this.globalMouseListener.getPoint();
        Point b = this.globalMouseListener.getPointTwo();

        Rectangle rectangle = new Rectangle();
        rectangle.setFrameFromDiagonal(a, b);
        try {
            BufferedImage image = new Robot().createScreenCapture(rectangle);
            ImageIO.write(image, "png", new File("screenshot-" + number + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void moveMouse(int x, int y, int maxTimes, Robot screenWin) {
        for (int count = 0; (MouseInfo.getPointerInfo().getLocation().getX() != x ||
                MouseInfo.getPointerInfo().getLocation().getY() != y) &&
                count < maxTimes; count++) {
            screenWin.mouseMove(x, y);
        }
    }

    public GlobalKeyListener getGlobalKeyListener() {
        return globalKeyListener;
    }

    public GlobalMouseListener getGlobalMouseListener() {
        return globalMouseListener;
    }

    public Timer getTimer() {
        return timer;
    }
}
