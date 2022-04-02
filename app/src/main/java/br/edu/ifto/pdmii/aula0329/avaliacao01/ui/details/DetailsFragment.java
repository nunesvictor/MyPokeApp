package br.edu.ifto.pdmii.aula0329.avaliacao01.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import br.edu.ifto.pdmii.aula0329.avaliacao01.adapters.DetailListAdapter;
import br.edu.ifto.pdmii.aula0329.avaliacao01.databinding.FragmentDetailsBinding;

public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DetailsViewModel detailsViewModel =
                new ViewModelProvider(this).get(DetailsViewModel.class);

        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ListView listView = binding.listPokemonDetail;
        final DetailListAdapter adapter = new DetailListAdapter(
                this.getContext(), new ArrayList<>());

        listView.setAdapter(adapter);

        detailsViewModel.getPokemonsWithAbilities().observe(getViewLifecycleOwner(), pokemonsWithAbilities -> {
            adapter.getData().clear();
            adapter.getData().addAll(pokemonsWithAbilities);
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
