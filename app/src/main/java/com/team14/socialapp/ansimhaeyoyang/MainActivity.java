package com.team14.socialapp.ansimhaeyoyang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team14.socialapp.ansimhaeyoyang.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    @BindView(R.id.textView_main_user_name)
    TextView textViewUserName;
    @BindView(R.id.textView_main_patient_name)
    TextView textViewPatientName;
    @BindView(R.id.button_logout)
    Button buttonLogout;
    @BindView(R.id.button_menu_board)
    BootstrapButton buttonMenuBoard;
    @BindView(R.id.button_menu_gallery_patient)
    BootstrapButton buttonMenuGalleryPatient;
    @BindView(R.id.button_menu_gallery_family)
    BootstrapButton buttonMenuGalleryFamily;
    @BindView(R.id.button_menu_yoyang_program)
    BootstrapButton buttonMenuYoyangProgram;
    //@BindView(R.id.textviewDelete)
    //TextView textivewDelete;

    private String userUID;

    private User userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //butterknife
        ButterKnife.bind(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, SignInActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();


        mDatabase
                .child("users")
                .child(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userInfo = new User();
                        userInfo = dataSnapshot.getValue(User.class);
                        textViewPatientName.setText(userInfo.getPatientName()+" 님의");
                        textViewUserName.setText("보호자 "+userInfo.getUserName()+" 님");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    }


                });

        buttonLogout.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, SignInActivity.class));
        }

        /*
        //회원탈퇴를 클릭하면 회원정보를 삭제한다. 삭제전에 컨펌창을 하나 띄워야 겠다.
        if(view == textivewDelete) {
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity.this);
            alert_confirm.setMessage("정말 계정을 삭제 할까요?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(MainActivity.this, "계정이 삭제 되었습니다.", Toast.LENGTH_LONG).show();
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                                        }
                                    });
                        }
                    }
            );
            alert_confirm.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MainActivity.this, "취소", Toast.LENGTH_LONG).show();
                }
            });
            alert_confirm.show();
        }
        */
    }

    @OnClick(R.id.button_menu_board)
    public void onClickMenuBoard(){
        startActivity(new Intent(this, BoardActivity.class));
    }
    @OnClick(R.id.button_menu_gallery_patient)
    public void onClickMenuGalleryPatient(){

    }
    @OnClick(R.id.button_menu_gallery_family)
    public void onClickMenuGalleryFamily(){
        startActivity(new Intent(this, FamilyGalleryActivity.class));
    }
    @OnClick(R.id.button_menu_yoyang_program)
    public void onClickMenuYoyangProgram(){

    }
}
