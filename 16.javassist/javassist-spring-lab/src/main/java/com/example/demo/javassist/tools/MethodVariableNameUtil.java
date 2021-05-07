package com.example.demo.javassist.tools;

import java.lang.reflect.Modifier;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

/**
 * Just a demo,do not use it in pro;
 * 
 * @author leo zhang
 *
 */
public abstract class MethodVariableNameUtil {
    /**
     * 获取方法的参数变量名称
     * 
     * @param classname
     * @param methodname
     * @return
     */
    public static String[] getMethodVariableName(CtMethod cm) {
        try {
            MethodInfo methodInfo = cm.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            String[] paramNames = new String[cm.getParameterTypes().length];
            LocalVariableAttribute attr =
                    (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            if (attr != null) {
                int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
                for (int i = 0; i < paramNames.length; i++) {
                    paramNames[i] = attr.variableName(i + pos);
                }
                return paramNames;
            }
        } catch (Exception e) {
            System.out.println("getMethodVariableName fail " + e);
        }
        return null;
    }

    public static String[] getMethodNames(Class clz) {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = null;
        try {
            cc = pool.get(clz.getName());
            String[] methodNames = new String[cc.getMethods().length];
            for (int i = 0; i < methodNames.length; i++) {
                CtMethod ctMethod = cc.getMethods()[i];
                methodNames[i] = ctMethod.getMethodInfo().getName();
            }
            return methodNames;
        } catch (NotFoundException e) {
            e.printStackTrace();
        } finally {
            if (cc != null)
                cc.detach();
        }

        return null;
    }
}
