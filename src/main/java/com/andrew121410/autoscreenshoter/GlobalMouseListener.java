package com.andrew121410.autoscreenshoter;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GlobalMouseListener implements NativeMouseInputListener {

    private Main main;
    private Point point;
    private Point pointTwo;

    private List<Point> clickEventsAfter;

    public GlobalMouseListener(Main main) {
        this.clickEventsAfter = new ArrayList<>();
        this.main = main;
    }

    public void nativeMouseClicked(NativeMouseEvent e) {
        if (this.main.getGlobalKeyListener().isAltKey()) {
            this.clickEventsAfter.add(e.getPoint());
            System.out.println("Registered event clicked.");
        }
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        if (this.main.getGlobalKeyListener().isControlKey()) {
            this.point = MouseInfo.getPointerInfo().getLocation();
            System.out.println("Point A has been set:" + e.getX() + ":" + e.getY());
        }
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        if (this.main.getGlobalKeyListener().isControlKey()) {
            this.pointTwo = MouseInfo.getPointerInfo().getLocation();
            System.out.println("Point B has been set:" + e.getX() + ":" + e.getY());
        }
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
    }

    public Point getPoint() {
        return point;
    }

    public Point getPointTwo() {
        return pointTwo;
    }

    public List<Point> getEventsAfterWords() {
        return clickEventsAfter;
    }
}
