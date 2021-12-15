package lucas.robs.entrega2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String PETS = "INTENT_PETS";
    public static final String PETS_SELECT = "INTENT_PETS_SELECT";

    ArrayList<Pet> pets = new ArrayList<>();
    private PetAdapter petAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setList();
    }

    public void setList(){
        if(getIntentPets() != null) {
            pets = getIntentPets();
            petAdapter = new PetAdapter(pets);
        }/*else{
            Pet pet = new Pet("Tyler",2,"Border Collie","🐶");
            pets.add(pet);
            pet = new Pet("Lilih",1,"Chartreux","🐱");
            pets.add(pet);
            pet = new Pet("Emma",4,"Angorá","🐱");
            pets.add(pet);
            pet = new Pet("Dirlândia",21,"Gata","\uD83D\uDC69\uD83C\uDFFC\u200D\uD83E\uDDB1");
            pets.add(pet);
            pet = new Pet("Marcelo",24,"Humano","\uD83D\uDC68\uD83C\uDFFB\u200D\uD83D\uDCBB");
            pets.add(pet);
            pet = new Pet("Pianko",21,"Humano","\uD83D\uDC35");
            pets.add(pet);
            pet = new Pet("Yago",21,"Humano","\uD83D\uDE48");
            pets.add(pet);
            pet = new Pet("Jaime",21,"Humano","\uD83D\uDE49");
            pets.add(pet);
            pet = new Pet("Davi",24,"Humano","\uD83D\uDE4A");
            pets.add(pet);
            pet = new Pet("Lucca",23,"Humano","\uD83D\uDC12");
            pets.add(pet);
            pet = new Pet("Caio",22,"Humano","\uD83D\uDD7A\uD83C\uDFFB");
            pets.add(pet);
            pet = new Pet("Uzumake",20,"Humano","\uD83E\uDD11");
            pets.add(pet);
            pet = new Pet("Baitinga",20,"Humano","\uD83E\uDD20");
            pets.add(pet);
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(MainActivity.PETS, pets);
            startActivity(intent);
        }*/
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