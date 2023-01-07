package com.example.employeesmanagment.ui.viewEmployee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.example.employeesmanagment.R;
import com.example.employeesmanagment.data.model.Employee;
import com.example.employeesmanagment.databinding.ActivityViewEmployeeBinding;

public class ViewEmployeeActivity extends AppCompatActivity {
    private ActivityViewEmployeeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

      Employee employee = getEmployeeFromIntent();
      updateUi(employee);

      binding.ivEmail.setOnClickListener(view -> sendEmail(employee.getEmail()));

      binding.ivPhone.setOnClickListener(view -> callNumber(employee.getPhoneNumber()));

      binding.ivMessage.setOnClickListener(view -> sendMessage(employee.getPhoneNumber()));
    }

    private void sendMessage(String phoneNumber) {
        String tel = "sms:" + phoneNumber;
        String message= "Bonjour Monsieur / Madame ";
        Uri uri=Uri.parse(tel);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.putExtra("sms_body", message);
        startActivity(intent);
    }

    private void callNumber(String phoneNumber) {
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        startActivity(i);

    }

    private void sendEmail(String email) {
        Intent selectorIntent = new Intent(Intent.ACTION_SENDTO);
        selectorIntent.setData(Uri.parse("mailto:"));

        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email de l'entreprise ");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Madame/Monsieur :");
        emailIntent.setSelector( selectorIntent );

        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    private void updateUi(Employee employee) {
        binding.tvName.setText(employee.getFirstName() + " " + employee.getSecondName());
        binding.tvEmail.setText(employee.getEmail());
        binding.tvPhone.setText(employee.getPhoneNumber());
        binding.tvAddress.setText(employee.getAddress());
        binding.tvDepartment.setText(employee.getDepartment());
        binding.tvStartDate.setText(employee.getStartDate());
        binding.tvSalary.setText(employee.getSalary());

        String gender = employee.getGender().toLowerCase();
        int id = getResources().getIdentifier(gender , "drawable" , getPackageName());
        Drawable drawable = this.getResources().getDrawable(id);
        binding.circleImageView.setImageDrawable(drawable);
    }

    private Employee getEmployeeFromIntent() {
        Intent intent = getIntent();
        return new Employee(
                Integer.parseInt(intent.getStringExtra("id")) ,
                intent.getStringExtra("firstName") ,
                intent.getStringExtra("secondName"),
                intent.getStringExtra("email") ,
                intent.getStringExtra("phone") ,
                intent.getStringExtra("gender") ,
                intent.getStringExtra("address"),
                intent.getStringExtra("department") ,
                intent.getStringExtra("startDate") ,
                intent.getStringExtra("salary"));
    }
}