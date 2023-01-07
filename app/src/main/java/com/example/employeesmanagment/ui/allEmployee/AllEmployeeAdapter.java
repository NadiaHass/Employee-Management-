package com.example.employeesmanagment.ui.allEmployee;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.employeesmanagment.R;
import com.example.employeesmanagment.data.model.Employee;
import com.example.employeesmanagment.data.sqlite.EmployeeDb;
import com.example.employeesmanagment.ui.updateEmployee.UpdateEmployeeActivity;
import com.example.employeesmanagment.ui.viewEmployee.ViewEmployeeActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllEmployeeAdapter extends RecyclerView.Adapter<AllEmployeeAdapter.EmployeeViewHolder> {

    private Context context  ;
    private ArrayList<Employee> employeeList = new ArrayList<>();

    public AllEmployeeAdapter(Context context, ArrayList<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;


    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EmployeeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_rv_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        holder.nameTextView.setText(employeeList.get(position).getFirstName()+ " " + employeeList.get(position).getSecondName());
        holder.departmentTextView.setText(employeeList.get(position).getDepartment());
        holder.phoneNumberTextView.setText(employeeList.get(position).getPhoneNumber());

        holder.employeeLayout.setOnClickListener(view -> openEmployeeActivity(position));

        String gender = employeeList.get(position).getGender().toLowerCase();
        Glide.with(context)
                .load(context.getResources().getIdentifier(gender , "drawable" , context.getPackageName()))
                .into(holder.personImageView);

        holder.moreOptionsImageView.setOnClickListener(view -> {
            showPopup(holder , position , view);
        });

    }

    void showPopup(EmployeeViewHolder holder, int position, View view) {

            PopupMenu popup = new PopupMenu(context , view);
            popup.inflate(R.menu.more_option_menu);
            popup.show();

            popup.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()){
                    case R.id.ouvrir_item:{
                        openEmployeeActivity(position);
                        return true;}

                    case R.id.modifier_item:{
                        openUpdateActivity(employeeList.get(position));
                        return true;}

                    case R.id.supprimer_item:{
                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("Est ce que vous voulez vraiment supprimer l'employee ?");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                (dialog, which) ->  deleteEmployee(employeeList.get(position).getId() , position));
                        alertDialog.show();
                        return true;}

                    default:
                        return false;
                }
            });
    }

    private void openUpdateActivity(Employee employee) {
        Intent updateIntent = new Intent(context , UpdateEmployeeActivity.class);
        updateIntent.putExtra("id" , String.valueOf(employee.getId()));
        updateIntent.putExtra("firstName" , employee.getFirstName());
        updateIntent.putExtra("secondName" , employee.getSecondName());
        updateIntent.putExtra("email" , employee.getEmail());
        updateIntent.putExtra("phone" , employee.getPhoneNumber());
        updateIntent.putExtra("gender" , employee.getGender());
        updateIntent.putExtra("address" , employee.getAddress());
        updateIntent.putExtra("department" , employee.getDepartment());
        updateIntent.putExtra("startDate" , employee.getStartDate());
        updateIntent.putExtra("salary" , employee.getSalary());
        context.startActivity(updateIntent);

    }


    private void openEmployeeActivity(int position) {
        Intent intent = new Intent(context , ViewEmployeeActivity.class);
        intent.putExtra("id" , String.valueOf(employeeList.get(position).getId()));
        intent.putExtra("firstName" , employeeList.get(position).getFirstName());
        intent.putExtra("secondName" , employeeList.get(position).getSecondName());
        intent.putExtra("email" , employeeList.get(position).getEmail());
        intent.putExtra("phone" , employeeList.get(position).getPhoneNumber());
        intent.putExtra("gender" , employeeList.get(position).getGender());
        intent.putExtra("address" , employeeList.get(position).getAddress());
        intent.putExtra("department" , employeeList.get(position).getDepartment());
        intent.putExtra("startDate" , employeeList.get(position).getStartDate());
        intent.putExtra("salary" , employeeList.get(position).getSalary());

        context.startActivity(intent);
    }

    private void deleteEmployee(int id , int position ) {
        try {
            EmployeeDb employeeDb = new EmployeeDb(context);
            employeeDb.deleteEmployeeById(id);
            employeeList.remove(position);
            notifyItemChanged(position);
        }catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView , phoneNumberTextView , departmentTextView ;
        ImageView moreOptionsImageView ;
        CircleImageView personImageView;
        LinearLayout employeeLayout;
        EmployeeViewHolder(View itemView){
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_name);
            moreOptionsImageView = itemView.findViewById(R.id.iv_more_options);
            employeeLayout = itemView.findViewById(R.id.employee_item);
            phoneNumberTextView = itemView.findViewById(R.id.tv_phone);
            departmentTextView = itemView.findViewById(R.id.tv_department);
            personImageView = itemView.findViewById(R.id.iv_person);

        }
    }
}
