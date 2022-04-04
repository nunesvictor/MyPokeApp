package br.edu.ifto.pdmii.aula0329.avaliacao01.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.edu.ifto.pdmii.aula0329.avaliacao01.R;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.Ability;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.Pokemon;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.PokemonWithAbilities;

public class SizeAdjectiveListAdapter extends DetailListAdapter {
    public SizeAdjectiveListAdapter(Context context, List<PokemonWithAbilities> data) {
        super(context, data);
    }

    static class ViewHolder extends DetailListAdapter.ViewHolder {
        protected TextView sizeAdjectiveTextView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Pokemon pokemon = getItem(i).pokemon;
        List<Ability> abilities = getItem(i).abilities;
        SizeAdjectiveListAdapter.ViewHolder mViewHolder;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);

            view = layoutInflater.inflate(R.layout.list_pokemon_size_item, viewGroup, false);
            mViewHolder = new SizeAdjectiveListAdapter.ViewHolder();

            mViewHolder.spriteImageView = view.findViewById(R.id.image_pokemon_sprite);
            mViewHolder.nameTextView = view.findViewById(R.id.text_pokemon_name);
            mViewHolder.heightTextView = view.findViewById(R.id.text_pokemon_height);
            mViewHolder.weightTextView = view.findViewById(R.id.text_pokemon_weight);
            mViewHolder.baseExperienceTextView = view.findViewById(R.id.text_pokemon_basexp);
            mViewHolder.typeTextView = view.findViewById(R.id.text_pokemon_type);
            mViewHolder.abilitiesTextView = view.findViewById(R.id.text_pokemon_abilities);
            mViewHolder.sizeAdjectiveTextView = view.findViewById(R.id.text_pokemon_size_adjective);

            view.setTag(mViewHolder);
        } else {
            mViewHolder = (SizeAdjectiveListAdapter.ViewHolder) view.getTag();
        }

        Picasso.get()
                .load(pokemon.sprites.frontDefault)
                .resize(200, 200)
                .into(mViewHolder.spriteImageView);

        mViewHolder.nameTextView.setText(pokemon.name
                .substring(0, 1).toUpperCase().concat(pokemon.name.substring(1)));
        mViewHolder.heightTextView.setText(mContext.getString(R.string.text_pokemon_height, pokemon.height));
        mViewHolder.weightTextView.setText(mContext.getString(R.string.text_pokemon_weight, pokemon.weight));
        mViewHolder.baseExperienceTextView.setText(mContext.getString(R.string.text_pokemon_basexp, pokemon.baseExperience));
        mViewHolder.typeTextView.setText(mContext.getString(
                R.string.text_pokemon_type, String.join(", ", pokemon.types)));
        mViewHolder.abilitiesTextView.setText(mContext.getString(R.string.text_pokemon_abilities, getAbilitiesText(abilities)));
        String sizeAdjective;

        switch (i) {
            case 0:
                sizeAdjective = mContext.getString(R.string.text_pokemon_size_adjective_biggest);
                break;
            case 1:
                sizeAdjective = mContext.getString(R.string.text_pokemon_size_adjective_heaviest);
                break;
            case 2:
                sizeAdjective = mContext.getString(R.string.text_pokemon_size_adjective_smallest);
                break;
            case 3:
                sizeAdjective = mContext.getString(R.string.text_pokemon_size_adjective_lighter);
                break;
            default:
                sizeAdjective = "";
                break;
        }

        mViewHolder.sizeAdjectiveTextView.setText(sizeAdjective);

        return view;
    }
}
