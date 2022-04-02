package br.edu.ifto.pdmii.aula0329.avaliacao01.api;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class PokemonSpriteApiResource {
    @ColumnInfo(name = "front_default")
    @SerializedName("front_default")
    public String frontDefault;
}
