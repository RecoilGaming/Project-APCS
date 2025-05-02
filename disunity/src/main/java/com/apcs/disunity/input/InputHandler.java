package com.apcs.disunity.input;

import java.awt.event.*;

/**
 * Handles inputs from any input source
 * 
 * @author Qinzhao Li
 */
public class InputHandler implements KeyListener, MouseListener, MouseMotionListener, FocusListener {

    /* ================ [ KEYLISTENER ] ================ */

    /**
     * Detects when a key is typed
     * 
     * @param e The key event
     */
    @Override
    public void keyTyped(KeyEvent e) { }

    /**
     * Detects when a key is pressed
     * 
     * @param e The key event
     */
    @Override
    public void keyPressed(KeyEvent e) { Inputs.press(Input.getFromKey(e.getKeyCode())); }

    /**
     * Detects when a key is released
     * 
     * @param e The key event
     */
    @Override
    public void keyReleased(KeyEvent e) { Inputs.release(Input.getFromKey(e.getKeyCode())); }

    /* ================ [ MOUSELISTENER ] ================ */

    /**
     * Detects when a mouse button is clicked
     * 
     * @param e The mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) { }

    /**
     * Detects when a mouse button is pressed
     * 
     * @param e The mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) { Inputs.press(Input.getFromMouse(e.getButton())); }

    /**
     * Detects when a mouse button is released
     * 
     * @param e The mouse event
     */
    @Override
    public void mouseReleased(MouseEvent e) { Inputs.release(Input.getFromMouse(e.getButton())); }

    /**
     * Detects when the mouse enters the window
     * 
     * @param e The mouse event
     */
    @Override
    public void mouseEntered(MouseEvent e) { }

    /**
     * Detects when the mouse leaves the window
     * 
     * @param e The mouse event
     */
    @Override
    public void mouseExited(MouseEvent e) { }

    /* ================ [ MOUSEMOTIONLISTENER ] ================ */

    /**
     * Detects when the mouse is dragged
     * 
     * @param e The mouse event
     */
    @Override
    public void mouseDragged(MouseEvent e) { }

    /**
     * Detects when the mouse is moved
     * 
     * @param e The mouse event
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        Inputs.mouseX = e.getX();
        Inputs.mouseY = e.getY();
    }

    /* ================ [ FOCUSLISTENER ] ================ */

    /**
     * Detects when the window gains focus
     * 
     * @param e The focus event
     */
    @Override
    public void focusGained(FocusEvent e) { }

    /**
     * Detects when the window loses focus
     * 
     * @param e The focus event
     */
    @Override
    public void focusLost(FocusEvent e) {
        Inputs.releaseAll();
    }
    
}
