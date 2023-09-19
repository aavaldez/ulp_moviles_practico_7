package com.a2valdez.ulp_moviles_practico_7.ui.ubicacion;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.a2valdez.ulp_moviles_practico_7.R;
import com.a2valdez.ulp_moviles_practico_7.databinding.FragmentUbicacionBinding;
import com.google.android.gms.maps.SupportMapFragment;

public class UbicacionFragment extends Fragment {

    private UbicacionViewModel mv;

    private FragmentUbicacionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUbicacionBinding.inflate(inflater, container, false);
        mv = new ViewModelProvider(this).get(UbicacionViewModel.class);
        View root = binding.getRoot();

        mv.getMMapa().observe(getViewLifecycleOwner(), new Observer<UbicacionViewModel.MapaActual>() {
            @Override
            public void onChanged(UbicacionViewModel.MapaActual mapaActual) {
                SupportMapFragment smf = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                smf.getMapAsync(mapaActual);
            }
        });
        mv.obtenerMapa();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}