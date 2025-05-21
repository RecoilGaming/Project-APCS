package com.apcs.disunity.app.input;

import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.apcs.disunity.app.rendering.ScalableBuffer;
import com.apcs.disunity.game.Game;
import com.apcs.disunity.math.Vector2;

/**
 * A listener that detects all inputs in the application.
 * 
 * @author Qinzhao Li
 */
public class InputHandler implements KeyListener, MouseListener, MouseMotionListener, FocusListener {

    /* ================ [ KEYLISTENER ] ================ */

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) { Inputs.press(Input.getFromKey(e.getKeyCode())); }

    @Override
    public void keyReleased(KeyEvent e) { Inputs.release(Input.getFromKey(e.getKeyCode())); }

    /* ================ [ MOUSELISTENER ] ================ */

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) { Inputs.press(Input.getFromMouse(e.getButton())); }

    @Override
    public void mouseReleased(MouseEvent e) { Inputs.release(Input.getFromMouse(e.getButton())); }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    /* ================ [ MOUSEMOTIONLISTENER ] ================ */

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        ScalableBuffer buffer = Game.getInstance().getBuffer();
        Rectangle bounds = Game.getInstance().getBounds();
        Vector2 pos = Vector2.of(e.getX(), e.getY());
        Vector2 viewDim = Vector2.of(bounds.width, bounds.height);

        Vector2 newPos = pos.sub(viewDim.div(2)).div(buffer.getScale());
        Inputs.mouseVel = newPos.sub(Inputs.mousePos);
        Inputs.mousePos = newPos;
    }

    /* ================ [ FOCUSLISTENER ] ================ */

    @Override
    public void focusGained(FocusEvent e) {}

    @Override
    public void focusLost(FocusEvent e) { Inputs.releaseAll(); }
}
