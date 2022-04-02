package br.edu.ifto.pdmii.aula0329.avaliacao01.model.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.edu.ifto.pdmii.aula0329.avaliacao01.converters.DataConverter;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.Ability;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.Pokemon;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.PokemonAbility;

@Database(
        entities = {
                Pokemon.class,
                Ability.class,
                PokemonAbility.class
        },
        version = 1
)
@TypeConverters({DataConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract AbilityDao abilityDao();

    public abstract PokemonDao pokemonDao();

    public abstract PokemonAbilityDao pokemonAbilityDao();
}
