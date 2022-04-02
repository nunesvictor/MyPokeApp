package br.edu.ifto.pdmii.aula0329.avaliacao01.api;

import com.google.gson.annotations.SerializedName;

public class PokemonAbilityApiResource {
    @SerializedName("is_hidden")
    public boolean isHidden;
    public NamedApiResource ability;
}
