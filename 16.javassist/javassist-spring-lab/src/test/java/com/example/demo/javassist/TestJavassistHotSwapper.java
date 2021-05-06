package com.example.demo.javassist;

import com.example.demo.service.HelloImpl;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.util.HotSwapper;

/**
 * how to use<br/>
 * 1.需要把tools.jar加到classpath<br/>
 * 2.增加JVM选项 -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000<br/>
 * 
 * @author leo zhang
 *
 */
public class TestJavassistHotSwapper {

    public static void main(String[] args) throws Exception {
        HelloImpl helloImpl = new HelloImpl();
        System.out.println("result are  " + helloImpl.hello("this is the word"));
        hotSwapperChange();
        System.out.println("result are  " + helloImpl.hello("this is the word"));


    }

    public static void hotSwapperChange() throws Exception {

        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("com.example.demo.service.HelloImpl");

        CtMethod ctMethod = ctClass.getDeclaredMethod("hello");
        ctMethod.insertAt(1, "{System.out.println(\"hello HotSwapper.\");}");

        // Furthermore, JAVA_HOME/lib/tools.jar must be included in the class path.
        HotSwapper swap = new HotSwapper(8000);
        swap.reload(ctClass.getName(), ctClass.toBytecode());
    }

}
