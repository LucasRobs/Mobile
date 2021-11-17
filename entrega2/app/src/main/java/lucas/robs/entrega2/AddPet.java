package lucas.robs.entrega2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddPet extends AppCompatActivity {
    String iconSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        setEditPet();
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_dog:
                if (checked)
                    iconSelected = "🐶";
                break;
            case R.id.radio_cat:
                if (checked)
                    iconSelected = "🐱";
                break;
            case R.id.radio_frog:
                if (checked)
                    iconSelected = "🐸";
                break;
            case R.id.radio_monkey:
                if (checked)
                    iconSelected = "🐵";
                break;
        }
    }

    public void pushHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void submit(View view){
        Pet pet = createPet();
        ArrayList<Pet> pets = getIntentPets();
        pets.add(pet);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.PETS, pets);
        startActivity(intent);
    }

    public ArrayList<Pet> getIntentPets(){
        ArrayList<Pet> pets = (ArrayList<Pet>) getIntent().getSerializableExtra(MainActivity.PETS);
        return pets;
    }

    public Pet createPet(){
        EditText editText = (EditText) findViewById(R.id.editTextName);
        String name = editText.getText().toString();

        editText = (EditText) findViewById(R.id.editTextAge);
        int age = Integer.parseInt(editText.getText().toString());

        editText = (EditText) findViewById(R.id.editTextRace);
        String race = editText.getText().toString();

        Pet pet = new Pet(name,age,race,iconSelected);
        return pet;
    }



    public void setEditPet(){
        Pet pet = getIntentPetSelect();
        if(pet != null){
            EditText editText = (EditText) findViewById(R.id.editTextName);
            editText.setText(pet.name);

            editText = (EditText) findViewById(R.id.editTextAge);
            editText.setText(pet.age);

            editText = (EditText) findViewById(R.id.editTextRace);
            editText.setText(pet.race);

            iconSelected = pet.icone;

            Button button = (Button) findViewById(R.id.button2);
            button.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onClick(View view) {
                    submitEdit(view);
                }
            });
        }
    }

    public void submitEdit(View view){
        Pet newPet = createPet();
        ArrayList<Pet> pets = getIntentPets();
        Pet pet = getIntentPetSelect();
        int IndexInIntent = pets.indexOf(pet);
        pets.set(IndexInIntent, newPet);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.PETS, pets);
        startActivity(intent);
    }

    public Pet getIntentPetSelect(){
        return (Pet) getIntent().getSerializableExtra(MainActivity.PETS_SELECT);
    }
}