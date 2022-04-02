package br.edu.ifto.pdmii.aula0329.avaliacao01.ui.play;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.edu.ifto.pdmii.aula0329.avaliacao01.model.Pokemon;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.dao.AppDatabase;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.dao.PokemonDao;

public class PlayViewModel extends AndroidViewModel {
    private MutableLiveData<Pokemon> mPokemon;

    public PlayViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Pokemon> getPokemon() {
        if (mPokemon == null) {
            mPokemon = new MutableLiveData<>();
            selectPokemon();
        }

        return mPokemon;
    }

    private void selectPokemon() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            AppDatabase db = Room.databaseBuilder(getApplication().getApplicationContext(),
                    AppDatabase.class, "pokemon-db").build();
            PokemonDao pokemonDao = db.pokemonDao();

            mPokemon.postValue(pokemonDao.findByMaxBaseExperience());
        });
    }
}
