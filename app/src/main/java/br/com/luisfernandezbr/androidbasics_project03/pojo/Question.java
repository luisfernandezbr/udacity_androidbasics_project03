package br.com.luisfernandezbr.androidbasics_project03.pojo;

import java.io.Serializable;

/**
 * Created by luis.fernandez on 2/12/17.
 */
public abstract class Question implements Serializable {

    private String value;
    private String type;

    public Question(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public abstract boolean isAnswered();
}
