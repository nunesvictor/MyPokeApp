package br.edu.ifto.pdmii.aula0329.avaliacao01.ui.details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.edu.ifto.pdmii.aula0329.avaliacao01.model.PokemonWithAbilities;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.dao.AppDatabase;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.dao.PokemonDao;

public class DetailsViewModel extends AndroidViewModel {

    private MutableLiveData<List<PokemonWithAbilities>> mPokemonsWithAbilities;

    public DetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<PokemonWithAbilities>> getPokemonsWithAbilities() {
        if (mPokemonsWithAbilities == null) {
            mPokemonsWithAbilities = new MutableLiveData<>();
            loadPokemonsWithAbilities();
        }

        return mPokemonsWithAbilities;
    }

    private void loadPokemonsWithAbilities() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            AppDatabase db = Room.databaseBuilder(getApplication().getApplicationContext(),
                    AppDatabase.class, "pokemon-db").build();
            PokemonDao pokemonDao = db.pokemonDao();

            mPokemonsWithAbilities.postValue(pokemonDao.getAllWithAbilities());
        });
    }
}
