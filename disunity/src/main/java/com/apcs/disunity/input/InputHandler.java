package com.apcs.disunity.input;

import com.apcs.disunity.server.SyncHandler;

import java.awt.event.*;

/**
 * Listens for inputs and updates them
 * 
 * @author Qinzhao Li
 */
public class InputHandler implements KeyListener, MouseListener, MouseMotionListener, FocusListener {

    /* ================ [ KEYLISTENER ] ================ */

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) { Inputs.runtimeInstance().press(Input.getFromKey(e.getKeyCode())); }

    @Override
    public void keyReleased(KeyEvent e) { Inputs.runtimeInstance().release(Input.getFromKey(e.getKeyCode())); }

    /* ================ [ MOUSELISTENER ] ================ */

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) { Inputs.runtimeInstance().press(Input.getFromMouse(e.getButton())); }

    @Override
    public void mouseReleased(MouseEvent e) { Inputs.runtimeInstance().release(Input.getFromMouse(e.getButton())); }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    /* ================ [ MOUSEMOTIONLISTENER ] ================ */

    @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) {
        Inputs.runtimeInstance().mouseX = e.getX();
        Inputs.runtimeInstance().mouseY = e.getY();
    }

    /* ================ [ FOCUSLISTENER ] ================ */

    @Override
    public void focusGained(FocusEvent e) { }

    @Override
    public void focusLost(FocusEvent e) {
        Inputs.runtimeInstance().releaseAll();
    }
}
