package com.bibhu.learnjavabytebuddy.space;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
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
                .implement(Shooter.class)
                .method(named("lazerShoot"))
                .intercept(MethodDelegation.to(SpaceShooter.class))
                .make()
                .load(SpaceShipTest.class.getClassLoader())
                .getLoaded()
                .getConstructor().newInstance();

        System.out.println("fly : " + myShip.flyAround());
        System.out.println("shoot : " + ((Shooter)myShip).lazerShoot());
        System.out.println("fly : " + myShip.land());
        System.out.println("class name : " + myShip.getClass().getName());

    }
}


/*

//op
fly : Swoooooosh ... ... cough cough
shoot : space shooter shoot
fly : Splash
class name : com.bibhu.SlowSpaceShip

*/

/*

comment this 2 line and run

//.method(named("lazerShoot"))
//.intercept(MethodDelegation.to(SpaceShooter.class))


//op
fly : Swoooooosh ... ... cough cough
shoot : shooter lazer shoot
fly : Splash
class name : com.bibhu.SlowSpaceShip

 */
