package br.edu.ifto.pdmii.aula0329.avaliacao01.model.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import br.edu.ifto.pdmii.aula0329.avaliacao01.model.PokemonAbility;

@Dao
public interface PokemonAbilityDao {
    @Insert
    long[] insert(PokemonAbility... pokemonAbilities);
}
