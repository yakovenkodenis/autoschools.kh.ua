package autoschools.kh.ua.autosched;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class ScheduleUtils {

    // -----------PARSE CSV-------------------//

    public static ArrayList<TheoryLesson> GetTheoryArray(String s) {

        ArrayList<TheoryLesson> res = new ArrayList<>();
        s = s.substring(0, s.length() - 2);
        String[] split = s.split(";");

        String[] t = new String[5];
        for (int i = 0, j = 0; i < split.length; ++i, ++j) {
            t[j] = split[i];
            if (j == 4) {
                res.add(new TheoryLesson(t[0], t[1], t[2], t[3], t[4]));
                j = -1;
            }
        }
        Collections.sort(res);
        return res;
    }

    public static String[] getShortDescriptions(ArrayList<TheoryLesson> arr) {
        String[] desc = new String[arr.size()];
        for (int i = 0; i < desc.length; ++i) {
            desc[i] = arr.get(i).toShortDescriptionString();
        }
        return desc;
    }

    public static String[] getLongDescriptions(ArrayList<TheoryLesson> arr) {
        try {
            String[] desc = new String[arr.size()];
            for (int i = 0; i < arr.size(); ++i) {
                desc[i] = arr.get(i).toDescriptionString();
            }
            return desc;
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{new TheoryLesson("error-error", "error-error", "error-error", "error-error", "error-error").toDescriptionString()};
        }
    }

    public static String[] getTitles(ArrayList<TheoryLesson> arr) {
        try {
            if (arr != null) {
                String[] titles = new String[arr.size()];
                for (int i = 0; i < arr.size(); ++i) {
                    titles[i] = "Занятие " + (i + 1);
                }
                return titles;
            } else {
                return new String[]{"Ошибка сервера"};
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Ошибка сервера"};
        }
    }
}
