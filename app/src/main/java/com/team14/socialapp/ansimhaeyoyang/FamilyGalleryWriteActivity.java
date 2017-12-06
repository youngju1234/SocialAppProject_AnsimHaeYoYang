package com.team14.socialapp.ansimhaeyoyang;

import android.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team14.socialapp.ansimhaeyoyang.model.GalleryItem;
import com.team14.socialapp.ansimhaeyoyang.model.User;
import com.team14.socialapp.ansimhaeyoyang.util.OnPermssionCallBackListener;
import com.team14.socialapp.ansimhaeyoyang.util.ProviderUtil;
import com.team14.socialapp.ansimhaeyoyang.util.RuntimeUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FamilyGalleryWriteActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseUser mUser;

    private static final int REQUEST_TAKE_PHOTO = 1; //카메라 촬영으로 사진 가져오기
    private static final int REQUEST_TAKE_ALBUM = 2; //앨범에서 사진 가져오기
    private Uri photoUri; //uri : 각각의 파일이 갖고 있는 경로

    @BindView(R.id.imageView_gallery_image)
    ImageView imageView;
    @BindView(R.id.edit_text_gallery_content)
    BootstrapEditText contentEditText;
    @BindView(R.id.write_button)
    BootstrapButton writeButton;

    private GalleryItem galleryItem;
    private User userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_gallery_write);
        ButterKnife.bind(this);
        setupActionBar();

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, SignInActivity.class));
        }

        mUser = firebaseAuth.getCurrentUser();

        mFirebaseDatabase.getReference()
                .child("users")
                .child(mUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userInfo = new User();
                        userInfo = dataSnapshot.getValue(User.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("보호자 소식 글쓰기");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent( FamilyGalleryWriteActivity.this,FamilyGalleryActivity.class));
                    finish();
                }
            });
        }
    }

    @OnClick(R.id.imageView_gallery_image)
    void clickImageView() {
        DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakePhotoAction();
            }
        };
        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakeAlbumAction();
            }
        };

        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("업로드할 이미지 선택")
                .setPositiveButton("사진촬영", cameraListener)
                .setNeutralButton("앨범선택", albumListener)
                .setNegativeButton("취소", cancelListener)
                .show();
    }

    /**
     * 카메라에서 사진 촬영
     */
    public void doTakePhotoAction(){

        RuntimeUtil.checkPermission(FamilyGalleryWriteActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, RuntimeUtil.PERMISSION_ALBUM, new OnPermssionCallBackListener() {
            @Override
            public void OnGrantPermission() {
                RuntimeUtil.checkPermission(FamilyGalleryWriteActivity.this, getWindow().getDecorView(), android.Manifest.permission.CAMERA, RuntimeUtil.PERMISSION_CAMERA, null, new OnPermssionCallBackListener() {
                    @Override
                    public void OnGrantPermission() {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            photoUri = ProviderUtil.getOutputMediaFileUri(getBaseContext());
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                            startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                        }
                    }
                });
            }
        });
    }

    /**
     * 앨범에서 이미지 가져오기
     */
    public void doTakeAlbumAction()
    {
        RuntimeUtil.checkPermission(FamilyGalleryWriteActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, RuntimeUtil.PERMISSION_ALBUM, new OnPermssionCallBackListener() {
            @Override
            public void OnGrantPermission() {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_TAKE_ALBUM);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RuntimeUtil.PERMISSION_CAMERA) {
            if (RuntimeUtil.verifyPermissions(FamilyGalleryWriteActivity.this, grantResults)) {
                doTakePhotoAction();
            }
        } else if (requestCode == RuntimeUtil.PERMISSION_ALBUM) {
            if (RuntimeUtil.verifyPermissions(FamilyGalleryWriteActivity.this, getWindow().getDecorView(), grantResults)) {
                doTakeAlbumAction();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_ALBUM) {
                photoUri = data.getData();
            }

            if (photoUri != null) {
                drawFile();
            }
        }
    }

    private void drawFile() {
        Bitmap bitmapImage;
        try {
            bitmapImage = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "IOException:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        showImage(bitmapImage);
    }

    private void showImage(Bitmap bitmap) {
        Drawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
        imageView.setImageDrawable(bitmapDrawable);
    }

    @OnClick(R.id.write_button)
    public void onClickWriteButton(){
        String content = contentEditText.getText().toString();
        String photoPath=null;
        if(photoUri!=null){
            photoPath=photoUri.toString();
        }

        if(content.isEmpty()||photoPath.isEmpty()){
            Toast.makeText(getBaseContext(), "정보를 모두 입력해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }else{
            galleryItem = new GalleryItem();
            galleryItem.setContent(content);
            galleryItem.setPhotoPath(photoPath);
            long time = System.currentTimeMillis();
            SimpleDateFormat dayTime = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
            galleryItem.setDate(dayTime.format(new Date(time)));
            galleryItem.setUser(userInfo);
            galleryItem.setTitle(userInfo.getPatientName()+"님의 가족");
            galleryItem.setWriter(userInfo.getUserName());

            mFirebaseDatabase.getReference("family_gallery")
                    .push()
                    .setValue(galleryItem)
                    .addOnSuccessListener(FamilyGalleryWriteActivity.this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getBaseContext(),"갤러리 글쓰기 완료", Toast.LENGTH_LONG).show();
                            startActivity(new Intent( FamilyGalleryWriteActivity.this,FamilyGalleryActivity.class));
                        }
                    });
        }
    }
}
