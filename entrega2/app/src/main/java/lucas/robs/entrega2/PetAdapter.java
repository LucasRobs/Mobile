package lucas.robs.entrega2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolderPet> {
    public ArrayList<Pet> pets = new ArrayList<>();

    public PetAdapter(ArrayList<Pet> pets){
        this.pets = pets;
    }
    @NonNull
    @Override
    public PetAdapter.ViewHolderPet onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.pet_card, parent, false);
        ViewHolderPet holderPet = new ViewHolderPet(view);
        return holderPet;
    }

    @Override
    public void onBindViewHolder(@NonNull PetAdapter.ViewHolderPet holder, int position) {
        if(pets != null && pets.size() > 0){
            Pet pet = pets.get(position);
            holder.textIcon.setText(pet.icone);
            holder.textName.setText(pet.name);
            holder.textYear.setText(Integer.toString(pet.age)+" anos");
            holder.textRace.setText(pet.race);
            holder.dotsButton.setTag(position);
            holder.dotsButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onClick(View view ) {
                    onClickPopUp(view);
                }
            });
        }
        //System.out.println(pets.size());

    }

    public void onClickPopUp(View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.dotsButton);
        PopupMenu popup = new PopupMenu(imageView.getContext(), view);
        popup.getMenuInflater().inflate(R.menu.menu_actions, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle().equals("🗑️ Apagar")){
                    int index = (int) view.getTag();
                    pets.remove(index);
                    pushMainActivity(view);
                }else{
                    int index = (int) view.getTag();
                    pushEditPet(view, pets.get(index));
                }
                return true;
            }
        });
        popup.show();
    }
    @Override
    public int getItemCount() {
        return pets.size();
    }

    public class ViewHolderPet extends RecyclerView.ViewHolder{
        public TextView textIcon;
        public TextView textName;
        public TextView textYear;
        public TextView textRace;
        public ImageView dotsButton;

        public ViewHolderPet(@NonNull View itemView) {
            super(itemView);
            textIcon = (TextView) itemView.findViewById(R.id.textIcon);
            textName = (TextView) itemView.findViewById(R.id.textName);
            textYear = (TextView) itemView.findViewById(R.id.textYear);
            textRace = (TextView) itemView.findViewById(R.id.textRace);
            dotsButton = (ImageView) itemView.findViewById(R.id.dotsButton);
        }


    }

    public void pushMainActivity(View view) {
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        intent.putExtra(MainActivity.PETS, pets);
        view.getContext().startActivity(intent);
    }

    public void pushEditPet(View view, Pet pet) {
        Intent intent = new Intent(view.getContext(), AddPet.class);
        intent.putExtra(MainActivity.PETS_SELECT, pet);
        view.getContext().startActivity(intent);
    }
}
