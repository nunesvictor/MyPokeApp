package br.edu.ifto.pdmii.aula0329.avaliacao01.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class PokemonWithAbilities {
    @Embedded
    public Pokemon pokemon;
    @Relation(
            parentColumn = "pokemonId",
            entityColumn = "abilityId",
            associateBy = @Junction(PokemonAbility.class)
    )
    public List<Ability> abilities;
}
