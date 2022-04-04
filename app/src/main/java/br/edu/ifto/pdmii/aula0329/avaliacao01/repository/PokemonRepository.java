package br.edu.ifto.pdmii.aula0329.avaliacao01.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import br.edu.ifto.pdmii.aula0329.avaliacao01.api.NamedApiResource;
import br.edu.ifto.pdmii.aula0329.avaliacao01.api.NamedApiResourceList;
import br.edu.ifto.pdmii.aula0329.avaliacao01.api.PokemonAbilityApiResource;
import br.edu.ifto.pdmii.aula0329.avaliacao01.api.PokemonApiResource;
import br.edu.ifto.pdmii.aula0329.avaliacao01.api.PokemonTypeApiResource;
import br.edu.ifto.pdmii.aula0329.avaliacao01.http.HttpClient;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.Ability;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.Pokemon;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.PokemonAbility;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.dao.AbilityDao;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.dao.AppDatabase;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.dao.PokemonAbilityDao;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.dao.PokemonDao;
import br.edu.ifto.pdmii.aula0329.avaliacao01.util.RandomInt;

public class PokemonRepository {
    private final Executor executor;
    private final AppDatabase database;

    public PokemonRepository(AppDatabase database, Executor executor) {
        this.database = database;
        this.executor = executor;
    }

    public void initDatabase(final RepositoryCallback<Void> callback) {
        executor.execute(() -> {
            if (database.pokemonDao().getAll().size() == 0) {
                try {
                    NamedApiResourceList result = makeSynchronousFetchNamedApiRequest();

                    for (NamedApiResource namedApiResource : result.getResults()) {
                        PokemonApiResource pokemonApiResource = makeSynchronousFetchPokemonRequest(
                                namedApiResource.getUrl());
                        Pokemon pokemon = createPokemonFromApiResource(pokemonApiResource);
                        List<Ability> abilities = createAbilitiesFromApiResource(pokemonApiResource);
                        makeSynchronousDatabasePokemonInsert(pokemon, abilities);
                    }

                    callback.onComplete(new Result.Success<>(null));
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onComplete(new Result.Error<>(e));
                }
            } else {
                callback.onComplete(new Result.Success<>(null));
            }
        });
    }

    private NamedApiResourceList makeSynchronousFetchNamedApiRequest() throws IOException {
        int limit = RandomInt.between(10, 20);
        int offset = RandomInt.between(0, 1126 - limit);

        String templateUrl = "https://pokeapi.co/api/v2/pokemon?limit={0}&offset={1}";
        String url = MessageFormat.format(templateUrl, limit, offset);

        Log.d(PokemonRepository.class.getSimpleName(), url);
        Gson gson = new Gson();

        String data = new HttpClient().get(url);
        Type type = new TypeToken<NamedApiResourceList>() {
        }.getType();

        return gson.fromJson(data, type);
    }

    private PokemonApiResource makeSynchronousFetchPokemonRequest(String url) throws IOException {
        Gson gson = new Gson();

        String data = new HttpClient().get(url);
        Type type = new TypeToken<PokemonApiResource>() {
        }.getType();

        return gson.fromJson(data, type);
    }

    private void makeSynchronousDatabasePokemonInsert(
            Pokemon pokemon, List<Ability> abilities) {

        PokemonDao pokemonDao = database.pokemonDao();
        AbilityDao abilityDao = database.abilityDao();
        PokemonAbilityDao pokemonAbilityDao = database.pokemonAbilityDao();

        long pokemonId = pokemonDao.insert(pokemon);
        long[] abilitiesIds = abilityDao.insertAll(abilities.toArray(new Ability[]{}));

        for (long abilityId : abilitiesIds) {
            PokemonAbility pokemonAbility = new PokemonAbility();

            pokemonAbility.pokemonId = pokemonId;
            pokemonAbility.abilityId = abilityId;

            pokemonAbilityDao.insert(pokemonAbility);
        }
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
}
