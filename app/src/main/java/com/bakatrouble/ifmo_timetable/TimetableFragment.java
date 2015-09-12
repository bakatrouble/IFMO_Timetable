package com.bakatrouble.ifmo_timetable;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Calendar;

public class TimetableFragment extends Fragment {
    private SubjectDataAdapter mSubjectDataAdapter;
    View rootView;
    int pos;

    public static TimetableFragment newInstance(int pos) {
        TimetableFragment fragment = new TimetableFragment();
        fragment.pos = pos;
        return fragment;
    }

    public TimetableFragment(){
        mSubjectDataAdapter = new SubjectDataAdapter();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab_position", pos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState != null){
            int tmp = savedInstanceState.getInt("tab_position", -1);
            if(tmp != -1){
                pos = tmp;
            }
        }

        rootView = inflater.inflate(R.layout.fragment_timetable, container, false);

        Calendar c = Calendar.getInstance();
        int day = (pos + c.getFirstDayOfWeek() - 2) % 7;
        ArrayList<Subject> subjects = TimetableActivity.DBHelper.getSubjects(TimetableActivity.week, day, TimetableActivity.gid);

        if(subjects.size() == 0){
            if(TimetableActivity.state == 0){
                rootView.findViewById(R.id.default_not_set).setVisibility(View.VISIBLE);
            }else{
                ((ScrollView)rootView.findViewById(R.id.empty_container)).addView(new EmptyGenerator(inflater).generateEmpty());
                rootView.findViewById(R.id.empty_container).setVisibility(View.VISIBLE);
            }
        }else{
            switch(TimetableActivity.state){
                case 0:
                    rootView.findViewById(R.id.default_not_set).setVisibility(View.VISIBLE);
                    break;
                case 1:
                    rootView.findViewById(R.id.subjects_loading).setVisibility(View.VISIBLE);
                    break;
                case 2:
                    rootView.findViewById(R.id.subjects_recycler).setVisibility(View.VISIBLE);
                    break;
            }
        }

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.subjects_recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        mSubjectDataAdapter.updateDataset(subjects);
        recyclerView.setAdapter(mSubjectDataAdapter);
        return rootView;
    }
}

