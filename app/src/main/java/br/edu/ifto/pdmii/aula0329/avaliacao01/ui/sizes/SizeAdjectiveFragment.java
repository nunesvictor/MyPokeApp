package br.edu.ifto.pdmii.aula0329.avaliacao01.ui.sizes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import br.edu.ifto.pdmii.aula0329.avaliacao01.adapters.SizeAdjectiveListAdapter;
import br.edu.ifto.pdmii.aula0329.avaliacao01.databinding.FragmentSizesBinding;

public class SizeAdjectiveFragment extends Fragment {

    private FragmentSizesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SizeAdjectiveViewModel sizeAdjectiveViewModel =
                new ViewModelProvider(this).get(SizeAdjectiveViewModel.class);

        binding = FragmentSizesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ListView listView = binding.listPokemonSizes;
        final SizeAdjectiveListAdapter adapter = new SizeAdjectiveListAdapter(
                this.getContext(), new ArrayList<>());

        listView.setAdapter(adapter);

        sizeAdjectiveViewModel
                .getPokemonsWithAbilities()
                .observe(getViewLifecycleOwner(), pokemonsWithAbilities -> {
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
