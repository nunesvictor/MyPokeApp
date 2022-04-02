package br.edu.ifto.pdmii.aula0329.avaliacao01.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.edu.ifto.pdmii.aula0329.avaliacao01.api.PokemonSpriteApiResource;
import br.edu.ifto.pdmii.aula0329.avaliacao01.api.PokemonTypeApiResource;

@Entity
public class Pokemon {
    @PrimaryKey
    public long pokemonId;
    public String name;
    @SerializedName("base_experience")
    @ColumnInfo(name = "base_experience")
    public int baseExperience;
    public int height;
    public int weight;
    @Embedded
    public PokemonSpriteApiResource sprites;
    public List<String> types;
}
