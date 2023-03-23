package com.example.coursecataloghi;

import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Entities.Course;


public class ExpandableListDataItems {

    public static HashMap<String, List<String>> getData(InputStream inputStream) throws IOException {
        Course course;
        HashMap<String, List<String>> expandableDetailList = new HashMap<String, List<String>>();
        //Lesa gögnin úr csv skránni og bæta í Hashmap
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        try {
            while((line = reader.readLine()) != null) {
                List<String> courseDetails = new ArrayList<String>();
                String[] row = line.split(";");
                for (int i = 2; i<row.length; i++) {
                    if (!row[i].equals("BLANK")) {
                        courseDetails.add(row[i]);
                    }
                }
                expandableDetailList.put((row[0] + ": " +row[1]), courseDetails);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            inputStream.close();
        }
        return expandableDetailList;
    }
}
