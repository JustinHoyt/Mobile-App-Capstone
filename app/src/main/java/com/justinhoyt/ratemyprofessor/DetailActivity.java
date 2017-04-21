package com.justinhoyt.ratemyprofessor;

import android.app.Activity;
import android.os.Bundle;


public class DetailActivity extends Activity{
    public static final String PROFESSOR_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ProfessorDetailFragment professorDetailFragment = (ProfessorDetailFragment)
                getFragmentManager().findFragmentById(R.id.detail_frag);
        int professorId = (int) getIntent().getExtras().get(PROFESSOR_ID);
        professorDetailFragment.setProfessor(professorId);
    }
}
