package com.example.bootcamp.util;

import java.util.Base64;

/**
 * Класс для преобразования строк в необходимый формат для регистрации пользователя
 * */
public class Converter {

    private static final String STRING_FORMAT = "%s:%s";
    public static final String NOT_BE_NULL_MESSAGE = "Email and code must not be null";

    private Converter() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Кодирует email и code в Base64 строку формата email:code.
     * @param email Email пользователя
     * @param code Код для регистрации
     * @return закодированная строка
     */
    public static String getEncodedEmailAndCode(String email, String code) {
        return Base64.getEncoder().encodeToString(getFormatedStringForEmailAndCode(email, code).getBytes());
    }

    /**
     * Кодирует email и code в Base64 пользовательской реализации строку формата email:code.
     * @param email Email пользователя
     * @param code Код для регистрации
     * @return закодированная строка
     */
    public static String getEncodedEmailAndCodeWithCustomBASE64(String email, String code) {
        return BASE64.encode(getFormatedStringForEmailAndCode(email, code));
    }

    /**
     * Кодирует email и code в Base64 строку формата email:code.
     * @param email Email пользователя
     * @param code Код для регистрации
     * @return закодированная строка
     */
    private static String getFormatedStringForEmailAndCode(String email, String code) {
        if (email == null || code == null) {
            throw new IllegalArgumentException(NOT_BE_NULL_MESSAGE);
        }

        return String.format(STRING_FORMAT, email, code);
    }
}
