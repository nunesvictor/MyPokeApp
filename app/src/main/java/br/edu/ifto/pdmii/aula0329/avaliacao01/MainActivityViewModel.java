package br.edu.ifto.pdmii.aula0329.avaliacao01;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.util.concurrent.Executors;

import br.edu.ifto.pdmii.aula0329.avaliacao01.model.dao.AppDatabase;
import br.edu.ifto.pdmii.aula0329.avaliacao01.repository.PokemonRepository;
import br.edu.ifto.pdmii.aula0329.avaliacao01.repository.Result;

public class MainActivityViewModel extends AndroidViewModel {
    private final MutableLiveData<Boolean> mIsDatabaseInitilized = new MutableLiveData<>(false);

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        AppDatabase database = Room.databaseBuilder(
                getApplication().getApplicationContext(),
                AppDatabase.class, "pokemon-db").build();

        PokemonRepository pokemonRepository = new PokemonRepository(
                database, Executors.newSingleThreadExecutor());

        pokemonRepository.initDatabase(result -> {
            if (result instanceof Result.Success) {
                mIsDatabaseInitilized.postValue(true);
            }
        });
    }

    public LiveData<Boolean> isDatabaseInitilized() {
        return mIsDatabaseInitilized;
    }
}
