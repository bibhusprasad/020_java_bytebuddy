package com.bibhu.learnjavabytebuddy.space;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import org.junit.jupiter.api.Test;

import static net.bytebuddy.matcher.ElementMatchers.named;

class SpaceShipTest {

    @Test
    public void flyAround() throws Exception {

        SpaceShip myShip = new ByteBuddy()
                .subclass(SpaceShip.class)
                .name("com.bibhu.SlowSpaceShip")
                .method(named("flyAround"))
                .intercept(FixedValue.value("Swoooooosh ... ... cough cough"))
                .make()
                .load(SpaceShipTest.class.getClassLoader())
                .getLoaded()
                .getConstructor().newInstance();

        System.out.println("fly : " + myShip.flyAround());
        System.out.println("fly : " + myShip.land());
        System.out.println("class name : " + myShip.getClass().getName());

    }
}