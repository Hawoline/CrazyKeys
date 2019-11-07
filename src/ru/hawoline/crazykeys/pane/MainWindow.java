package ru.hawoline.crazykeys.pane;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.hawoline.crazykeys.model.CrazyKeys;
import ru.hawoline.crazykeys.model.Quiz;

public class MainWindow extends Application {

    private BorderPane root;

    private Label helloLabel;
    private KeyboardGame keyboardGame;
    private Label timerLabel;
    private Label mistakeLabel;
    private Label scoreLabel;

    private CrazyKeys crazyKeys;
    private Timeline timeline;

    private int timeSeconds;
    private int score;
    private boolean gameStarted;

    public static Quiz[] quizzes = new Quiz[13];

    Quiz[] gameQuizzes;
    private int currentQuestion = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {

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
                "Не каждый совершить сумеет сам Процесс создания компьютерных программ. Искусство это тщательно планируем, А сам процесс зовется…",
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

        mistakeLabel = new Label("");
        timerLabel = new Label("");
        scoreLabel = new Label("Очки: " + score);

        AnchorPane anchorPane = new AnchorPane();

        AnchorPane.setBottomAnchor(timerLabel, 30.0);
        AnchorPane.setRightAnchor(timerLabel, 30.0);

        AnchorPane.setBottomAnchor(scoreLabel, 30.0);
        AnchorPane.setLeftAnchor(scoreLabel, 30.0);

        anchorPane.getChildren().addAll(timerLabel, scoreLabel);

        BorderPane.setAlignment(mistakeLabel, Pos.TOP_RIGHT);
        BorderPane.setMargin(mistakeLabel, new Insets(30, 30, 0, 0));


        root.setCenter(helloLabel);
        root.setBottom(anchorPane);
        root.setTop(mistakeLabel);

        primaryStage.setTitle("Fort Boyard");
        Scene scene = new Scene(root, 1000, 500);

        scene.setOnKeyPressed(this::handleOnKeyPressed);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE){
            if (gameStarted)
                return;

            root.getChildren().remove(helloLabel);
            keyboardGame = new KeyboardGame();
            gameQuizzes = keyboardGame.getGameQuizzes();
            root.setCenter(keyboardGame);

            crazyKeys = new CrazyKeys(gameQuizzes[currentQuestion].getAnswer(), 5);
            mistakeLabel.setText("Ошибок: " + Integer.toString(crazyKeys.getMistake()));

            startTimer();
            gameStarted = true;
            return;
        }

//        if (crazyKeys != null && event.getText().length() > 0){
//            if (!gameStarted)
//                return;
//
//            crazyKeys.addSymbol(event.getText().charAt(0));
//            mistakeLabel.setText("Ошибок: " + crazyKeys.getMistake());
//            keyboardGame.getKeyBoardTextLabel().setText(crazyKeys.getText());
//            if (keyboardGame.getKeyBoardTextLabel().getText().toLowerCase().equals(crazyKeys.getResultText())){
//                score += 2;
//                if (gameQuizzes.length - 1 == currentQuestion){
//                    String winText = "Поздравляем с успешным прохождением испытания!\n" +
//                            "Ваш набранный балл: " + score;
//                    root.getChildren().remove(keyboardGame);
//                    root.setCenter(new Label(winText));
//                    crazyKeys.getWinMP().play();
//                    timeline.stop();
//                    gameStarted = false;
//                    return;
//                }
//
//                keyboardGame.showKeyboard();
//                crazyKeys.setResultText(gameQuizzes[++currentQuestion].getAnswer());
//                crazyKeys.setText("");
//                keyboardGame.getKeyBoardTextLabel().setText("");
//                keyboardGame.getQuestionLabel().setText(gameQuizzes[currentQuestion].getQuestion());
//                scoreLabel.setText("Очки: " + score);
//            }
//            if (crazyKeys.getMistake() == 0){
//                String looseText = "Количество допустимых ошибок исчерпано\n" +
//                        "Ваш набранный балл: " + score;
//                root.getChildren().remove(keyboardGame);
//                root.setCenter(new Label(looseText));
//                timeline.stop();
//            }
//        }
    }

    private void startTimer(){
        timeSeconds = 420;
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
                            }
                        }));
        timeline.playFromStart();
    }
}
