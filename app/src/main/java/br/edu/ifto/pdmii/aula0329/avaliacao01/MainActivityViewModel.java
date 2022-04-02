package br.edu.ifto.pdmii.aula0329.avaliacao01;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import br.edu.ifto.pdmii.aula0329.avaliacao01.api.NamedApiResource;
import br.edu.ifto.pdmii.aula0329.avaliacao01.api.NamedApiResourceList;
import br.edu.ifto.pdmii.aula0329.avaliacao01.api.PokemonAbilityApiResource;
import br.edu.ifto.pdmii.aula0329.avaliacao01.api.PokemonApiResource;
import br.edu.ifto.pdmii.aula0329.avaliacao01.api.PokemonTypeApiResource;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.Ability;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.Pokemon;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.dao.AppDatabase;
import br.edu.ifto.pdmii.aula0329.avaliacao01.repository.PokemonRepository;
import br.edu.ifto.pdmii.aula0329.avaliacao01.repository.Result;

public class MainActivityViewModel extends AndroidViewModel {
    private final PokemonRepository pokemonRepository;
    private final MutableLiveData<Boolean> mIsDatabaseInitilized = new MutableLiveData<>(false);

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.pokemonRepository = new PokemonRepository(Executors.newSingleThreadExecutor());
        makeFetchNamedApiResourceListRequest();
    }

    private void makeFetchNamedApiResourceListRequest() {
        pokemonRepository.makeFetchNamedApiRequest(result -> {
            if (result instanceof Result.Success) {
                NamedApiResourceList resourceList = ((Result.Success<NamedApiResourceList>) result).data;

                for (NamedApiResource resource : resourceList.getResults()) {
                    Log.d(MainActivityViewModel.class.getName(), "Fetching " + resource.getUrl());
                    makeFetchPokemonRequest(resource.getUrl());
                }

                mIsDatabaseInitilized.postValue(true);
            }
        });
    }

    private void makeFetchPokemonRequest(String url) {
        pokemonRepository.makeFetchPokemonRequest(url, result -> {
            if (result instanceof Result.Success) {
                PokemonApiResource pokemonApiResource = ((Result.Success<PokemonApiResource>) result).data;
                Log.d(MainActivityViewModel.class.getName(), "Fetched " + pokemonApiResource.name);

                Pokemon pokemon = createPokemonFromApiResource(pokemonApiResource);
                List<Ability> abilities = createAbilitiesFromApiResource(pokemonApiResource);

                Log.d(MainActivityViewModel.class.getName(), "Inserting " + pokemon.name);
                makeDatabasePokemonInsert(pokemon, abilities);
            }
        });
    }

    private void makeDatabasePokemonInsert(Pokemon pokemon, List<Ability> abilities) {
        AppDatabase db = Room.databaseBuilder(getApplication().getApplicationContext(),
                AppDatabase.class, "pokemon-db").build();

        pokemonRepository.makeDatabasePokemonInsert(db, pokemon, abilities, result -> {
            if (result instanceof Result.Success) {
                Log.d(MainActivityViewModel.class.getName(), "Inserted " + pokemon.name);
            }
        });
    }

    private Pokemon createPokemonFromApiResource(PokemonApiResource resource) {
        Pokemon pokemon = new Pokemon();
        List<String> types = new ArrayList<>();

        pokemon.pokemonId = resource.id;
        pokemon.name = resource.name;
        pokemon.baseExperience = resource.baseExperience;
        pokemon.height = resource.height;
        pokemon.weight = resource.weight;
        pokemon.sprites = resource.sprites;

        for (PokemonTypeApiResource typeApiResource : resource.types) {
            types.add(typeApiResource.getType().getName());
        }

        pokemon.types = types;

        return pokemon;
    }

    private List<Ability> createAbilitiesFromApiResource(PokemonApiResource resource) {
        List<PokemonAbilityApiResource> abilityApiResources = resource.abilities;
        List<Ability> abilities = new ArrayList<>();

        for (PokemonAbilityApiResource abilityApiResource : abilityApiResources) {
            Ability ability = new Ability();

            ability.isHidden = abilityApiResource.isHidden;
            ability.ability = abilityApiResource.ability.getName();

            abilities.add(ability);
        }

        return abilities;
    }

    public LiveData<Boolean> isDatabaseInitilized() {
        return mIsDatabaseInitilized;
    }
}
