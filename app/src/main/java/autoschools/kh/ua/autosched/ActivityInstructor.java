package autoschools.kh.ua.autosched;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ActivityInstructor extends Activity
{
    String[] myItems;
    String[] descriptions;
    int[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(ReadFile().contains("logged_in_instructor")) {
            setContentView(R.layout.instructor_activity);

            Resources res = getResources();
            myItems = res.getStringArray(R.array.titles);
            descriptions = res.getStringArray(R.array.descriptions_practice);

            ListView lstPractice = (ListView) findViewById(R.id.listView);

            lstPractice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ShowAlert();
                }
            });

            lstPractice.setAdapter(new MyListViewAdapter_Instructor(getApplicationContext(), myItems, images, descriptions));
        }
        else{
            finish();
            startActivity(new Intent(ActivityInstructor.this, LoginActivity.class));
        }
    }

    public String ReadFile()
    {
        try{
            InputStream in = openFileInput("is_logged_in");
            if(in != null)
            {
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(reader);

                StringBuilder builder = new StringBuilder();
                String str;
                while((str = bufferedReader.readLine()) != null)
                    builder.append(str);
                in.close();
                Log.d("ReadFile", builder.toString());
                return builder.toString();
            }
            return "logged_out";

        } catch(FileNotFoundException e){
            e.printStackTrace();
            return "logged_out";
        } catch(IOException e){
            e.printStackTrace();
            return "logged_out";
        }
    }
    void WriteFile(String is_logged_in)
    {
        try{
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter
                    (openFileOutput("is_logged_in", MODE_PRIVATE)));

            bw.write(is_logged_in);
            Log.d("WriteFile", "файлы записаны");

            bw.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public void ShowAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Информация о занятии")
                .setMessage("Имя студента: " + "\t" + "Иванов Пётр Петрович" +
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

    private Menu optionsMenu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.action_menu, menu);
//        return true;

        this.optionsMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void setRefreshActionButtonState(final boolean refreshing)
    {
        if(optionsMenu != null)
        {
            final MenuItem refreshItem = optionsMenu.findItem(R.id.update);
            if(refreshItem != null)
            {
                if(refreshing)
                    refreshItem.setActionView(R.layout.update_intermediate_progress);
                else refreshItem.setActionView(null);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id) {
            case R.id.site:
                return true;
            case R.id.update:
                setRefreshActionButtonState(true);
                return true;
            case R.id.logout: {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityInstructor.this);
                builder.setMessage("Вы действительно хотите выйти?")
                        .setTitle("Выход")
                        .setIcon(R.drawable.logout_icon)
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }
                        ).setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        WriteFile("logged_out");
                        startActivity(new Intent(ActivityInstructor.this, LoginActivity.class));
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();

                dialog.show();

                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}

class MyListViewAdapter_Instructor extends ArrayAdapter<String> {
    private class MyViewHolder {
        ImageView myImage;
        TextView myTitle;
        TextView myDescription;

        public MyViewHolder(View v) {
            myImage = (ImageView) v.findViewById(R.id.imageView_instructor);
            myTitle = (TextView) v.findViewById(R.id.textView_instructor);
            myDescription = (TextView) v.findViewById(R.id.textView2_instructor);
        }
    }

    Context context;
    int[] images;
    String[] titleArray;
    String[] descriptionArray;

    MyListViewAdapter_Instructor(Context c, String[] titles, int[] imgs, String[] desc) {
        super(c, R.layout.single_row_instructor, R.id.textView_instructor, titles);
        this.context = c;
        this.images = imgs;
        this.titleArray = titles;
        this.descriptionArray = desc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MyViewHolder holder;

        if (row == null) //first launch
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_row_instructor, parent, false);
            holder = new MyViewHolder(row);
            row.setTag(holder);
        } else //recycling
            holder = (MyViewHolder) row.getTag();

        holder.myImage.setImageResource(R.drawable.car_icon2);
        holder.myTitle.setText(titleArray[position]);
        holder.myDescription.setText(descriptionArray[position]);

//        if(descriptionArray[position] == "Р’РёРґ Р·Р°РЅСЏС‚РёСЏ: Р›РµРєС†РёСЏ")
//        if (position % 2 == 0)
//            row.setBackgroundColor(Color.parseColor("#FFDEAD"));
//        else row.setBackgroundColor(Color.parseColor("#98FB98"));

        return row;
    }
}

