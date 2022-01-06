package lucas.robs.entrega2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String PETS = "INTENT_PETS";
    public static final String PETS_SELECT = "INTENT_PETS_SELECT";

    ArrayList<Pet> pets = new ArrayList<>();
    private PetAdapter petAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setList();
    }

    public void setList() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(user.getUid()).child("pets").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Gson gson = new Gson();
                    if (task.getResult().getValue() != null) {
                        DataSnapshot snapshot = task.getResult();
                        for (DataSnapshot entity : snapshot.getChildren()) {
                            Pet pet = gson.fromJson(entity.getValue().toString(), Pet.class);
                            pet.setId(entity.getKey());

                            pets.add(pet);
                        }
                        petAdapter = new PetAdapter(pets);
                        recyclerView.setAdapter(petAdapter);
                        //Log.i("firebase", pets.toString());
                    }
                } else {
                    Log.e("firebase", "Error getting data", task.getException());
                }
            }
        });

    }

    public void pushAddPet(View view) {
        Intent intent = new Intent(this, AddPet.class);
        intent.putExtra(MainActivity.PETS, pets);
        startActivity(intent);
    }

    public ArrayList<Pet> getIntentPets() {
        return (ArrayList<Pet>) getIntent().getSerializableExtra(MainActivity.PETS);
    }
}