package org.academiadecodigo.millionaire;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.graphics.Text;
import org.academiadecodigo.simplegraphics.pictures.Picture;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Game {

    public static final int PADDING = 10;
    private Picture background;
    private Rectangle[] recsMenu;
    private Text[] textsMenu;
    private Rectangle[] rectangles;
    private Text[] texts;
    private Question[] questions;
    private int questionN;

    private Text[] textInstruct;
    private Rectangle recInstruct;

    private int currentAns;
    private int score;

    private Text textScore;
    private Text textScore2;
    private Text textInsertName;
    private Text textName;
    private String name;

    private Sound intro;
    private Sound right;
    private Sound wrong;
    private Sound nextQuest;

    private boolean wrongAnswer;

    Game() {
        
        background = new Picture(PADDING, PADDING, "resources/background.jpg");
        background.draw();

        recsMenu = new Rectangle[3];
        textsMenu = new Text[3];
        rectangles = new Rectangle[5];
        texts = new Text[5];
        questions = new Question[14];
        textInstruct = new Text[3];
        name = " ";

        intro = new Sound("/resources/intro.wav");
        right = new Sound("/resources/right.wav");
        wrong = new Sound("/resources/wrong.wav");
        nextQuest = new Sound("/resources/nextQuest.wav");


        init();

        menu();
        intro.play(true);
        intro.loopIndef();

    }

    public void instructions(){

        for (int i = 0; i < recsMenu.length; i++) {
            recsMenu[i].delete();
            textsMenu[i].delete();
        }

        recInstruct.fill();
        for (int i = 0; i < textInstruct.length; i++) {
            textInstruct[i].draw();
        }

    }

    public void menu(){

        rectangles[currentAns].setColor(Color.BLUE);
        rectangles[currentAns].fill();
        if(questionN > 0) {
            rectangles[questions[questionN - 1].getRightAnswer()].setColor(Color.BLUE);
            rectangles[questions[questionN - 1].getRightAnswer()].fill();
        }

        wrongAnswer = false;
        questionN = 0;
        score = 0;
        name = "";

        for (int i = 0; i < textInstruct.length; i++) {
            textInstruct[i].delete();
        }
        recInstruct.delete();


        textInsertName.delete();
        textName.delete();

        for (int i = 0; i < rectangles.length; i++) {
            rectangles[i].delete();
            texts[i].delete();
        }

        for (int i = 0; i < recsMenu.length; i++) {
            recsMenu[i].fill();
            textsMenu[i].draw();
        }

    }

    public boolean nextQuestion() {

        intro.stop();
        right.stop();
        wrong.stop();

        if(!wrongAnswer) {
            nextQuest.play(true);
        }

        if(questionN >= questions.length){
            questionN = 0;
            return false;
        }

        for (int i = 0; i < recsMenu.length; i++) {
            recsMenu[i].delete();
            textsMenu[i].delete();
        }

        for (int i = 0; i < texts.length; i++) {
            texts[i].delete();
        }


        rectangles[currentAns].setColor(Color.BLUE);
        rectangles[currentAns].fill();
        if(questionN > 0) {
            rectangles[questions[questionN - 1].getRightAnswer()].setColor(Color.BLUE);
            rectangles[questions[questionN - 1].getRightAnswer()].fill();
        }


        texts[0] = new Text(rectangles[0].getX() + rectangles[0].getWidth()/2, rectangles[0].getY() + rectangles[0].getHeight()/2, questions[questionN].getQuestion());
        texts[0].translate(- texts[0].getWidth()/2, -texts[0].getHeight()/2);
        texts[0].setColor(Color.WHITE);
        //texts[0].setText(questions[questionN].getQuestion());
        texts[1].setText(questions[questionN].getAnswers()[0]);
        texts[2].setText(questions[questionN].getAnswers()[1]);
        texts[3].setText(questions[questionN].getAnswers()[2]);
        texts[4].setText(questions[questionN].getAnswers()[3]);

        for (int i = 0; i < rectangles.length; i++) {
            rectangles[i].fill();
            texts[i].draw();
        }

        return true;
    }

    public void nextAnswer(int ans){
        for (int i = 1; i < 5; i++) {
            if(ans == i){
                rectangles[i].setColor(Color.ORANGE);
                rectangles[i].fill();
                texts[i].draw();
                continue;
            }
            rectangles[i].setColor(Color.BLUE);
            rectangles[i].fill();
            texts[i].draw();
        }
        currentAns = ans;
    }

    public boolean testAnswer(){


        if(currentAns == questions[questionN].getRightAnswer()){
            rectangles[currentAns].setColor(Color.GREEN);
            rectangles[currentAns].fill();
            texts[currentAns].draw();
            score++;
            questionN++;

            nextQuest.stop();
            right.play(true);
            return true;
        }
        rectangles[questions[questionN].getRightAnswer()].setColor(Color.GREEN);
        rectangles[questions[questionN].getRightAnswer()].fill();
        rectangles[currentAns].setColor(Color.RED);
        rectangles[currentAns].fill();
        questionN++;

        nextQuest.stop();
        wrong.play(true);
        wrongAnswer = true;
        return false;
    }

    public boolean testScore(){
        rectangles[currentAns].setColor(Color.BLUE);
        rectangles[currentAns].fill();
        if(questionN > 0) {
            rectangles[questions[questionN - 1].getRightAnswer()].setColor(Color.BLUE);
            rectangles[questions[questionN - 1].getRightAnswer()].fill();
        }

        questionN = 0;

        for (int i = 0; i < rectangles.length; i++) {
            rectangles[i].delete();
            texts[i].delete();
        }

        textScore = new Text(background.getWidth()/2, background.getHeight()/2, "Your score is:");
        Integer scoreToInt = (Integer) score;
        textScore2 = new Text(background.getWidth()/2, background.getHeight()/2, scoreToInt.toString());
        textScore.translate(- textScore.getWidth()/2, - textScore.getHeight());
        textScore2.translate(- textScore2.getWidth()/2, textScore2.getHeight());
        textScore.setColor(Color.WHITE);
        textScore2.setColor(Color.WHITE);
        textScore.draw();
        textScore2.draw();


        return true;
    }

    public void insertNewScorePage(){


        textScore.delete();
        textScore2.delete();


        textInsertName.draw();

    }

    public void nameAddChar(char c){
        name += c;
        textName.delete();
        textName = new Text(background.getWidth()/2, background.getHeight()/2, name);
        textName.translate(- textName.getWidth()/2, textName.getHeight());
        textName.setColor(Color.WHITE);
        textName.draw();
    }

    public void insertNewScore(){
        intro.play(true);
        intro.loopIndef();
    }

    
    private void init(){


        int backX = PADDING + 30 ;
        int backY = background.getHeight()/2 -75;
        int backWidth = background.getWidth() - 30 * 2;
        int backHeight = 150;
        
        rectangles[0] = new Rectangle(backX, backY, backWidth, backHeight);
        rectangles[1] = new Rectangle(backX, backY + backHeight + 50, backWidth/2 - 25, 80);
        rectangles[2] = new Rectangle(backX + rectangles[1].getWidth() + 50, backY + backHeight + 50, backWidth/2 - 25, 80);
        rectangles[3] = new Rectangle(rectangles[1].getX(), rectangles[1].getY() + rectangles[1].getHeight() + 35, rectangles[1].getWidth(), rectangles[1].getHeight());
        rectangles[4] = new Rectangle(rectangles[2].getX(), rectangles[2].getY() + rectangles[2].getHeight() + 35, rectangles[2].getWidth(), rectangles[2].getHeight());

        for (int i = 0; i < rectangles.length; i++) {
            rectangles[i].setColor(Color.BLUE);
        }
        
        texts[0] = new Text(backX, background.getHeight()/2 - 30, "What does IP stand for?");
        texts[0].translate(backWidth/2 - texts[0].getWidth()/2, 0);
        texts[0].setColor(Color.WHITE);


        for (int i = 1; i < texts.length; i++) {
            texts[i] = new Text(rectangles[i].getX() + 5, rectangles[i].getY() + rectangles[i].getHeight()/2, "Option " + i);
            texts[i].translate(0,- texts[i].getHeight()/2);
            texts[i].setColor(Color.WHITE);
        }

        fillQuestions();
        drawMenu();
        fillInstructions();

        textInsertName = new Text(background.getWidth()/2, background.getHeight()/2, "New Top Score! Insert your name:");
        textName = new Text(background.getWidth()/2, background.getHeight()/2, name);
        textInsertName.translate(- textInsertName.getWidth()/2, - textInsertName.getHeight());
        textName.translate(- textName.getWidth()/2, textName.getHeight());
        textInsertName.setColor(Color.WHITE);
        textName.setColor(Color.WHITE);


    }


    private void fillQuestions(){

        try {
            FileReader reader = new FileReader("resources/questions.txt");
            BufferedReader bReader = new BufferedReader(reader);

            String line = "";

            for (int i = 0; i < questions.length; i++) {
                String question;
                String[] answers = new String[4];
                line = bReader.readLine();
                question = line;
                for (int j = 0; j < answers.length; j++) {

                    line = bReader.readLine();
                    answers[j] = line;

                }

                questions[i] = new Question(question, answers);
            }

            bReader.close();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    }

    private void drawMenu(){
        recsMenu[0] = new Rectangle(PADDING + background.getWidth()/2 - 150, PADDING + 100, 300, 100);
        recsMenu[1] = new Rectangle(PADDING + background.getWidth()/2 - 150, PADDING + 300, 300, 100);
        //recsMenu[2] = new Rectangle(PADDING + 50, background.getHeight()/2 + 25, background.getWidth()/2 - PADDING - 50 -25, background.getHeight()/2 - 50 -25);
        recsMenu[2] = new Rectangle(PADDING + background.getWidth()/2 - 150, PADDING + 500, 300, 100);

        for (int i = 0; i < recsMenu.length; i++) {
            recsMenu[i].setColor(Color.BLUE);
        }

        textsMenu[0] = new Text(recsMenu[0].getX() + recsMenu[0].getWidth()/2, recsMenu[0].getY() + recsMenu[0].getHeight()/2, "Play");
        textsMenu[1] = new Text(recsMenu[1].getX() + recsMenu[1].getWidth()/2, recsMenu[1].getY() + recsMenu[1].getHeight()/2, "Instructions");
        //textsMenu[2] = new Text(recsMenu[2].getX() + 75, recsMenu[2].getY() + 50, "High-Scores");
        textsMenu[2] = new Text(recsMenu[2].getX() + recsMenu[2].getWidth()/2, recsMenu[2].getY() + recsMenu[2].getHeight()/2, "Quit");

        for (int i = 0; i < textsMenu.length; i++) {
            textsMenu[i].translate(- textsMenu[i].getWidth()/2, - textsMenu[i].getHeight()/2);
            textsMenu[i].setColor(Color.WHITE);
        }

    }

    private void fillInstructions(){
        textInstruct[0] = new Text(PADDING + background.getWidth()/2, PADDING + background.getHeight()/2, "Use the keys 1, 2, 3 and 4 to pre select the respective answer.");
        textInstruct[1] = new Text(PADDING + background.getWidth()/2, PADDING + background.getHeight()/2, "Use the key S to lock your answer and see if it is the correct one");
        textInstruct[2] = new Text(PADDING + background.getWidth()/2, PADDING + background.getHeight()/2,"Use space bar to go to the next question");
        textInstruct[0].translate( - textInstruct[0].getWidth()/2, - textInstruct[0].getHeight() * 3);
        textInstruct[1].translate( - textInstruct[1].getWidth()/2, - textInstruct[1].getHeight()/2);
        textInstruct[2].translate( - textInstruct[2].getWidth()/2,  textInstruct[2].getHeight() * 2);

        recInstruct = new Rectangle(PADDING + background.getWidth()/2, PADDING + background.getHeight()/2, 400, 200);
        recInstruct.translate( - recInstruct.getWidth()/2, - recInstruct.getHeight()/2);
        recInstruct.setColor(Color.BLUE);


        for (int i = 0; i < textInstruct.length; i++) {
            textInstruct[i].setColor(Color.WHITE);
        }

    }

    private void clearScreen(){
        for (int i = 0; i < recsMenu.length; i++) {
            recsMenu[i].delete();
            textsMenu[i].delete();
        }

        for (int i = 0; i < rectangles.length; i++) {
            rectangles[i].delete();
            texts[i].delete();
        }

        for (int i = 0; i < textInstruct.length; i++) {
            textInstruct[i].delete();
        }

        textScore.delete();
        textScore2.delete();

        textInsertName.delete();
        textName.delete();
    }

}
