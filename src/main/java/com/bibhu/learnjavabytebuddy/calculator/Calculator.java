package com.bibhu.learnjavabytebuddy.calculator;

import com.bibhu.learnjavabytebuddy.log.Log;

import java.util.Arrays;

public class Calculator {

    @Log
    public int sum(int... values) {
        return Arrays.stream(values).sum();
    }

}
