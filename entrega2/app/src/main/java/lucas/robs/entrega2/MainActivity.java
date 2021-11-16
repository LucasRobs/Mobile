package lucas.robs.entrega2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String PETS = "INTENT_PETS";
    ArrayList<Pet> pets = new ArrayList<>();
    private PetAdapter petAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setList();

    }

    public void setList(){
        if(getIntentPets() != null) {
            pets = getIntentPets();
            petAdapter = new PetAdapter(pets);
        }else{
            Pet pet = new Pet("a",1,"a","🐸");
            pets.add(pet);
            petAdapter = new PetAdapter(pets);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(petAdapter );
    }

    public void pushAddPet(View view) {
        Intent intent = new Intent(this, AddPet.class);
        intent.putExtra(MainActivity.PETS, pets);
        startActivity(intent);
    }

    public ArrayList<Pet> getIntentPets(){
        return (ArrayList<Pet>) getIntent().getSerializableExtra(MainActivity.PETS);
    }
}