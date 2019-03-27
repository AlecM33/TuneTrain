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
            templateNames[x] = allTemplates.get(x).templateName;
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_templatelist,templateNames);
        ListView listView = (ListView) findViewById(R.id.template_list);
        listView.setAdapter(adapter);
    }
}
