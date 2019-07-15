package com.example.chriszou.quicksample.test;

import java.lang.reflect.Field;
import java.util.Objects;

public class Testdfdfd {

    public static <T> T reflect(String cls, Objects instance, String reflectName) {
        try {
            return (T) reflect(Class.forName("android.support.v7.widget.Toolbar"), instance, reflectName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T reflect(Class<?> cls, Objects instance, String reflectName) {
        try {
            Field field = cls.getDeclaredField(reflectName);
            field.setAccessible(true);
            return (T) field.get(instance);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
