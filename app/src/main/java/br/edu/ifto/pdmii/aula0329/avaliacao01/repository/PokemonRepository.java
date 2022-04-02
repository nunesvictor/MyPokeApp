package br.edu.ifto.pdmii.aula0329.avaliacao01.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.Executor;

import br.edu.ifto.pdmii.aula0329.avaliacao01.api.NamedApiResourceList;
import br.edu.ifto.pdmii.aula0329.avaliacao01.api.PokemonApiResource;
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

    public PokemonRepository(Executor executor) {
        this.executor = executor;
    }

    public void makeFetchNamedApiRequest(
            final RepositoryCallback<NamedApiResourceList> callback) {

        executor.execute(() -> {
            try {
                Result<NamedApiResourceList> result = makeSynchronousFetchNamedApiRequest();
                callback.onComplete(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void makeFetchPokemonRequest(
            final String url,
            final RepositoryCallback<PokemonApiResource> callback) {

        executor.execute(() -> {
            try {
                Result<PokemonApiResource> result = makeSynchronousFetchPokemonRequest(url);
                callback.onComplete(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void makeDatabasePokemonInsert(
            final AppDatabase db,
            final Pokemon pokemon,
            final List<Ability> abilities,
            final RepositoryCallback<Void> callback) {

        executor.execute(() -> {
            Result<Void> result = makeSynchronousDatabasePokemonInsert(db, pokemon, abilities);
            callback.onComplete(result);
        });
    }

    private Result<NamedApiResourceList> makeSynchronousFetchNamedApiRequest() throws IOException {
        int limit = RandomInt.between(10, 20);
        int offset = RandomInt.between(0, 1126 - limit);

        String templateUrl = "https://pokeapi.co/api/v2/pokemon?limit={0}&offset={1}";
        String url = MessageFormat.format(templateUrl, limit, offset);

        Log.d(PokemonRepository.class.getSimpleName(), url);
        Gson gson = new Gson();

        String data = new HttpClient().get(url);
        Type type = new TypeToken<NamedApiResourceList>() {
        }.getType();

        return new Result.Success<>(gson.fromJson(data, type));
    }

    private Result<PokemonApiResource> makeSynchronousFetchPokemonRequest(String url) throws IOException {
        Gson gson = new Gson();

        String data = new HttpClient().get(url);
        Type type = new TypeToken<PokemonApiResource>() {
        }.getType();

        return new Result.Success<>(gson.fromJson(data, type));
    }

    private Result<Void> makeSynchronousDatabasePokemonInsert(
            AppDatabase db, Pokemon pokemon, List<Ability> abilities) {

        PokemonDao pokemonDao = db.pokemonDao();
        AbilityDao abilityDao = db.abilityDao();
        PokemonAbilityDao pokemonAbilityDao = db.pokemonAbilityDao();

        long pokemonId = pokemonDao.insert(pokemon);
        long[] abilitiesIds = abilityDao.insertAll(abilities.toArray(new Ability[]{}));

        for (long abilityId : abilitiesIds) {
            PokemonAbility pokemonAbility = new PokemonAbility();

            pokemonAbility.pokemonId = pokemonId;
            pokemonAbility.abilityId = abilityId;

            pokemonAbilityDao.insert(pokemonAbility);
        }

        return new Result.Success<>(null);
    }
}
