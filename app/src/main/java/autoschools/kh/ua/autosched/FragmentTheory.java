package autoschools.kh.ua.autosched;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FragmentTheory extends Fragment {

    String[] myItems;
    String[] descriptions;
    int[] images;


    ArrayList<TheoryLesson> arr;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theory, container, false);

        ListView list_theory = (ListView) view.findViewById(R.id.list_theory);

        arr = ScheduleUtils.GetTheoryArray(ReadScheduleTheoryFromFile());

        try {
            myItems = ScheduleUtils.getTheoryTitles(arr);
            descriptions = ScheduleUtils.getShortTheoryDescriptions(arr);
        } catch (Throwable e) {


            //TODO debug
            StackTraceElement[] st = e.getStackTrace();
            StringBuilder sb = new StringBuilder();
            for (StackTraceElement s : st) {
                sb.append(s.toString()).append("\n");
            }
            Log.wtf("FRAGMENT THEORY CLASS onCreateView()", sb.toString());


            e.printStackTrace();
            Toast.makeText(getActivity(), "Ошибка подключения FRAGMENT THEORY CLASS", Toast.LENGTH_SHORT).show();
        }


        list_theory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShowAlert(view.getContext(), i);
            }
        });


        MyListViewAdapter_Theory adapter = new MyListViewAdapter_Theory(getActivity(),
                myItems, images, descriptions);
        list_theory.setAdapter(adapter);


        return view;
    }

    public void ShowAlert(Context c, int i) {
        String[] desc = ScheduleUtils.getLongTheoryDescriptions(arr);
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Информация о занятии")
                .setMessage(desc[i])
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    String ReadScheduleTheoryFromFile() {
        try {
            InputStream in = getActivity().openFileInput("schedule_theory");
            if (in != null) {
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(reader);

                StringBuilder builder = new StringBuilder();
                String str;
                while ((str = bufferedReader.readLine()) != null)
                    builder.append(str);
                in.close();
                Log.d("ReadScheduleTheoryFromFile", builder.toString());
                return builder.toString();
            }
            return "none";

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "none";
        } catch (IOException e) {
            e.printStackTrace();
            return "none";
        }
    }
}

