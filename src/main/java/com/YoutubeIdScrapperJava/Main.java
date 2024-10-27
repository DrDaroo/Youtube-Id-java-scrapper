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
        ArrayList<String> list= new ArrayList<>();
        LinkedHashSet<String> hash = new LinkedHashSet<>();

        int y = 0, d = 0;

        for (Element row : document.select("Script")) {
            if (hash.size() == x){
                break;
            }

            if (row.html() != null) {
                array = row.html().split("[^\\w-]+");

                for (String s : array){
                    if (hash.size() == x){
                        break;
                    }

                    if (!s.isBlank()) {

                        if(y == 5 && d == 1){
                            hash.add(s);
                            d=0;
                        }
                        if(s.equals("WEB_PAGE_TYPE_WATCH")){
                            y=0;
                            d=1;
                        }
                        y++;

                    }
                }
            }
        }

        String[] string = new String[hash.size()];
        string = hash.toArray(string);

        if(x > string.length){
            System.out.println("Displaying only: " + string.length + " Titles!");
        }
        for (int i = 0; i < x && i < string.length; i++) {
            list.add("https://www.youtube.com/watch?v=" + string[i]);
        }

        return list;
    }
}

