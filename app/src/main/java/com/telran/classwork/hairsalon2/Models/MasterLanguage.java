package com.telran.classwork.hairsalon2.Models;

/**
 * Created by vadim on 15.03.2017.
 */

public class MasterLanguage {
    private String langName;
    private String langCode;

    public MasterLanguage() {
    }

    public MasterLanguage(String langName, String langCode) {
        this.langName = langName;
        this.langCode = langCode;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }
}

