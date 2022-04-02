package br.edu.ifto.pdmii.aula0329.avaliacao01.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ifto.pdmii.aula0329.avaliacao01.R;
import br.edu.ifto.pdmii.aula0329.avaliacao01.model.Pokemon;

public class DescriptionListAdapter extends BaseAdapter {
    private final List<Pokemon> mData;
    private final Context mContext;

    static class ViewHolder {
        private TextView idTextView;
        private TextView nameTextView;
    }

    public DescriptionListAdapter(Context context, List<Pokemon> data) {
        this.mContext = context;
        this.mData = data;
    }

    public List<Pokemon> getData() {
        return mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mData.get(i).pokemonId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Pokemon pokemon = mData.get(i);
        ViewHolder mViewHolder;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);

            view = layoutInflater.inflate(R.layout.list_pokemon_description_item, viewGroup, false);
            mViewHolder = new ViewHolder();

            mViewHolder.idTextView = view.findViewById(R.id.text_pokemon_id);
            mViewHolder.nameTextView = view.findViewById(R.id.text_pokemon_name);

            view.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) view.getTag();
        }

        mViewHolder.idTextView.setText(mContext.getString(R.string.text_pokemon_id, pokemon.pokemonId));
        mViewHolder.nameTextView.setText(pokemon.name
                .substring(0, 1).toUpperCase().concat(pokemon.name.substring(1)));

        return view;
    }
}
