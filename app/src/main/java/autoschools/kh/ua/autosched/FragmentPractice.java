package autoschools.kh.ua.autosched;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextClock;


public class FragmentPractice extends Fragment {
    private ListView list_practice;
    private TextClock textClock;

    String[] myItems;
    String[] descriptions;
    int[] images;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_practice, container, false);

        list_practice = (ListView) view.findViewById(R.id.list_practice);
        //textClock = (TextClock) getView().findViewById(R.id.textClock);
        //textClock.setTimeZone("Europe/Kiev");

        Resources res = getResources();
        myItems = res.getStringArray(R.array.titles);
        descriptions = res.getStringArray(R.array.descriptions_practice);
//        images = new int[]{R.drawable.lecture_icon, R.drawable.car_icon, R.drawable.lecture_icon, R.drawable.car_icon,
//                R.drawable.lecture_icon, R.drawable.car_icon, R.drawable.lecture_icon, R.drawable.car_icon,
//                R.drawable.lecture_icon, R.drawable.car_icon, R.drawable.lecture_icon, R.drawable.car_icon,
//                R.drawable.lecture_icon, R.drawable.car_icon, R.drawable.lecture_icon, R.drawable.car_icon};

        //int[] images = {R.drawable.lecture_icon, R.drawable.car_icon};

        //timer = new Timer();

        list_practice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShowAlert(view.getContext());
            }
        });

        MyListViewAdapter_Practice adapter = new MyListViewAdapter_Practice(getActivity(), myItems, images, descriptions);
        list_practice.setAdapter(adapter);

        return view;
    }

    public void ShowAlert(Context c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Информация о занятии")
                .setMessage("Имя преподавателя: " + "\t" + "Иванов Пётр Иванович" +
                        "\n" + "Тип занятия: " + "\t" + "Практика")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

