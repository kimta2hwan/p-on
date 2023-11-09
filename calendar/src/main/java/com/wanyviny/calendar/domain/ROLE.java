package com.wanyviny.calendar.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ROLE {
    GUEST("ROLE_GUEST"), USER("ROLE_USER");

    private final String key;
}
