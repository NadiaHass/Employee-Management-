package com.example.employeesmanagment.ui.addEmployee;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.employeesmanagment.data.model.Employee;
import com.example.employeesmanagment.data.sqlite.EmployeeDb;
import com.example.employeesmanagment.databinding.ActivityAddEmployeeBinding;
import com.example.employeesmanagment.ui.allEmployee.AllEmployeeActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEmployeeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ActivityAddEmployeeBinding binding;
    String[] genderSpinner = {"Femme" , "Homme"};
    private String selectedItemSpinner = "Femme";
    final Calendar myCalendar= Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupSpinner();
        binding.etDate.setFocusable(false);
        binding.ivAddEmployee.setOnClickListener(v -> {
            if(inputNotEmpty()){
                Employee employee = getEmployee();
                insertToDatabase(employee);
            }
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
            new DatePickerDialog(AddEmployeeActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

    }

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        binding.etDate.setText(dateFormat.format(myCalendar.getTime()));
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

    private void insertToDatabase(Employee employee) {
        EmployeeDb employeeDb = new EmployeeDb(this);
        if (employeeDb.addEmployee(employee)){
            Toast.makeText(this , "L'employee a ete ajoutee" , Toast.LENGTH_LONG).show();
            startActivity(new Intent(this , AllEmployeeActivity.class));
            finish();
        }else{
            Toast.makeText(this , "Errreur dans l'ajout de l'employee" , Toast.LENGTH_LONG).show();
        }
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

    private void setupSpinner() {
        binding.spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderSpinner);

        arrayAdapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        binding.spinner.setAdapter(arrayAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedItemSpinner = genderSpinner[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}