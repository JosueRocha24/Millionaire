package org.academiadecodigo.millionaire;

import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

public class MoveEvents implements KeyboardHandler {


    private Game game;

    private boolean onMenu;
    private boolean onInstructions;
    private boolean onGame;
    private boolean onTestScore;
    private boolean haveNewScore;
    private boolean insertingScore;
    private boolean gaveAnswer;
    private boolean lockedAnswer;

    public MoveEvents(Game game) {
        this.game = game;

        onMenu = true;

        Keyboard k = new Keyboard(this);

        addKey(k, KeyboardEvent.KEY_1);
        addKey(k, KeyboardEvent.KEY_2);
        addKey(k, KeyboardEvent.KEY_3);
        addKey(k, KeyboardEvent.KEY_4);
        addKey(k, KeyboardEvent.KEY_SPACE);
        for (int i = KeyboardEvent.KEY_A; i <= KeyboardEvent.KEY_Z; i++) {
            addKey(k, i);
        }



    }

    private void addKey(Keyboard k, int key){
        KeyboardEvent event = new KeyboardEvent();
        event.setKey(key);
        event.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(event);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {

        if(insertingScore){
            if(keyboardEvent.getKey() > KeyboardEvent.KEY_A && keyboardEvent.getKey() < KeyboardEvent.KEY_Z) {
                game.nameAddChar((char) keyboardEvent.getKey());
            }
            if(keyboardEvent.getKey() == KeyboardEvent.KEY_SPACE){
                insertingScore = false;
                onMenu = true;
                game.insertNewScore();
                game.menu();
            }
            return;
        }

        switch(keyboardEvent.getKey()){
            case KeyboardEvent.KEY_1:
                if(onMenu) {
                    game.nextQuestion();
                    onGame = true;
                    onMenu = false;
                    return;
                }
                if(onGame && !lockedAnswer){
                    game.nextAnswer(1);
                    gaveAnswer = true;
                    return;
                }
                break;
            case KeyboardEvent.KEY_2:
                if(onGame && !lockedAnswer){
                    game.nextAnswer(2);
                    gaveAnswer = true;
                    return;
                }
                if(onMenu) {
                    game.instructions();
                    onInstructions = true;
                    onMenu = false;
                    onGame = false;
                }
                break;
            case KeyboardEvent.KEY_3:
                if(onMenu){
                    System.exit(0);
                }
                if(onGame && !lockedAnswer){
                    game.nextAnswer(3);
                    gaveAnswer = true;
                    return;
                }
                break;
            case KeyboardEvent.KEY_4:

                if(onGame && !lockedAnswer){
                    game.nextAnswer(4);
                    gaveAnswer = true;
                    return;
                }
                break;
            case KeyboardEvent.KEY_S:
                if(onGame && gaveAnswer && !lockedAnswer && !insertingScore){
                    if(!game.testAnswer()){
                        onTestScore = true;
                    }
                    lockedAnswer = true;
                    gaveAnswer = false;
                    return;
                }
            case KeyboardEvent.KEY_SPACE:
                if(onGame && lockedAnswer){
                    gaveAnswer = false;
                    lockedAnswer = false;
                    if(!game.nextQuestion() || onTestScore){
                        onTestScore = true;
                        onGame = false;
                        haveNewScore = game.testScore();
                        return;
                    }
                }
                if(onTestScore && haveNewScore){
                    game.insertNewScorePage();
                    onTestScore = false;
                    insertingScore = true;
                    return;
                }
                if(insertingScore){
                    onMenu = true;
                    insertingScore = false;
                    game.menu();
                }
                if(onInstructions){
                    game.menu();
                    onMenu = true;
                    onInstructions = false;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {

    }

}
