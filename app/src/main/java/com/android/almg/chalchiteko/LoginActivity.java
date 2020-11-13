package com.android.almg.chalchiteko;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.media.CamcorderProfile.get;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private static final String PROVEEDOR_DESCONOCIDO = "Proveedor Desconocido";
    private static final String PASSWORD_FIREBASE = "password";

    @BindView(R.id.imgPhotoProfile)
    ImageView imgPhotoProfile;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvProvider)
    TextView tvProvider;

    private Button btn;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btn = (Button) findViewById(R.id.btnLogin);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Acceder al Segundo Activity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Tomar datos del Intent hacia otra Activity
        Bundle bundle = getIntent().getExtras();

        // Instancias para el Inicio de Sesión
        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    onSetDataUser(user.getDisplayName(), user.getEmail(), user.zza() != null ?
                            user.zza().get(0) : PROVEEDOR_DESCONOCIDO);
                } else {
                    onSignedOutCleanup();

                    startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setTosUrl("https://www.facebook.com/pg/Comunidad-Ling%C3%BC%C3%ADstica-Chalchiteka-ALMG-102015078068501/community/")
                            .setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build()))
                            .build(), RC_SIGN_IN);
                }
            }
        };
    }

    private void onSignedOutCleanup() {
        onSetDataUser("","", "");
    }

    private void onSetDataUser(String userName, String email, String provider) {
        tvUserName.setText(userName);
        tvEmail.setText(email);

        int drawableRes;
        switch (provider){
            case PASSWORD_FIREBASE:
                drawableRes = R.drawable.ic_facebook;
            break;
            default:
                drawableRes = R.drawable.ic_block_helper;
                provider = PROVEEDOR_DESCONOCIDO;
                break;
        }
        tvProvider.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableRes, 0,0,0);
        tvProvider.setText(provider);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Bienvenido...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Intente de nuevo...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_sign_out:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}