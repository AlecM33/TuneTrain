package com.example.alec.tunetrain.Daos;
import android.arch.persistence.room.*;
import com.example.alec.tunetrain.DatabaseEntities;
import java.util.List;

@Dao
public interface TemplateDao {
    @Insert
    void insert(DatabaseEntities.Template template);

    @Query("DELETE FROM Templates")
    void deleteAll();

    @Query("SELECT * FROM Templates")
    List<DatabaseEntities.Template> getTemplates();
}