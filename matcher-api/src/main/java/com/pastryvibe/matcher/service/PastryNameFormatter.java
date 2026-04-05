package com.pastryvibe.matcher.service;

import java.util.Arrays;
import java.util.stream.Collectors;

final class PastryNameFormatter {

    String humanize(String pastryId) {
        return Arrays.stream(pastryId.split("_"))
                .map(fragment -> Character.toUpperCase(fragment.charAt(0)) + fragment.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
