package ru.hawoline.crazykeys.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;
import ru.hawoline.crazykeys.model.Quiz;

import java.util.Arrays;
import java.util.Random;

public class KeyboardGame extends VBox {

    private Label questionLabel;
    private Label keyBoardTextLabel;
    private ImageView keyBoardIV;

    private Timeline timeline;

    private Quiz[] gameQuizzes;

    public KeyboardGame() {
        keyBoardTextLabel = new Label("");
        keyBoardTextLabel.setFont(new Font(30));

        Image image = new Image(getClass().getResourceAsStream("res/img/keyboard.png"));
        keyBoardIV = new ImageView(image);

        timeline = new Timeline();

        int questionsCount = 5;

        String[] questions = new String[questionsCount];

        gameQuizzes = generateQuiz(MainWindow.quizzes, questionsCount);

        for (int i = 0; i < questions.length; i++){
            questions[i] = MainWindow.quizzes[i].getQuestion();
        }

        questionLabel = new Label(gameQuizzes[0].getQuestion());
        questionLabel.setFont(new Font(20));

        setAlignment(Pos.CENTER);

        getChildren().addAll(questionLabel, keyBoardTextLabel, keyBoardIV);
        showKeyboard();

        System.out.println(System.getProperty("user.dir"));
    }

    public Label getQuestionLabel() {
        return questionLabel;
    }

    public void setQuestionLabel(Label questionLabel) {
        this.questionLabel = questionLabel;
    }

    public Label getKeyBoardTextLabel() {
        return keyBoardTextLabel;
    }

    public ImageView getKeyBoardIV() {
        return keyBoardIV;
    }

    public Quiz[] getGameQuizzes() {
        return gameQuizzes;
    }

    public void setGameQuizzes(Quiz[] gameQuizzes) {
        this.gameQuizzes = gameQuizzes;
    }

    private void setImageView(ImageView imageView, int width, int height){
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    public void showKeyboard(){
        keyBoardIV.setVisible(true);

        timeline.setCycleCount(1);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(10),
                        (EventHandler<ActionEvent>) event1 -> {
                            keyBoardIV.setVisible(false);
                        }));
        timeline.playFromStart();
    }

    private Quiz[] generateQuiz(Quiz[] initialArray, int countOfNewQuestions){
        if (countOfNewQuestions > initialArray.length){
            throw new IllegalArgumentException("Count of new array over than initial array length.");
        }

        Quiz[] newQuestions = new Quiz[countOfNewQuestions];
        Random random = new Random();

        for (int i = 0; i < countOfNewQuestions; i++){
            int selectedIndex = random.nextInt(initialArray.length);
            newQuestions[i] = initialArray[selectedIndex];
            initialArray = removeItemFromQuiz(initialArray, selectedIndex);
        }

        return newQuestions;
    }

    private Quiz[] removeItemFromQuiz(Quiz[] array, int index){
        Quiz[] arrayWithRemovedItem = new Quiz[array.length - 1];
        array[index] = array[array.length - 1];
        arrayWithRemovedItem = Arrays.copyOf(array, arrayWithRemovedItem.length);

        return arrayWithRemovedItem;
    }
}
