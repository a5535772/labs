package com.example.demo.javassist;

import com.example.demo.javassist.tools.MethodVariableNameUtil;
import com.example.demo.service.HelloApi;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class TestJavassist {

    public static void main(String[] args) throws Exception {
        HelloApi helloApi = (HelloApi) addPrintArgs("com.example.demo.service.HelloImpl", "hello");
        String result = helloApi.hello("this is the word");
        System.out.println("result are  " + result);

    }

    public static Object addPrintArgs(String className, String methodName) throws Exception {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get(className);
        Class clz = null;
        if (ctClass == null) {
            throw new RuntimeException("class not found");
        }
        try {
            CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);
            String[] methodArgs = MethodVariableNameUtil.getMethodVariableName(ctMethod);
            if (methodArgs != null && methodArgs.length > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("{System.out.println(\"args are : \"");
                sb.append(getBody(methodArgs));
                sb.append("); }");
                // ctMethod.insertBefore("{System.out.println(\"Hello.say():\"); }");
                ctMethod.insertBefore(sb.toString());
            }

            clz = ctClass.toClass();
        } catch (Exception e) {
            throw e;
        } finally {
            ctClass.detach();
        }
        return clz.newInstance();
    }

    private static String getBody(String[] methodArgs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < methodArgs.length; i++) {
            sb.append("+");
            sb.append(methodArgs[i]);
        }
        return sb.toString();
    }

}
