package com.example.gentl.servio;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gentl.servio.Adapters.Place;

// Shows detailed info about a place
public class PlaceFragment extends DialogFragment
{
    private static final String ARG_Place = "Place";

    private Place mPlace;

    public PlaceFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param place Parameter 1.
     * @return A new instance of fragment PlaceFragment.
     */
    public static PlaceFragment newInstance(Place place)
    {
        PlaceFragment fragment = new PlaceFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_Place, place);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mPlace = (Place) getArguments().getSerializable(ARG_Place);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place, container, false);

        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvCode = (TextView) view.findViewById(R.id.tvCode);
        TextView tvLeft = (TextView) view.findViewById(R.id.tvLeft);
        TextView tvTop = (TextView) view.findViewById(R.id.tvTop);
        TextView tvWidth = (TextView) view.findViewById(R.id.tvWidth);
        TextView tvHeight = (TextView) view.findViewById(R.id.tvHeight);
        TextView tvCorner = (TextView) view.findViewById(R.id.tvCorner);
        TextView tvShapeType = (TextView) view.findViewById(R.id.tvShapeType);
        TextView tvShapeOrient = (TextView) view.findViewById(R.id.tvShapeOrient);
        TextView tvColor = (TextView) view.findViewById(R.id.tvColor);
        TextView tvStyle = (TextView) view.findViewById(R.id.tvStyle);
        TextView tvFrameColor = (TextView) view.findViewById(R.id.tvFrameColor);
        TextView tvFontColor = (TextView) view.findViewById(R.id.tvFontColor);

        tvName.setText("Name: " + mPlace.getName());
        tvCode.setText("Code: " + mPlace.getCode());
        tvLeft.setText("Left: " + mPlace.getLeft());
        tvTop.setText("Top: " + mPlace.getTop());
        tvWidth.setText("Width: " + mPlace.getWidth());
        tvHeight.setText("Height: " + mPlace.getHeight());
        tvCorner.setText("Corner: " + mPlace.getCorner());
        tvShapeType.setText("ShapeType: " + mPlace.getShapeType());
        tvShapeOrient.setText("ShapeOrient: " + mPlace.getShapeOrient());
        tvColor.setText("Color: " + mPlace.getColor());
        tvStyle.setText("Style: " + mPlace.getStyle());
        tvFrameColor.setText("FrameColor: " + mPlace.getFrameColor());
        tvFontColor.setText("FontColor: " + mPlace.getFontColor());

        return view;
    }
}
