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


public class TasksFragment extends Fragment {
    private long listId;
    private TextView tvProfessorName;
    private ImageView ivProfessorImage;
    public SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            listId = savedInstanceState.getLong("listId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tasks, container, false);
        List list = List.LISTs[(int) listId];

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.professorLayout);
        String[] degrees = list.getDegrees().split("; ");
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
                preferences.edit().putFloat("professor_" + listId + "_rating", rating).commit();
            }
        });

        float ratings = preferences.getFloat("professor_" + listId + "_rating", 0f);
        rbProfessorRating.setRating(ratings);

        if (view != null) {
            List list = List.LISTs[(int) listId];


            tvProfessorName.setText(list.getName());
            ivProfessorImage.setImageResource(list.getResourceId());
        }
    }


    public void setProfessor(long professorId) {
        this.listId = professorId;
    }
}
