package com.example.bootcamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public enum CandidateStatus {

    INCREASED("increased");

    private String title;
}
