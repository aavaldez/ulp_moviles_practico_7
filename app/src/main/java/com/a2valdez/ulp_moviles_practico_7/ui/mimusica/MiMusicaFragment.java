package com.a2valdez.ulp_moviles_practico_7.ui.mimusica;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a2valdez.ulp_moviles_practico_7.databinding.FragmentMiMusicaBinding;

public class MiMusicaFragment extends Fragment {

    private FragmentMiMusicaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MiMusicaViewModel miMusicaViewModel =
                new ViewModelProvider(this).get(MiMusicaViewModel.class);

        binding = FragmentMiMusicaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}