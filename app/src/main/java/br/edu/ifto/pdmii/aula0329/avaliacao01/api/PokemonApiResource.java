package br.edu.ifto.pdmii.aula0329.avaliacao01.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonApiResource {
    public int id;
    public String name;
    @SerializedName("base_experience")
    public int baseExperience;
    public int height;
    public int weight;
    public List<PokemonAbilityApiResource> abilities;
    public PokemonSpriteApiResource sprites;
    public List<PokemonTypeApiResource> types;
}
