package ru.hawoline.crazykeys.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.hawoline.crazykeys.model.CrazyKeys;
import ru.hawoline.crazykeys.model.Quiz;
import ru.hawoline.crazykeys.util.Utils;

import java.io.IOException;

public class MainWindow extends Application {

    private BorderPane root;

    private Label helloLabel;
    private KeyboardGame keyboardGame;
    private Label timerLabel;
    private Label mistakeLabel;
    private Label scoreLabel;
    private AnchorPane anchorPane;

    private CrazyKeys crazyKeys;
    private Timeline timeline;

    private int timeSeconds;
    private int score;
    private boolean gameStarted;

    public static Quiz[] quizzes = new Quiz[13];

    private Quiz[] gameQuizzes;
    private int currentQuestion = 0;

    @Override
    public void start(Stage primaryStage) {

        String[] questions = {
                "Висит груша нельзя скушать",

                "Что за друг такой железный,\n" +
                        "Интересный и полезный?\n" +
                        "Дома скучно, нет уюта,\n" +
                        "Если выключен...",

                "Чтоб в морозы печь топить, Мы должны его купить. И положим горкой в угол Мы блестящий черный…",
                "На когтях на столб влезает,\n" +
                        "Провода он обрезает.\n" +
                        "Кто же это?",

                "У компьютера рука\n" +
                        "На веревочке пока.\n" +
                        "Как приветливый мальчишка,\n" +
                        "Кто вам тянет руку?..",

                "Кричит горлан Через море-океан",

                "Стоит Антошка на одной ножке.",

                "В каком числе цифр столько, сколько букв в его названии?",

                "Ночь –\n" +
                        "Но если захочу,\n" +
                        "Щелкну раз –\n" +
                        "И день включу.",

                "С лопатою в руках\n" +
                        "Кидает в топку камушки,\n" +
                        "Весь в копоти, пропах\n" +
                        "Нательник, сшитый бабушкой,\n" +
                        "Кто же это?",
                "Программы стоит обновить Компьютер долго будет жить, А чтобы жизнь его не сбилась, Не подпускай к порогу…",
                "Не каждый совершить сумеет сам Процесс создания компьютерных программ. \n" +
                        "Искусство это тщательно планируем, А сам процесс зовется…",
                "Свет даёт он и тепло,\n" +
                        "С ним уютно и светло,\n" +
                        "Он по проводам бежит,\n" +
                        "Без него не можем жить!\n" +
                        "Нам приборы он включает,\n" +
                        "В темноте всё освещает,\n" +
                        "Что же в дом несёт нам свет?\n" +
                        "Знает кто, скажи ответ)"
        };

        String[] answers = {
                "лампочка",
                "компьютер",
                "уголь",
                "электрик",
                "мышка",
                "радио",
                "монитор",
                "сто",
                "выключатель",
                "кочегар",
                "вирус",
                "программированием",
                "ток"
        };

        for (int i = 0; i < quizzes.length; i++) {
            quizzes[i] = new Quiz(questions[i], answers[i]);
        }

        root = new BorderPane();

        score = 0;
        gameStarted = false;

        String hello = "Приветствуем участников Форт Боярда на локации Электротехнического факультета!\n" +
                "Время испытания 7 минут. Желаем удачи!\n" +
                "Для старта нажмите пробел.\n" +
                "Разработчик: Нелтанов Биликто, ЭТФ, ПИ, Б669.";
        helloLabel = new Label(hello);
        helloLabel.setTextAlignment(TextAlignment.CENTER);
        helloLabel.setFont(new Font(25));

        mistakeLabel = new Label("");
        timerLabel = new Label("");
        scoreLabel = new Label("Очки: " + score);

        mistakeLabel.setFont(new Font(25));
        timerLabel.setFont(new Font(25));
        scoreLabel.setFont(new Font(25));

        anchorPane = new AnchorPane();

        AnchorPane.setBottomAnchor(timerLabel, 30.0);
        AnchorPane.setRightAnchor(timerLabel, 30.0);

        AnchorPane.setBottomAnchor(scoreLabel, 30.0);
        AnchorPane.setLeftAnchor(scoreLabel, 30.0);

        anchorPane.getChildren().addAll(timerLabel, scoreLabel);

        BorderPane.setAlignment(mistakeLabel, Pos.TOP_RIGHT);
        BorderPane.setMargin(mistakeLabel, new Insets(30, 30, 0, 0));

        root.setCenter(helloLabel);
        root.setTop(mistakeLabel);

        primaryStage.setTitle("Бешеные клавиши");
        Scene scene = new Scene(root, 1024, 512);

        scene.setOnKeyPressed(this::handleOnKeyPressed);

        keyboardGame = new KeyboardGame();

        gameQuizzes = keyboardGame.getGameQuizzes();
        crazyKeys = new CrazyKeys(gameQuizzes[currentQuestion].getAnswer(), 20);

        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE){
            if (gameStarted)
                return;

            root.setBottom(anchorPane);
            root.getChildren().remove(helloLabel);

            root.setCenter(keyboardGame);

            mistakeLabel.setText("Ошибок: " + crazyKeys.getMistake());

            startTimer();

            gameStarted = true;

            keyboardGame.showKeyboard();

            return;
        }

        if (crazyKeys != null && event.getText().length() > 0){
            if (!gameStarted) {
                return;
            }

            crazyKeys.addSymbol(event.getText().charAt(0));
            mistakeLabel.setText("Ошибок: " + crazyKeys.getMistake());
            keyboardGame.getKeyBoardTextLabel().setText(crazyKeys.getText());
            if (keyboardGame.getKeyBoardTextLabel().getText().toLowerCase().equals(crazyKeys.getResultText())){
                score += 2;
                if (gameQuizzes.length - 1 == currentQuestion){
                    showWinTextLabel();
                    gameStarted = false;
                    score = 0;
                    return;
                }

                keyboardGame.showKeyboard();
                crazyKeys.setResultText(gameQuizzes[++currentQuestion].getAnswer());
                crazyKeys.setText("");
                keyboardGame.getKeyBoardTextLabel().setText("");
                keyboardGame.getQuestionLabel().setText(gameQuizzes[currentQuestion].getQuestion());
                scoreLabel.setText("Очки: " + score);
            }

            if (crazyKeys.getMistake() == 0){
                showLooseTextLabel();
            }
        }
    }

    private void startTimer(){
        timeSeconds = 10 * 60;
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        (EventHandler<ActionEvent>) event -> {
                            timeSeconds--;

                            int minute = timeSeconds / 60;
                            int second = timeSeconds % 60;

                            timerLabel.setText(minute + ":" + second);

                            if (timeSeconds <= 0) {
                                timeline.stop();
                                showLooseTextLabel();
                            }
                        }));
        timeline.play();
    }

    private void showLooseTextLabel(){
        String looseText = "Количество допустимых ошибок исчерпано\n" +
                "Ваш набранный балл: " + score;
        scoreLabel.setText("Очки: " + score);

        Label looseTextLabel = new Label(looseText);
        looseTextLabel.setFont(new Font(25));
        root.getChildren().remove(keyboardGame);
        root.setCenter(looseTextLabel);
        timeline.stop();
    }

    private void showWinTextLabel(){
        String winText = "Поздравляем с успешным прохождением испытания!\n" +
                "Ваш набранный балл: " + score;

        scoreLabel.setText("Очки: " + score);

        Label winTextLabel = new Label(winText);
        winTextLabel.setFont(new Font(25));
        root.getChildren().remove(keyboardGame);
        root.setCenter(winTextLabel);
        timeline.stop();
    }
}
