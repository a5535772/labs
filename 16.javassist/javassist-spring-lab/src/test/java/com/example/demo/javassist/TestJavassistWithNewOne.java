package com.example.demo.javassist;

import com.example.demo.service.HelloApi;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class TestJavassistWithNewOne {

    public static void main(String[] args) throws Exception {
        HelloApi helloApi = (HelloApi) crack("com.example.demo.service.HelloImpl",
                "com.example.demo.javassist.crack.HelloImplCrack", "hello");
        String result = helloApi.hello("this is the word");
        System.out.println("result are  " + result);

    }

    public static Object crack(String className, String classNameOfCrack, String methodName) throws Exception {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get(className);
        CtClass ctCrackClass = classPool.get(classNameOfCrack);
        Class clz = null;
        if (ctClass == null || ctCrackClass == null) {
            throw new RuntimeException("class not found");
        }
        try {
            CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);
            // ctClass.removeMethod(ctMethod);

            CtMethod ctCrackMethod = ctCrackClass.getDeclaredMethod(methodName);
            ctMethod.setBody(ctCrackMethod, null);
            // ctClass.addMethod(ctCrackMethod);
            clz = ctClass.toClass();
        } catch (Exception e) {
            throw e;
        } finally {
            ctClass.detach();
        }
        return clz.newInstance();
    }


}
