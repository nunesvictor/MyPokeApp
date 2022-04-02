package br.edu.ifto.pdmii.aula0329.avaliacao01.ui.play;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import br.edu.ifto.pdmii.aula0329.avaliacao01.R;
import br.edu.ifto.pdmii.aula0329.avaliacao01.databinding.FragmentPlayBinding;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.Pokemon;

public class PlayFragment extends Fragment {

    static class ViewHolder {
        private ImageView spriteImageView;
        private TextView nameTextView;
        private TextView heightTextView;
        private TextView weightTextView;
        private TextView baseExperienceTextView;
        private TextView typeTextView;
        private TextView adjectiveTextView;
    }

    final PlayFragment.ViewHolder mViewHolder = new PlayFragment.ViewHolder();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        PlayViewModel playViewModel =
                new ViewModelProvider(this).get(PlayViewModel.class);

        FragmentPlayBinding binding = FragmentPlayBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initViewHolder(binding);
        playViewModel.getPokemon().observe(getViewLifecycleOwner(), this::buildPokemonCard);

        return root;
    }

    private void initViewHolder(FragmentPlayBinding binding) {
        mViewHolder.spriteImageView = binding.imagePokemonSprite;
        mViewHolder.nameTextView = binding.textPokemonName;
        mViewHolder.heightTextView = binding.textPokemonHeight;
        mViewHolder.weightTextView = binding.textPokemonWeight;
        mViewHolder.baseExperienceTextView = binding.textPokemonBasexp;
        mViewHolder.typeTextView = binding.textPokemonType;
        mViewHolder.adjectiveTextView = binding.textPokemonAdjective;
    }

    private void buildPokemonCard(Pokemon pokemon) {
        Picasso.get()
                .load(pokemon.sprites.frontDefault)
                .resize(200, 200)
                .into(mViewHolder.spriteImageView);
        mViewHolder.nameTextView.setText(pokemon.name
                .substring(0, 1).toUpperCase().concat(pokemon.name.substring(1)));
        mViewHolder.heightTextView.setText(getString(R.string.text_pokemon_height, pokemon.height));
        mViewHolder.weightTextView.setText(getString(R.string.text_pokemon_weight, pokemon.weight));
        mViewHolder.baseExperienceTextView.setText(getString(R.string.text_pokemon_basexp, pokemon.baseExperience));
        mViewHolder.typeTextView.setText(getString(
                R.string.text_pokemon_type, String.join(", ", pokemon.types)));
        mViewHolder.adjectiveTextView.setText(getString(R.string.text_pokemon_adjective_winner));
    }
}
