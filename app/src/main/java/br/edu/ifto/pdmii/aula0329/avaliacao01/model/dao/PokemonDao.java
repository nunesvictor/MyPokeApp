package br.edu.ifto.pdmii.aula0329.avaliacao01.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import br.edu.ifto.pdmii.aula0329.avaliacao01.model.Pokemon;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.PokemonWithAbilities;

@Dao
public interface PokemonDao {
    @Query("SELECT * FROM Pokemon")
    List<Pokemon> getAll();

    @Query("SELECT * FROM Pokemon WHERE pokemonId IN (:ids)")
    List<Pokemon> loadAllByIds(int[] ids);

    @Transaction
    @Query("SELECT * FROM Pokemon")
    List<PokemonWithAbilities> getAllWithAbilities();

    @Query("SELECT * FROM Pokemon WHERE name LIKE :name LIMIT 1")
    Pokemon findByName(String name);

    @Transaction
    @Query("SELECT * FROM Pokemon ORDER BY height DESC LIMIT 1")
    PokemonWithAbilities findByMaxHeight();

    @Transaction
    @Query("SELECT * FROM Pokemon ORDER BY height LIMIT 1")
    PokemonWithAbilities findByMinHeight();

    @Transaction
    @Query("SELECT * FROM Pokemon ORDER BY weight DESC LIMIT 1")
    PokemonWithAbilities findByMaxWeight();

    @Transaction
    @Query("SELECT * FROM Pokemon ORDER BY weight LIMIT 1")
    PokemonWithAbilities findByMinWeight();

    @Query("SELECT * FROM Pokemon ORDER BY base_experience DESC LIMIT 1")
    Pokemon findByMaxBaseExperience();

    @Insert
    Long insert(Pokemon pokemon);

    @Insert
    long[] insertAll(Pokemon... pokemons);

    @Delete
    void delete(Pokemon pokemon);
}
