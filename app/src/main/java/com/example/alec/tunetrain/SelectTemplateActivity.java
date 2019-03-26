package com.example.alec.tunetrain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import com.example.alec.tunetrain.Entities.Template;

public class SelectTemplateActivity extends AppCompatActivity {

    List<Template> allTemplates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_template);
        AppDatabase db = AppDatabase.getAppDatabase(this.getBaseContext());
        allTemplates = db.templateDao().getTemplates();
        String[] templateNames = new String[allTemplates.size()];
        for (int x = 0; x < allTemplates.size(); x++) {
            String templateString = allTemplates.get(x).templateName + "\n\n" + allTemplates.get(x).pad1.noteName + "  "
                    + allTemplates.get(x).pad2.noteName + "  " + allTemplates.get(x).pad3.noteName + "  " + allTemplates.get(x).pad4.noteName +
                    "  " + allTemplates.get(x).pad5.noteName + "  " + allTemplates.get(x).pad6.noteName;
            if (allTemplates.get(x).templateName.contains("Major") || allTemplates.get(x).templateName.contains("Minor")) {
                templateString += "  " + allTemplates.get(x).pad7.noteName;
            }
            if (allTemplates.get(x).templateName.contains("Chromatic")) {
                templateString += "  " + allTemplates.get(x).pad7.noteName + "  " + allTemplates.get(x).pad8.noteName
                        + "  " + allTemplates.get(x).pad9.noteName + "  " + allTemplates.get(x).pad10.noteName + "  "
                        + allTemplates.get(x).pad11.noteName + "  " + allTemplates.get(x).pad12.noteName;
            }
            templateNames[x] = templateString;
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_templatelist,templateNames);
        ListView listView = (ListView) findViewById(R.id.template_list);
        listView.setAdapter(adapter);
    }
}
