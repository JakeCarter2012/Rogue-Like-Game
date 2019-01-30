package SourceFiles.GameLogic;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//Handles key events for keyboard input
public class KeyControl extends KeyAdapter{
    /*
    KeyControl is used to handle the player's keyboard inputs, and uses a GameEvent
    to update the player character for which keys are currently being pressed.
    */
    private GameEvents events;
    
    public KeyControl() {}
    
    public KeyControl(GameEvents events){
        this.events = events;
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        events.setKey(e);
    }
    
    @Override
    public void keyReleased(KeyEvent e){
        events.setKey(e);
    }
}