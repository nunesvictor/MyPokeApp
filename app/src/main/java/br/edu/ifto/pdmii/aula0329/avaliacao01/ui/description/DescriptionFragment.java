package br.edu.ifto.pdmii.aula0329.avaliacao01.ui.description;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import br.edu.ifto.pdmii.aula0329.avaliacao01.adapters.DescriptionListAdapter;
import br.edu.ifto.pdmii.aula0329.avaliacao01.databinding.FragmentDescriptionBinding;

public class DescriptionFragment extends Fragment {

    private FragmentDescriptionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DescriptionViewModel descriptionViewModel =
                new ViewModelProvider(this).get(DescriptionViewModel.class);

        binding = FragmentDescriptionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ListView listView = binding.listPokemonDescription;
        final DescriptionListAdapter adapter = new DescriptionListAdapter(
                this.getContext(), new ArrayList<>());

        listView.setAdapter(adapter);

        descriptionViewModel.getPokemons().observe(getViewLifecycleOwner(), pokemons -> {
            adapter.getData().clear();
            adapter.getData().addAll(pokemons);
            adapter.notifyDataSetChanged();
        });
        
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
