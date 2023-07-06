package com.lamvo.groupproject_flowershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.lamvo.groupproject_flowershop.apis.CustomerRepository;
import com.lamvo.groupproject_flowershop.apis.CustomerService;
import com.lamvo.groupproject_flowershop.models.Customer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth auth;
    private CustomerService customerService;
    EditText etEmail, etPassword, etConfirmPassword;
    Button btnRegister;
    TextView txtLogin;
    boolean isSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmail = findViewById(R.id.etEmailRegister);
        etPassword = findViewById(R.id.etPasswordRegister);
        etConfirmPassword = findViewById(R.id.etPasswordConfirm);
        btnRegister = findViewById(R.id.btnSignup);
        txtLogin = findViewById(R.id.textViewLogin);
        auth = FirebaseAuth.getInstance();
        customerService = CustomerRepository.getCustomerService();

        txtLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == txtLogin.getId()) {
            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
        }
        if (v.getId() == btnRegister.getId()) {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString();
            String confirmPass = etConfirmPassword.getText().toString();
            if (!ValidateData(email, password, confirmPass)) {
                return;
            }
            isSuccess = true;
            // create new user account in firebase
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // create new user account in database
                                Customer customer = new Customer(email);
                                RegisterAccount(customer);
                            } else {
                                isSuccess = false;
                                Toast.makeText(SignUpActivity.this,
                                        "Register failed! " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    private boolean ValidateData(String email, String password, String confirmPass) {
        boolean flag = true;

        if (email.isEmpty()) {
            etEmail.setError("Email can not be empty!");
            flag = false;
        }
        if (password.isEmpty()) {
            etPassword.setError("Password can not be empty!");
            flag = false;
        }
        if (confirmPass.isEmpty()) {
            etConfirmPassword.setError("Confirm password can not be empty!");
            flag = false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Email wrong format!");
            flag = false;
        }
        if (!confirmPass.equals(password)) {
            etConfirmPassword.setError("Confirm password does not match password!");
            flag = false;
        }
        return flag;
    }

    private void RegisterAccount(Customer customer) {
        try {
            Call<Customer> call = customerService.createCustomer(customer);
            call.enqueue(new Callback<Customer>() {
                @Override
                public void onResponse(Call<Customer> call, Response<Customer> response) {
                    Toast.makeText(SignUpActivity.this, "Register successfully!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                }
                @Override
                public void onFailure(Call<Customer> call, Throwable t) {
                    isSuccess = false;
                    Toast.makeText(SignUpActivity.this, "Register failed! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Log.d("Register failed", ex.getMessage());
        }
    }
}