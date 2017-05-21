package com.example.andras.myapplication.material;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.andras.myapplication.DragController;
import com.example.andras.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Andras_Nemeth on 2015.09.04..
 */
public class RecyclerViewFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.drag_overlay)
    ImageView dragOverlay ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycle_view, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MaterialTestAdapter());
        recyclerView.addOnItemTouchListener(new DragController(recyclerView, dragOverlay));
        return view ;
    }
}
