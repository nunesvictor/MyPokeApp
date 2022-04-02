package br.edu.ifto.pdmii.aula0329.avaliacao01.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"pokemonId", "abilityId"})
public class PokemonAbility {
    public long pokemonId;
    @ColumnInfo(index = true)
    public long abilityId;
}
