package com.full.cn.utils;

import org.junit.Test;
import org.python.core.*;

import org.python.util.PythonInterpreter;

import java.io.*;
//import org.python.modules.s


public class Java2Python {

    //@Test
    public void java2Json(){
        /*PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("days=('mod','Tue','Wed','Thu','Fri','Sat','Sun'); ");
        interpreter.exec("print days[6];");
        interpreter.execfile("E:/workspalce/python/hello/Snownlp.py");
        //interpreter.exec("print('hello')");
        PyFunction pyFunction = interpreter.get("hello", PyFunction.class); // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型
        PyObject pyObject = pyFunction.__call__(); // 调用函数

        System.out.println(pyObject);*/
        String result = "";

        try {
            Process process = Runtime.getRuntime().exec("python E:/workspalce/python/hello/zjttest.py" );
//            process.waitFor();
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            result = input.readLine();
            input.close();
            ir.close();
//            process.waitFor();
        } catch (IOException e) {
            System.out.println("调用python脚本并读取结果时出错：" + e.getMessage());
        }
        System.out.println(result);




    }

   @Test
    public void testPython(){
       PySystemState sys = Py.getSystemState();
       sys.path.add("F:\\tools\\Anaconda3");

        PythonInterpreter interpreter = new PythonInterpreter();

      // interpreter.exec("from snownlp import SnowNLP");
       //interpreter.exec("print snownlp");
        interpreter.execfile("E:\\workspalce\\python\\hello\\Snownlp.py");

        PyFunction func = (PyFunction) interpreter.get("getresult",
                PyFunction.class);

        //int a = 2010, b = 2;
        //PyObject pyobj = func.__call__(new PyInteger(a), new PyInteger(b));
        PyObject pyobj = func.__call__();
        System.out.println("anwser = " + pyobj.toString());
    }

    //@Test
    public void testPython1(){
        PythonInterpreter interpreter = new PythonInterpreter();
        PySystemState sys = Py.getSystemState();
        // sys.path.add("D:\\ProgramData\\Anaconda3\\Lib");
        interpreter.exec("from __future__ import print_function, unicode_literals");
        interpreter.exec("import json");
        interpreter.exec("import requests");
        interpreter.exec("import string");
        interpreter.execfile("E:\\workspalce\\python\\hello\\test002.py");
        PyFunction func = (PyFunction) interpreter.get("test2java",
                PyFunction.class);


        PyObject pyobj = func.__call__();
        System.out.println("anwser = " + pyobj.toString());
    }

    @Test
    public void testPytnon(){

        PythonInterpreter interp =
                new PythonInterpreter();
        System.out.println("Hello, brave new world");
        interp.exec("import sys");
        interp.exec("print sys");
        interp.set("a", new PyInteger(42));
        interp.exec("print a");
        interp.exec("x = 2+2");
        PyObject x = interp.get("x");
        System.out.println("x: "+x);
        System.out.println("Goodbye, cruel world!");
    }


    public static void main(String []args){
        // String a="C:\\work\\jpy\\zjttest.py";
        String a="E:\\workspalce\\python\\hello\\zjttest.py";
        String arg ="python"+" "+a;

//		String[] arg=new String[] {"python","C:\\work\\jpy\\zjttest.py",a)
        try{
            System.out.println("start");
//            Process pr = Runtime.getRuntime().exec("python C:\\work\\jpy\\abstract_text.py");
            Process pr = Runtime.getRuntime().exec(arg);

            BufferedReader in = new BufferedReader(new
                    InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {

                System.out.println("line="+line);
            }
            in.close();
            pr.waitFor();
            System.out.println("end");
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void testPython004(){
        String keywords = "我今天很快乐。我今天很愤怒。";
        try {
            System.out.println("start");
            Process pr = Runtime.getRuntime().exec("python E:\\workspalce\\python\\hello\\Snownlp.py"+keywords);

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("1231:"+line);
        }
        in.close();
        pr.waitFor();
            System.out.println("end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过Java调用Python脚本的代码
     */
    @Test
    public void testPython005(){
        try {
            //String a=getPara("car").substring(1),b="D34567",c="LJeff34",d="iqngfao";
            //String[] args1=new String[]{ "python", "D:\\pyworkpeace\\9_30_1.py", a, b, c, d };
            //Process pr=Runtime.getRuntime().exec(args1);
            String url="厂商并没有此型号，由4s店改装，价格比智行贵3000块销售。" +
                    "当时销售顾问说这款智能版是在智行版上多了雷达皮座椅，我们觉得贵3000值得，" +
                    "所以果断购买。提车回家研究发现并非如此，而是酷动改装，少了很多智行上的配置。" +
                    "但是由于消费者并不知情，合同已签，所以4S店不认账，当时打电话给销售顾问，他还具不承认此车是改装的，" +
                    "还说我们选的就是这个，白纸黑字写的清清楚楚。此时觉得维权无望，只能四处询问解决方案。";
            System.out.println("start;");
            String[] args1 = new String[] { "python", "E:\\workspalce\\python\\hello\\test004.py", url};
            Process pr=Runtime.getRuntime().exec(args1);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            pr.waitFor();
            System.out.println("end");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


}
