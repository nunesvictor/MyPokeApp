package br.edu.ifto.pdmii.aula0329.avaliacao01.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.edu.ifto.pdmii.aula0329.avaliacao01.R;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.Ability;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.Pokemon;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.PokemonWithAbilities;

public class DetailListAdapter extends BaseAdapter {
    protected final List<PokemonWithAbilities> mData;
    protected final Context mContext;

    static class ViewHolder {
        protected ImageView spriteImageView;
        protected TextView nameTextView;
        protected TextView heightTextView;
        protected TextView weightTextView;
        protected TextView baseExperienceTextView;
        protected TextView typeTextView;
        protected TextView abilitiesTextView;
    }

    public DetailListAdapter(Context context, List<PokemonWithAbilities> data) {
        this.mContext = context;
        this.mData = data;
    }

    public List<PokemonWithAbilities> getData() {
        return mData;
    }

    protected String getAbilitiesText(List<Ability> abilities) {
        StringBuilder abilitiesText = new StringBuilder();

        for (Ability ability : abilities) {
            if (ability.isHidden) {
                continue;
            }

            abilitiesText.append((abilitiesText.length() == 0)
                    ? ability.ability
                    : ", ".concat(ability.ability));
        }

        return abilitiesText.toString();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public PokemonWithAbilities getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mData.get(i).pokemon.pokemonId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Pokemon pokemon = getItem(i).pokemon;
        List<Ability> abilities = getItem(i).abilities;
        DetailListAdapter.ViewHolder mViewHolder;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);

            view = layoutInflater.inflate(R.layout.list_pokemon_detail_item, viewGroup, false);
            mViewHolder = new DetailListAdapter.ViewHolder();

            mViewHolder.spriteImageView = view.findViewById(R.id.image_pokemon_sprite);
            mViewHolder.nameTextView = view.findViewById(R.id.text_pokemon_name);
            mViewHolder.heightTextView = view.findViewById(R.id.text_pokemon_height);
            mViewHolder.weightTextView = view.findViewById(R.id.text_pokemon_weight);
            mViewHolder.baseExperienceTextView = view.findViewById(R.id.text_pokemon_basexp);
            mViewHolder.typeTextView = view.findViewById(R.id.text_pokemon_type);
            mViewHolder.abilitiesTextView = view.findViewById(R.id.text_pokemon_abilities);

            view.setTag(mViewHolder);
        } else {
            mViewHolder = (DetailListAdapter.ViewHolder) view.getTag();
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

        return view;
    }

}
