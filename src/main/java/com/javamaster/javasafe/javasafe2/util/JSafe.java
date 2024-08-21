package com.javamaster.javasafe.javasafe2.util;

import java.io.File;

public class JSafe {

    private JSafe(){

    }

    public static final File USER_DIRECTORY = new File(System.getProperty("user.home")+"/JavaSafeData");
    public static final File USER_MASTER_KEY_HASH = new File(USER_DIRECTORY.getAbsolutePath()+"/master_key.dat");
    public static final File USER_PASSWORD_DATA = new File(USER_DIRECTORY.getAbsolutePath()+"/passwords.dat");


    public static String ENCRYPTED_MASTER_KEY;

}
