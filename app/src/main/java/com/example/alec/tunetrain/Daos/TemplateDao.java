package com.example.alec.tunetrain.Daos;
import android.arch.persistence.room.*;
import com.example.alec.tunetrain.Entities.Template;

import java.util.List;

@Dao
public interface TemplateDao {
    @Insert
    void insert(Template template);

    @Query("DELETE FROM Templates")
    void deleteAll();

    @Query("SELECT * FROM Templates")
    List<Template> getTemplates();
}