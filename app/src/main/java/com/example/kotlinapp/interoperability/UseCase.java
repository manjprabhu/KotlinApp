package com.example.kotlinapp.interoperability;

public class UseCase {

    private void demo() {
        //Without @JvmStatic
        //AppUtils.INSTANCE.getAppUtils();

        //With @JvmStatic
        AppUtils.getAppUtils();
    }

    private void sessionDemo() {
        //Without Jvmoverloads annotation
        Session s1 = new Session("one", "two", "three");

        //With Jvmoverloads annotation
        Session s2 = new Session("One", "second param");
    }

    private void sessionDemoThree() {

        Session s1 = new Session("One", "two");
        //Without @jvmField annotation
      /*  s1.getParamOne();
        s1.getParamTwo();
        s1.getParamThree();*/

        //With @jvmField annotation
        String paramOne = s1.paramOne;
        String paramTwo = s1.paramTwo;
        String paramThree = s1.paramThree;
    }
}
