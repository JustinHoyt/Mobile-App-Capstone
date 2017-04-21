package com.goldteam.todolist;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;


public class ProfessorDetailFragment extends Fragment {
    private long professorId;
    private TextView tvProfessorName;
    private ImageView ivProfessorImage;
    public SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            professorId = savedInstanceState.getLong("professorId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_professor_detail, container, false);
        Professor professor = Professor.professors[(int) professorId];

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.professorLayout);
        String[] degrees = professor.getDegrees().split("; ");
        for( String degree : degrees){
            TextView textView = new TextView(getActivity());
            textView.setPadding(8, 16, 8, 16);
            textView.setTextSize(20f);
            textView.setText(degree);
            linearLayout.addView(textView);
        }
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        View view = getView();

        preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        tvProfessorName = (TextView) view.findViewById(R.id.tvProfessorName);
        ivProfessorImage = (ImageView) view.findViewById(R.id.ivProfessorImage);

        RatingBar rbProfessorRating = (RatingBar) view.findViewById(R.id.rbProfessorRating);
        rbProfessorRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                preferences.edit().putFloat("professor_" + professorId + "_rating", rating).commit();
            }
        });

        float ratings = preferences.getFloat("professor_" + professorId + "_rating", 0f);
        rbProfessorRating.setRating(ratings);

        if (view != null) {
            Professor professor = Professor.professors[(int) professorId];


            tvProfessorName.setText(professor.getName());
            ivProfessorImage.setImageResource(professor.getResourceId());
        }
    }


    public void setProfessor(long professorId) {
        this.professorId = professorId;
    }
}
