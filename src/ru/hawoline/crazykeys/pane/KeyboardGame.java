package ru.hawoline.crazykeys.pane;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
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

    private int time;

    public KeyboardGame() {
        keyBoardTextLabel = new Label("");

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

        setAlignment(Pos.CENTER);

        getChildren().addAll(questionLabel, keyBoardTextLabel);
        showKeyboard();
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
        getChildren().add(keyBoardIV);

        time = 10;

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        (EventHandler<ActionEvent>) event1 -> {
                            time--;

                            if (time <= 0) {
                                getChildren().remove(keyBoardIV);
                                timeline.stop();
                            }
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
            initialArray = removeItemFromQuizz(initialArray, selectedIndex);
        }

        return newQuestions;
    }

    private Quiz[] removeItemFromQuizz(Quiz[] array, int index){
        Quiz[] arrayWithRemovedItem = new Quiz[array.length - 1];
        array[index] = array[array.length - 1];
        arrayWithRemovedItem = Arrays.copyOf(array, arrayWithRemovedItem.length);

        return arrayWithRemovedItem;
    }
}
