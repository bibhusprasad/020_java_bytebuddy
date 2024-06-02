package com.bibhu.learnjavabytebuddy.log;

import java.util.Arrays;

public class Calculator {

    @Log
    public int sum(int... values) {
        return Arrays.stream(values).sum();
    }

}
