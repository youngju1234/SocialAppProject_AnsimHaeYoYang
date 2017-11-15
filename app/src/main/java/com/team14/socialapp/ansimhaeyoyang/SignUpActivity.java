package com.team14.socialapp.ansimhaeyoyang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team14.socialapp.ansimhaeyoyang.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {


    @BindView(R.id.editTextEmail)
    EditText editTextEmail;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.editTextCheckPassword)
    EditText editTextCheckPassword;
    @BindView(R.id.editTextUserName)
    EditText editTextUserName;
    @BindView(R.id.editTextPatientName)
    EditText editTextPatientName;
    @BindView(R.id.editTextPatientBirth)
    EditText editTextPatientBirth;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    private User userInfo;
    private String email;
    private String password;
    private String checkPassword;
    private String userName;
    private String patientName;
    private String patientBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }


    @OnClick(R.id.buttonSignup)
    public void onClickSignUpButton(){

        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        checkPassword = editTextCheckPassword.getText().toString().trim();
        userName = editTextUserName.getText().toString().trim();
        patientName = editTextPatientName.getText().toString().trim();
        patientBirth = editTextPatientBirth.getText().toString().trim();

        if(!validateForm()){
            return;
        }

        userInfo = new User(null,email,userName,patientName,patientBirth);

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(SignUpActivity.this, "SignUp Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private boolean validateForm() {
        boolean result = true;

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            result = false;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            result = false;
        }
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            result = false;
        }
        if(TextUtils.isEmpty(patientName)){
            Toast.makeText(this, "요양원 입주자의 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            result = false;
        }
        if(TextUtils.isEmpty(patientBirth)){
            Toast.makeText(this, "요양원 입주자의 생년월일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            result = false;
        }
        if(TextUtils.isEmpty(checkPassword)){
            Toast.makeText(this, "비밀번호 확인란을 입력해주세요.", Toast.LENGTH_SHORT).show();
            result = false;
        }

        if(password.length()<6){
            Toast.makeText(this, "6자리 이상의 비밀번호로 설정해주세요.", Toast.LENGTH_SHORT).show();
            result = false;
        }

        if(patientBirth.length()!=6){
            Toast.makeText(this, "생년월일은 YYMMDD 형태로 입력해주세요.", Toast.LENGTH_SHORT).show();
            result = false;
        }

        if(!password.equals(checkPassword)){
            Toast.makeText(this, "비밀번호가 일치하지 않습니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
            result = false;
        }


        return result;
    }


    private void onAuthSuccess(FirebaseUser user) {

        userInfo.setUserUID(user.getUid());

        mDatabase.child("users").child(user.getUid()).setValue(userInfo);

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }


}
