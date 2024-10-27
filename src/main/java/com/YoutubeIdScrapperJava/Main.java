package com.YoutubeIdScrapperJava;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> arrayList;

        System.out.println("Enter how many videos you want to display: ");
        int x = Integer.parseInt(reader.readLine());

        System.out.println("Enter your search query: ");
        arrayList = Search(reader.readLine(),x);

        System.out.println(Arrays.toString(arrayList.toArray()));
    }
    private static ArrayList<String> Search(String searchQuery, int x) throws IOException {

        String url = "https://www.youtube.com/results?search_query=" + searchQuery;

        final Document document = Jsoup.connect(url).get();
        String[] array;
        ArrayList<String[]> list1= new ArrayList<>();

        for (Element row : document.select("Script")) {
            if (row.html() != null) {
                array = row.html().split("\\W");
                list1.add(array);
            }
        }

        ArrayList<String> list2 = new ArrayList<>();

        for (String[] a : list1) {
            {
                for (String s : a){
                    if (!s.isBlank()) {
                        list2.add(s);
                    }
                }
            }
        }

        LinkedHashSet<String> hash = new LinkedHashSet<>();

        for (int i = 0; i < list2.size(); i++) {
            if (list2.get(i).equals("videoId")) {
                hash.add(list2.get(i + 1));
            }
        }

        String[] string = new String[hash.size()];
        string = hash.toArray(string);
        list2 = new ArrayList<>();

        if(x > string.length){
            System.out.println("Displaying only: " + string.length + " Titles!");
        }
        for (int i = 0; i < x && i < string.length; i++) {
            list2.add("https://www.youtube.com/watch?v=" + string[i]);
        }

        return list2;
    }
}

