package com.example.employeesmanagment.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.employeesmanagment.data.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDb extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "m_employee_database";

    public EmployeeDb(@Nullable Context context) {
        super(context, DB_NAME, null , DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE my_employee_table (id INTEGER PRIMARY KEY AUTOINCREMENT, firstName TEXT, secondName TEXT, email TEXT," +
                " phoneNumber TEXT , gender TEXT , address TEXT , department TEXT , startDate TEXT , salary TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS my_employee_table");
        onCreate(sqLiteDatabase);
    }

    public boolean addEmployee(Employee employee){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("firstName" , employee.getFirstName());
        contentValues.put("secondName" , employee.getSecondName());
        contentValues.put("email" , employee.getEmail());
        contentValues.put("phoneNumber" , employee.getPhoneNumber());
        contentValues.put("gender" , employee.getGender());
        contentValues.put("address" , employee.getAddress());
        contentValues.put("department" , employee.getDepartment());
        contentValues.put("startDate" , employee.getStartDate());
        contentValues.put("salary" , employee.getSalary());


        boolean isAddedSuccessfully = sqLiteDatabase.insert("my_employee_table" , null , contentValues) != -1;
        sqLiteDatabase.close();

        return isAddedSuccessfully;
    }

    public ArrayList<Employee> getAllEmployee(){
        ArrayList<Employee> employeeList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM my_employee_table" , null );
        if (cursor.moveToFirst()){
            do{
                employeeList.add(new Employee(cursor.getInt(0) , cursor.getString(1) , cursor.getString(2), cursor.getString(3) ,
                        cursor.getString(4),cursor.getString(5), cursor.getString(6), cursor.getString(7) , cursor.getString(8),cursor.getString(9) ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return employeeList;
    }

    public ArrayList<Employee> getAllEmployeeBySearch(String input){
        ArrayList<Employee> employeeList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM my_employee_table WHERE firstName = ? OR secondName = ? OR email = ?" , new String[]{input , input , input} );
        if (cursor.moveToFirst()){
            do{
                employeeList.add(new Employee(cursor.getInt(0) , cursor.getString(1) , cursor.getString(2), cursor.getString(3) ,
                        cursor.getString(4),cursor.getString(5), cursor.getString(6), cursor.getString(7) , cursor.getString(8),cursor.getString(9) ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return employeeList;
    }

    public void deleteEmployeeById(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
         sqLiteDatabase.delete("my_employee_table" , "id = ?" , new String[] {String.valueOf(id)});
         sqLiteDatabase.close();
    }

    public void updateEmployeeById(Employee employee){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("firstName" , employee.getFirstName());
        contentValues.put("secondName" , employee.getSecondName());
        contentValues.put("email" , employee.getEmail());
        contentValues.put("phoneNumber" , employee.getPhoneNumber());
        contentValues.put("gender" , employee.getGender());
        contentValues.put("address" , employee.getAddress());
        contentValues.put("department" , employee.getDepartment());
        contentValues.put("startDate" , employee.getStartDate());
        contentValues.put("salary" , employee.getSalary());
        sqLiteDatabase.update("my_employee_table" , contentValues , "salary = ?" , new String[] {employee.getSalary()});

//        String sqlStr = "UPDATE my_employee_table SET " +
//                "id = " + "'"+ employee.getId() + "'" +
//                " , firstName = " + "'"+ employee.getFirstName() + "'" +
//                " , " + "secondName = " + "'" + employee.getSecondName() +"'" +
//                " , " + " email = " + "'" + employee.getEmail() + "'" +
//                " , " + "phoneNumber = " + "'"+ employee.getPhoneNumber() + "'" +
//                " , " + "gender = " + "'"+ employee.getGender() +"'" +
//                " , " + "address = " + "'"+ employee.getAddress() +"'" +
//                " , " + "department = " +"'"+ employee.getDepartment() +"'" +
//                " , " + "startDate = " + "'"+ employee.getStartDate() +"'" +
//                " , " + "salary = " + "'"+ employee.getSalary() +"'" +
//                " WHERE salary = " + "'"+ employee.getSalary() +"'" ;
//        sqLiteDatabase.execSQL(sqlStr);
    }
}
