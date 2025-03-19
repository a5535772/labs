package com.leo.labs.springaidemo.demos.statics;

public class KeyWordEncryptionUtil {
    public String doBeforeWordChange(String orgUserText) {
        if (orgUserText != null) {
            return orgUserText.replace("刘德华", "张学友");
        }
        return orgUserText;
    }

    public String doAfterWordChange(String orgUserText) {
        if (orgUserText != null) {
            return orgUserText.replace("张学友", "刘德华");
        }
        return orgUserText;
    }
}
