package com.example.zadanie1;

public class Question {
    private int questionId;
    private boolean trueAnwser;

    public Question(int questionId, boolean trueAnwser) {
        this.questionId = questionId;
        this.trueAnwser = trueAnwser;
    }

    public boolean isTrueAnwser() {
        return this.trueAnwser;
    }

    public int getQuestionId() {
        return this.questionId;
    }
}
