package com.example.heatdetection.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.heatdetection.MyModel;
import com.example.heatdetection.R;
import com.example.heatdetection.databinding.FragmentHomeBinding;

import java.io.IOException;
import java.util.Arrays;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private int selectedWeight;
    private int selectedHeight;
    private int selectedAge;
    private int selectedGender;
    private float BMI;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner ageSpinner = view.findViewById(R.id.ageSpinner);
        Spinner GenderSpinner = view.findViewById(R.id.GenderSpinner);
        Spinner WeightSpinner = view.findViewById(R.id.WeightSpinner);
        Spinner HeightSpinner = view.findViewById(R.id.HeightSpinner);

        Integer[] ages = new Integer[98];
        for (int i = 0; i < 98; i++) {
            ages[i] = 1 + i;
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(adapter);

        String[] gender = new String[2];
        gender[0] = "Male";
        gender[1] = "Female";
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, gender);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        GenderSpinner.setAdapter(adapter1);

        Integer[] Weight = new Integer[198];
        for (int i = 0; i < 198; i++) {
            Weight[i] = 50 + i;
        }
        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Weight);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        WeightSpinner.setAdapter(adapter2);

        Integer[] Height = new Integer[198];
        for (int i = 0; i < 198; i++) {
            Height[i] = 50 + i;
        }
        ArrayAdapter<Integer> adapter3 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Height);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        HeightSpinner.setAdapter(adapter3);

        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAge = (int) parent.getItemAtPosition(position);
                //Toast.makeText(getContext(), "Selected Age: " + selectedAge, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 当没有选中时执行的操作
            }
        });

        GenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Male")){
                    selectedGender = 1;
                }else{
                    selectedGender = 0;
                }
                //Toast.makeText(getContext(), "Selected Gender: " + selectedGender, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 当没有选中时执行的操作
            }
        });

        WeightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedWeight = (int) parent.getItemAtPosition(position);
                //Toast.makeText(getContext(), "Selected Weight: " + selectedWeight, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 当没有选中时执行的操作
            }
        });

        HeightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  selectedHeight = (int) parent.getItemAtPosition(position);
                  //Toast.makeText(getContext(), "Selected Height: " + selectedHeight, Toast.LENGTH_SHORT).show();
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {
                 // 当没有选中时执行的操作
            }
        });
        BMI = (float) selectedWeight * 703 / (selectedHeight * selectedHeight);

        try {
            MyModel myModel = new MyModel(requireContext());

            float scaledBMI = BMI;
            float scaledAge = selectedAge;
            int scaledGender = selectedGender;

            float[] result = myModel.runModel(scaledBMI, scaledAge, scaledGender);

            TextView resultBox = view.findViewById(R.id.resultBox);

            resultBox.setText("output:" + result[0]);
            //resultBox.setText("output:" + Arrays.toString(result));

            //Log.d("Model Output", "Prediction result: " + result[0]);
            myModel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}