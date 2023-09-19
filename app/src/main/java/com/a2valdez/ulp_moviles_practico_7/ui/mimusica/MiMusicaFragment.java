package com.a2valdez.ulp_moviles_practico_7.ui.mimusica;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a2valdez.ulp_moviles_practico_7.databinding.FragmentMiMusicaBinding;

public class MiMusicaFragment extends Fragment {

    private Context context;
    private MiMusicaViewModel mv;
    private FragmentMiMusicaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMiMusicaBinding.inflate(inflater, container, false);
        mv = new ViewModelProvider(this).get(MiMusicaViewModel.class);
        View root = binding.getRoot();
        context = container.getContext();
        binding.btReproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ServicioMusica.class);
                context.startService(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}