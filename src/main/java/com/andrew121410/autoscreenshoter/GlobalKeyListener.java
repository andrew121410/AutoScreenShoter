package com.andrew121410.autoscreenshoter;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {

    private Main main;

    private boolean controlKey;
    private boolean altKey;

    public GlobalKeyListener(Main main) {
        this.main = main;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
                System.exit(1);
            } catch (NativeHookException nativeHookException) {
                nativeHookException.printStackTrace();
            }
        } else if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            this.controlKey = true;
        } else if (e.getKeyCode() == NativeKeyEvent.VC_ALT) {
            this.altKey = true;
        } else if (e.getKeyCode() == NativeKeyEvent.VC_INSERT) {
            this.main.start();
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            this.controlKey = false;
        } else if (e.getKeyCode() == NativeKeyEvent.VC_ALT) {
            this.altKey = false;
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    public boolean isControlKey() {
        return controlKey;
    }

    public boolean isAltKey() {
        return altKey;
    }
}
