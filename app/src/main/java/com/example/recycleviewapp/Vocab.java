package com.example.recycleviewapp;

public class Vocab {

    public void setWord(String word) {
        this.word = word;
    }

    private String word;

    public void setDef(String def) {
        this.def = def;
    }

    private String def;

    public Vocab(String iWord, String iDef){
        word = iWord;
        def = iDef;
    }

    public String getWord() {
        return word;
    }

    public String getDef() {
        return def;
    }


}
