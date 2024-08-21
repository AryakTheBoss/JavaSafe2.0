package com.javamaster.javasafe.javasafe2.util;

import java.io.File;

public class JSafe {

    private JSafe(){

    }

    public static File USER_DIRECTORY = new File(System.getProperty("user.home")+"/JavaSafeData");
    public static File USER_MASTER_KEY_HASH = new File(USER_DIRECTORY.getAbsolutePath()+"/master_key.dat");
    public static File USER_PASSWORD_DATA = new File(USER_DIRECTORY.getAbsolutePath()+"/passwords.dat");

}
