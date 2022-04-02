package br.edu.ifto.pdmii.aula0329.avaliacao01.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import br.edu.ifto.pdmii.aula0329.avaliacao01.model.Ability;

@Dao
public interface AbilityDao {
    @Query("SELECT * FROM Ability")
    List<Ability> getAll();

    @Query("SELECT * FROM Ability WHERE abilityId IN (:ids)")
    List<Ability> loadAllByIds(int[] ids);

    @Query("SELECT * FROM Ability WHERE ability LIKE :name LIMIT 1")
    Ability findByName(String name);

    @Insert
    long insert(Ability ability);

    @Insert
    long[] insertAll(Ability... abilities);

    @Delete
    void delete(Ability ability);
}
