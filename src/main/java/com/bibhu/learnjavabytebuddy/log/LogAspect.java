package com.bibhu.learnjavabytebuddy.log;

import com.bibhu.learnjavabytebuddy.calculator.Calculator;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.implementation.bind.annotation.Origin;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;

public class LogAspect {

    public static void main(String[] args) {

        premain("", ByteBuddyAgent.install());

        Calculator calculator = new Calculator();
        int sum = calculator.sum(10, 20, 30);
        System.out.println("Sum is : " + sum);
    }

    public static void premain(String agentArgs, Instrumentation inst) {
        new AgentBuilder.Default()
                .type(type -> type.getSimpleName().equals("Calculator"))
                .transform((builder, typeDescription, classLoader, javaModule, protectionDomain) -> builder
                        .method(method -> method.getDeclaredAnnotations().isAnnotationPresent(Log.class))
                        .intercept(MethodDelegation.to(LogAspect.class).andThen(SuperMethodCall.INSTANCE)))
                .installOn(inst);
    }

    public static void intercept(@Origin Method method) {
        System.out.println(method.getName() + " method and " + method.getDeclaringClass() + " class is intercepted");
    }
}

//OP
//sum method and class com.bibhu.learnjavabytebuddy.calculator.Calculator class is intercepted
//Sum is : 60