package org.learnbyexample.camel.routes;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class WordBean {

    private String[] words = new String[]{
        "Camel",
        "Rocks",
        "Whiskey",
        "Beer",
        "Bad",
        "Donkey",
        "Cool",
        "Dude",
        "Hawt",
        "Fabric8"
    };

    private int counter;

    public String generateWord() {
        int ran = new Random().nextInt(words.length);
        return "#" + ++counter + "-" + words[ran];
    }
}
