package com.example.actualtravellerkiviprojectui;

import android.widget.FrameLayout;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actualtravellerkiviprojectui.model.Tour;

import java.util.ArrayList;

/**
 * @author Güneş
 * this fragment was created to show attended tours and upcoming tours which can be accessed by account page
 */
public class TourPagesFragment extends Fragment {
    private ArrayList<Tour> toursList = new ArrayList<Tour>();
    private SearchView tourSearchBar;
    private FrameLayout fragmentForToursPage;
    private RecyclerView recyclerView;
}
