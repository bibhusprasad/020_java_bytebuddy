package com.bibhu.learnjavabytebuddy.space;

public interface Shooter {

    default String lazerShoot() {
        return "shooter lazer shoot";
    }
}
