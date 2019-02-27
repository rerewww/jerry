package com.jerry.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by son on 2019-02-26.
 */
@NoArgsConstructor
public class LogModel {
    private CommonCode commonCode;
    private String message;

    public LogModel(final CommonCode commonCode) {
        this.commonCode = commonCode;
        this.message= commonCode.getMessage();
    }

    @Getter
    private List<String> exceptions = new ArrayList<>();
    @Getter
    private List<String> stackTraces = new ArrayList<>();

    public void setExceptions(final String exception) {
        exceptions.add(exception);
    }

    public void setStackTraces(final StringBuilder builder) {
        stackTraces.add(builder.toString());
    }
}
