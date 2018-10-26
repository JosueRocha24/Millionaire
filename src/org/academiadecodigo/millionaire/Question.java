package org.academiadecodigo.millionaire;

public class Question {

    private String question;
    private String[] answers;
    private int rightAnswer;

    public Question(String question, String[] tempAnswers){
        this.question = question;
        answers = new String[4];
        boolean[] pickedAns = new boolean[4];
        for (int i = 0; i < tempAnswers.length; i++) {
            int index = (int) Math.floor(Math.random()*4);
            while(pickedAns[index]){
                index = (int) Math.floor(Math.random()*4);
            }
            answers[i] = tempAnswers[index];
            pickedAns[index] = true;
            if(index == 0){
                rightAnswer = i + 1;
            }

        }

    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }
}
