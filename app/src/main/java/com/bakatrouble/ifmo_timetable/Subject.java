package com.bakatrouble.ifmo_timetable;

import java.util.ArrayList;

public class Subject {
    String title;
    String teacher;
    String time_begin;
    String time_end;
    String place;
    long id;
    int day;
    int week;
    ArrayList<Subject> addons = new ArrayList<>();

    Subject(){}

    Subject(String title, String teacher, String time_begin, String time_end, String place){
        this.title = title;
        this.teacher = teacher;
        this.time_begin = time_begin;
        this.time_end = time_end;
        this.place = place;
    }
    Subject(String title, String teacher, String time_begin, String time_end, String place, long id, int day, int week){
        this.title = title;
        this.teacher = teacher;
        this.time_begin = time_begin;
        this.time_end = time_end;
        this.place = place;
        this.id = id;
        this.day = day;
        this.week = week;
    }
}
