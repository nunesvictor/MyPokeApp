package br.edu.ifto.pdmii.aula0329.avaliacao01.ui.description;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.edu.ifto.pdmii.aula0329.avaliacao01.model.Pokemon;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.dao.AppDatabase;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.dao.PokemonDao;

public class DescriptionViewModel extends AndroidViewModel {

    private MutableLiveData<List<Pokemon>> mPokemons;

    public DescriptionViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Pokemon>> getPokemons() {
        if (mPokemons == null) {
            mPokemons = new MutableLiveData<>();
            selectPokemons();
        }

        return mPokemons;
    }

    private void selectPokemons() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            AppDatabase db = Room.databaseBuilder(getApplication().getApplicationContext(),
                    AppDatabase.class, "pokemon-db").build();
            PokemonDao pokemonDao = db.pokemonDao();

            mPokemons.postValue(pokemonDao.getAll());
        });
    }
}
