package com.pluviostudios.selfimage.mainActivity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pluviostudios.selfimage.R;
import com.pluviostudios.selfimage.Utilities;
import com.pluviostudios.selfimage.data.DatabaseContract;

import java.util.Calendar;
import java.util.MissingFormatArgumentException;

public class DayCardFragment extends Fragment {

    private static final String REFERENCE_ID = "DayCardFragment";

    static private View.OnClickListener mOnFABClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_day_card, container, false);
        TextView textView = (TextView) root.findViewById(R.id.fragment_day_card_textview);
        ImageView imageView = (ImageView) root.findViewById(R.id.fragment_day_card_imageview);

        long todayDate = Utilities.normalizeDate(Calendar.getInstance().getTimeInMillis());

        if (!getArguments().containsKey(DatabaseContract.DateEntry.DATE_COL))
            throw new MissingFormatArgumentException("Did not receive DATE_COL and IMAGE_DIRECTORY_COL in arg bundle");

        Uri imageUri = null;
        if (getArguments().containsKey(DatabaseContract.DateEntry.IMAGE_DIRECTORY_COL) &&
                getArguments().getString(DatabaseContract.DateEntry.IMAGE_DIRECTORY_COL) != null) {
            Log.d("TEST!", getArguments().getString(DatabaseContract.DateEntry.IMAGE_DIRECTORY_COL));
            imageUri = Uri.parse(getArguments().getString(DatabaseContract.DateEntry.IMAGE_DIRECTORY_COL));
        }

        Long date = Long.parseLong(getArguments().getString(DatabaseContract.DateEntry.DATE_COL));


        String dateString;
        if (date == todayDate) {
            Log.v(REFERENCE_ID, "TodayCreate!");
            FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fragment_day_card_FAB);
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(mOnFABClickListener);
            fab.setImageResource((imageUri == null) ?
                    R.drawable.ic_add_a_photo_white_24dp :
                    R.drawable.ic_photo_camera_white_24dp);
            dateString = getString(R.string.date_today);
        } else if ((todayDate - 86400000) == date) {
            dateString = getString(R.string.date_yesterday);
        } else {
            dateString = Utilities.formatDateFromMillis(date);
        }

        textView.setText(dateString);

        if (imageUri != null) {
            imageView.setImageURI(null);
            imageView.setImageURI(imageUri);
        }

        return root;
    }

    public static void setOnFABClickListener(View.OnClickListener onFABClickListener) {
        mOnFABClickListener = onFABClickListener;
    }

}