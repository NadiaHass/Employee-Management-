package com.example.employeesmanagment.ui.updateEmployee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.employeesmanagment.R;
import com.example.employeesmanagment.data.model.Employee;
import com.example.employeesmanagment.data.sqlite.EmployeeDb;
import com.example.employeesmanagment.databinding.ActivityUpdateEmployeeBinding;
import com.example.employeesmanagment.ui.addEmployee.AddEmployeeActivity;
import com.example.employeesmanagment.ui.allEmployee.AllEmployeeActivity;
import com.example.employeesmanagment.ui.allEmployee.AllEmployeeAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class UpdateEmployeeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ActivityUpdateEmployeeBinding binding;
    String[] genderSpinner = {"Femme" , "Homme"};
    private String selectedItemSpinner = "Femme";
    final Calendar myCalendar= Calendar.getInstance();
    Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setDataFromIntent();
        setupSpinner(intent.getStringExtra("gender").toLowerCase());

        binding.etDate.setFocusable(false);

        binding.ivAddEmployee.setOnClickListener(v -> {
            if(inputNotEmpty()){
                    Employee employee = getEmployee();
                    updateEmployee(employee);

            }
            startActivity(new Intent(this , AllEmployeeActivity.class));
            finish();
        });

        binding.ivCancel.setOnClickListener(v -> {
            startActivity(new Intent(this , AllEmployeeActivity.class));
            finish();
        });


        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        binding.etDate.setOnClickListener(view -> {
            new DatePickerDialog(UpdateEmployeeActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

    }

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        binding.etDate.setText(dateFormat.format(myCalendar.getTime()));
    }
    private void updateEmployee(Employee employee) {
        EmployeeDb employeeDb = new EmployeeDb(this);
        employeeDb.updateEmployeeById(employee);
    }

    private void setDataFromIntent() {
        intent = getIntent();
        binding.etFirstName.setText( intent.getStringExtra("firstName"));
        binding.etSecondName.setText(intent.getStringExtra("secondName"));
        binding.etEmail.setText(intent.getStringExtra("email"));
        binding.etPhone.setText(intent.getStringExtra("phone") );  ;
        binding.etAdresse.setText(intent.getStringExtra("address"));
        binding.etDepartment.setText(intent.getStringExtra("department"));
        binding.etDate.setText(intent.getStringExtra("startDate"));
        binding.etSalary.setText(intent.getStringExtra("salary"));
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this , AllEmployeeActivity.class));
        finish();
    }

    private Employee getEmployee() {
        String firstName = binding.etFirstName.getText().toString();
        String secondName = binding.etSecondName.getText().toString();
        String email = binding.etEmail.getText().toString();
        String phone = binding.etPhone.getText().toString();
        String address = binding.etAdresse.getText().toString();
        String department = binding.etDepartment.getText().toString();
        String startDate = binding.etDate.getText().toString();
        String salary = binding.etSalary.getText().toString();

        return new Employee(0 , firstName , secondName , email , phone , selectedItemSpinner , address , department , startDate , salary);
    }

    private boolean inputNotEmpty() {
        return binding.etFirstName.getText().toString().trim().length() > 0 &&
                binding.etSecondName.getText().toString().trim().length() > 0  &&
                binding.etEmail.getText().toString().trim().length() > 0  &&
                binding.etPhone.getText().toString().trim().length() > 0 &&
                binding.etAdresse.getText().toString().trim().length() > 0 &&
                binding.etDepartment.getText().toString().trim().length() > 0  &&
                binding.etDate.getText().toString().trim().length() > 0  &&
                binding.etSalary.getText().toString().trim().length() > 0;
    }

    private void setupSpinner(String gender) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderSpinner);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        binding.spinner.setAdapter(arrayAdapter);
        if (gender.equals("femme"))
            binding.spinner.setSelection(0, true);
        else
            binding.spinner.setSelection(1, true);

        binding.spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedItemSpinner = genderSpinner[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}