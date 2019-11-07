package ru.hawoline.crazykeys.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class CrazyKeys {

    private String resultText;
    private String text;

    private MediaPlayer winMP;
    private MediaPlayer looseMP;
    private MediaPlayer correctMP;
    private MediaPlayer mistakeMP;

    private int mistake;

    public CrazyKeys(String resultText, int mistake) {
        this.resultText = resultText;
        this.mistake = mistake;

        winMP = getMediaPlayer("C:/Users/user/IdeaProjects/Crazy Keys/src/ru/hawoline/crazykeys/pane/res/music/win.mp3");
        looseMP = getMediaPlayer("C:/Users/user/IdeaProjects/Crazy Keys/src/ru/hawoline/crazykeys/pane/res/music/loose.mp3");
        correctMP = getMediaPlayer("C:/Users/user/IdeaProjects/Crazy Keys/src/ru/hawoline/crazykeys/pane/res/music/correct.mp3");
        mistakeMP = getMediaPlayer("C:/Users/user/IdeaProjects/Crazy Keys/src/ru/hawoline/crazykeys/pane/res/music/mistake.mp3");

        text = "";
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MediaPlayer getWinMP() {
        return winMP;
    }

    public void setWinMP(MediaPlayer winMP) {
        this.winMP = winMP;
    }

    public MediaPlayer getLooseMP() {
        return looseMP;
    }

    public void setLooseMP(MediaPlayer looseMP) {
        this.looseMP = looseMP;
    }

    public MediaPlayer getCorrectMP() {
        return correctMP;
    }

    public void setCorrectMP(MediaPlayer correctMP) {
        this.correctMP = correctMP;
    }

    public MediaPlayer getMistakeMP() {
        return mistakeMP;
    }

    public void setMistakeMP(MediaPlayer mistakeMP) {
        this.mistakeMP = mistakeMP;
    }

    public int getMistake() {
        return mistake;
    }

    public void setMistake(int mistake) {
        this.mistake = mistake;
    }

    public void addSymbol(char symbol){
        mistakeMP.stop();
        if (text.length() < resultText.length()) {
            if (mistake != 0){
                if (isTrueSymbol(symbol)) {
                    StringBuilder stringBuilder = new StringBuilder(text);
                    stringBuilder.append(symbol);
                    text = stringBuilder.toString();
                } else {
                    mistake--;
                    mistakeMP.play();
                }
            } else {
                looseMP.play();
            }
        } else {
            correctMP.play();
        }
    }

    public boolean isTrueSymbol(char symbol){
        return resultText.charAt(text.length()) == symbol;
    }

    public void removeSymbol(char symbol){
        if (!isTrueSymbol(symbol))
            resultText.replace(resultText.charAt(resultText.length() - 1), ' ');
    }

    public static MediaPlayer getMediaPlayer(String path){
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        return mediaPlayer;
    }
}
