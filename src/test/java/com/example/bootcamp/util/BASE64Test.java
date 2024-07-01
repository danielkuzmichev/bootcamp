package com.example.bootcamp.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Base64;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BASE64Test {

    @ParameterizedTest
    @MethodSource("provideStringsForBase64")
    void testEncodeCustomEncoder(String inputRaw) {
        String customEncoded = BASE64.encode(inputRaw);
        String standardEncoded = Base64.getEncoder().encodeToString(inputRaw.getBytes());
        assertEquals(standardEncoded, customEncoded, "Custom BASE64 encoding should match Java's Base64 encoding for input: " + inputRaw);
    }

    static Stream<String> provideStringsForBase64() {
        return Stream.of(
            "cat",
            "ok",
            "A",
            " ",
            "Long string "
        );
    }
}
