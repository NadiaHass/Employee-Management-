package com.example.employeesmanagment.ui.allEmployee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.employeesmanagment.data.model.Employee;
import com.example.employeesmanagment.data.sqlite.EmployeeDb;
import com.example.employeesmanagment.databinding.ActivityAllEmployeeBinding;
import com.example.employeesmanagment.ui.addEmployee.AddEmployeeActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AllEmployeeActivity extends AppCompatActivity {

    private ActivityAllEmployeeBinding binding;
    private ArrayList<Employee> employeeList = new ArrayList<>();
    private EmployeeDb employeeDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fabAddEmployee.setOnClickListener(v-> {
            startActivity(new Intent(this , AddEmployeeActivity.class));
            finish();
        });

         employeeDb = new EmployeeDb(this);

        getEmployeeList(this);

        try {
            setSearchViewPropreties();
        }catch (Exception e){

        }

                binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    filter(binding.searchView.getQuery().toString());
                    if(newText.equals("")){
                        getEmployeeList(AllEmployeeActivity.this);
                    }
                }catch (Exception e){

                }

                return false;
            }
        });
    }

    private void setSearchViewPropreties() {
        binding.searchView.setQueryHint("Chercher des employees");
        binding.searchView.setIconified(false);
        binding.searchView.clearFocus();
//        ImageView button = binding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
//        button.setOnClickListener(view -> {
//        });
    }

    private void getEmployeeList(AllEmployeeActivity allEmployeeActivity) {
                employeeList = employeeDb.getAllEmployee();
                AllEmployeeAdapter allEmployeeAdapter = new AllEmployeeAdapter(allEmployeeActivity , employeeList);
                binding.rvAllEmployee.setAdapter(allEmployeeAdapter);
                binding.rvAllEmployee.setLayoutManager(new LinearLayoutManager(allEmployeeActivity , RecyclerView.VERTICAL , false));
    }

    private void filter(String input) {
        try{

            ArrayList<Employee> employees = new ArrayList<>();
            EmployeeDb employeeDb = new EmployeeDb(this);
            employees = employeeDb.getAllEmployeeBySearch(input);
            AllEmployeeAdapter allEmployeeAdapter = new AllEmployeeAdapter(this , employees);
            binding.rvAllEmployee.setAdapter(allEmployeeAdapter);
            binding.rvAllEmployee.setLayoutManager(new LinearLayoutManager(this , RecyclerView.VERTICAL , false));

        }catch (Exception e){
        }

    }
}