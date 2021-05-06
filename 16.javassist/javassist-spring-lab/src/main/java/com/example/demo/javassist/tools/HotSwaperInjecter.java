package com.example.demo.javassist.tools;

import java.io.IOException;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.util.HotSwapper;


public class HotSwaperInjecter {
    private HotSwapper swap;


    public static synchronized HotSwaperInjecter getInstance() {
        if (instance == null) {
            instance = new HotSwaperInjecter();
        }
        return instance;
    }

    private static HotSwaperInjecter instance;

    private HotSwaperInjecter() {
        try {
            swap = new HotSwapper(8000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalConnectorArgumentsException e) {
            e.printStackTrace();
        }
    }

    public void injectPirnt(Object bean, String methodName) {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass;
        try {
            ctClass = pool.get(bean.getClass().getName());
            CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);
            ctMethod.insertAt(1, "{System.out.println(\"hello HotSwapper.." + methodName + "\");}");
            swap.reload(ctClass.getName(), ctClass.toBytecode());
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void injectPrint(Object bean, String[] methodNames) {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass;
        try {
            ctClass = pool.get(bean.getClass().getName());
            for (int i = 0; i < methodNames.length; i++) {
                if (shouldJnject(methodNames[i])) {
                    System.out.println("injecting +" + methodNames[i]);
                    CtMethod cm = ctClass.getDeclaredMethod(methodNames[i]);
                    cm.insertAt(1, "{System.out.println(\"hello HotSwapper.." + methodNames[i] + "\");}");
                }
            }
            swap.reload(ctClass.getName(), ctClass.toBytecode());
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean shouldJnject(String name) {
        if ("hello".equals(name)) {
            return true;
        }
        return false;
    }
}
