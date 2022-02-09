package lucas.robs.entrega2.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import lucas.robs.entrega2.InfoUser;
import lucas.robs.entrega2.R;


public class Tour extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ID_CHAT = "INTENT_ID_CHAT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        setInforUser();
    }

    public void setInforUser() {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        String idUser = getIntentIdUserOwner();
        mDatabase.getReference("/users/" + idUser + "/infoUser").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Gson gson = new Gson();
                    if (task.getResult().getValue() != null) {
                        DataSnapshot snapshot = task.getResult();
                        InfoUser user = gson.fromJson(snapshot.getValue().toString(), InfoUser.class);
                        TextView nome = findViewById(R.id.nome);
                        TextView email = findViewById(R.id.email);
                        nome.setText("Amigo: " + user.getName());
                        email.setText(user.getEmail());
                    }
                } else {
                    Log.e("firebase", "Error getting data", task.getException());
                }
            }
        });
    }

    public void pushChat(View view) {
        Intent intent = new Intent(view.getContext(), Chat.class);
        intent.putExtra(ID_CHAT,getIntentIdUserOwner()+"-"+getIntentIdUserFriend());
        view.getContext().startActivity(intent);
    }

    public String getIntentIdUserOwner() {
        return getIntent().getStringExtra(MainActivityPetFriend.ID_PET_OWNER);
    }
    public String getIntentIdUserFriend() {
        return getIntent().getStringExtra(MainActivityPetFriend.ID_PET_FRIEND);
    }

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView img = findViewById(R.id.imageView3);
            img.setImageBitmap(imageBitmap);
        }
    }
}