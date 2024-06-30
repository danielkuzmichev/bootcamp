package com.example.bootcamp.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BASE64 {

    public static final int CHUNK_SIZE = 3;
    private static final String ENCRYPTION_TABLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
    public static final int CODE_OF_SPEC_CHAR = 64;
    public static final int BIT_SHIFT_LENGTH = 6;
    public static final String LEAD_BITS = "00";
    public static final String BYTE_FORMAT = "%8s";

    public static String encode(String inputRaw) {
        byte[] inputBytes = inputRaw.getBytes();
        List<byte[]> groupedBytes = getGroupedBytes(inputBytes, CHUNK_SIZE);
        int remainingBytesCount = CHUNK_SIZE - inputBytes.length % CHUNK_SIZE;
        restructureGroupedBytes(groupedBytes);
        setSpecialChars(groupedBytes, remainingBytesCount);
        return getStringCodes(groupedBytes);
    }

    private static void setSpecialChars(List<byte[]> byteGroups, int remainingBytesCount) {
        if (remainingBytesCount != CHUNK_SIZE) {
            byte[] lastGroup = byteGroups.get(byteGroups.size() - 1);
            int lenLastByteGroup = lastGroup.length;
            for (int i = lenLastByteGroup - 1; i >= lenLastByteGroup - remainingBytesCount; i--) {
                lastGroup[i] = (byte) CODE_OF_SPEC_CHAR;
            }
        }
    }

    private static String getStringCodes(List<byte[]> byteGroups) {
        int[] flatBytes = byteGroups.stream()
                .parallel()
                .flatMapToInt(arr -> IntStream.range(0, arr.length).map(i -> arr[i]))
                .toArray();

        return Arrays.stream(flatBytes)
                .parallel()
                .mapToObj(b -> String.valueOf(ENCRYPTION_TABLE.charAt(b)))
                .collect(Collectors.joining());
    }

    private static List<byte[]> getGroupedBytes(byte[] bytesArray, int chunkSize) {
        int numChunks = (int) Math.ceil((double) bytesArray.length / chunkSize);
        return IntStream.range(0, numChunks)
                .parallel()
            .mapToObj(i -> getBytesChunk(bytesArray, chunkSize, i))
            .collect(Collectors.toList());
    }
    
    private static byte[] getBytesChunk(byte[] bytesArray, int chunkSize, int i) {
        int start = i * chunkSize;
        int end = Math.min(start + chunkSize, bytesArray.length);
        byte[] chunk = new byte[chunkSize];
        System.arraycopy(bytesArray, start, chunk, 0, end - start);
        return chunk;
    }

    private static void restructureGroupedBytes(List<byte[]> byteGroups) {
        int bitLength = BIT_SHIFT_LENGTH;
        List<byte[]> restructuredGroups = byteGroups.stream()
            .map(group -> getNewGroup(group, bitLength))
            .collect(Collectors.toList());
        byteGroups.clear();
        byteGroups.addAll(restructuredGroups);
    }

    private static byte[] getNewGroup(byte[] group, int bitLength) {
        String binaryRaw = getBinaryRaw(group);
        List<byte[]> selectedDigits = getGroupedBytes(binaryRaw.getBytes(), bitLength);
        return getNewBytes(selectedDigits);
    }

    private static byte[] getNewBytes(List<byte[]> selectedDigits) {
        byte[] newGroup = new byte[selectedDigits.size()];
        for (int i = 0; i < newGroup.length; i++) {
            String truncatedRaw = new String(selectedDigits.get(i));
            newGroup[i] = (byte) Integer.parseInt(LEAD_BITS + truncatedRaw, 2);
        }
        return newGroup;
    }

    private static String getBinaryRaw(byte[] bytes) {
        StringBuilder binaryStringBuilder = new StringBuilder();
        for (byte b : bytes) {
            binaryStringBuilder.append(
                String.format(BYTE_FORMAT, Integer.toBinaryString(b))
                    .replace(' ', '0')
            );
        }
        return binaryStringBuilder.toString();
    }
}
