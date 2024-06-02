package com.bibhu.learnjavabytebuddy.log;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

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

    @RuntimeType
    public static Object intercept(@Origin Method method, @SuperCall Callable<?> superCall) throws Exception {
        method.getAnnotation(Log.class);
        System.out.println(method.getName() + " method and " + method.getDeclaringClass() + " class is intercepted");
        return superCall.call();
    }
}

//OP
//sum method and class com.bibhu.learnjavabytebuddy.log.Calculator class is intercepted
//Sum is : 60