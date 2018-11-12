package com.example.sammrabatool.solutions5d.data;

import android.content.Context;
import android.content.res.TypedArray;

import com.example.sammrabatool.solutions5d.R;
import com.example.sammrabatool.solutions5d.model.Notification;
import com.example.sammrabatool.solutions5d.utils.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SuppressWarnings("ResourceType")
public class DataGenerator {

    private static Random r = new Random();

    public static int randInt(int max) {
        int min = 0;
        return r.nextInt((max - min) + 1) + min;
    }




    /**
     * Generate dummy data inbox
     *
     * @param ctx android context
     * @return list of object
     */




    private static int getRandomIndex(int max) {
        return r.nextInt(max - 1);
    }
}
