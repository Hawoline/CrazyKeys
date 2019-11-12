package ru.hawoline.crazykeys.model;

import javafx.scene.media.MediaPlayer;

public class CrazyKeys {

    private String resultText;
    private String text;

    private int mistake;

    public CrazyKeys(String resultText, int mistake) {
        this.resultText = resultText;
        this.mistake = mistake;

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

    public int getMistake() {
        return mistake;
    }

    public void setMistake(int mistake) {
        this.mistake = mistake;
    }

    public void addSymbol(char symbol){
        if (text.length() < resultText.length()) {
            if (mistake != 0){
                if (isTrueSymbol(symbol)) {
                    StringBuilder stringBuilder = new StringBuilder(text);
                    stringBuilder.append(symbol);
                    text = stringBuilder.toString();
                } else {
                    mistake--;
                }
            }
        }
    }

    public boolean isTrueSymbol(char symbol){
        return resultText.charAt(text.length()) == symbol;
    }

    public void removeSymbol(char symbol){
        if (!isTrueSymbol(symbol))
            resultText.replace(resultText.charAt(resultText.length() - 1), ' ');
    }
}
