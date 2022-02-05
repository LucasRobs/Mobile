package lucas.robs.entrega2.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import lucas.robs.entrega2.Contact;
import lucas.robs.entrega2.Message;
import lucas.robs.entrega2.Pet;
import lucas.robs.entrega2.PetAdapter;
import lucas.robs.entrega2.R;
import lucas.robs.entrega2.Walker;
import lucas.robs.entrega2.WalkerAdapter.ViewHolderWalker;

public class Chat extends AppCompatActivity {
    private GroupAdapter adapter;
    private FirebaseUser user;
    private EditText editChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        user = FirebaseAuth.getInstance().getCurrentUser();
        editChat = findViewById(R.id.edit_chat);
        setListMenssages();
    }

    public void setListMenssages() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_chat);
        adapter = new GroupAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("chat");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Gson gson = new Gson();
                if (snapshot.getValue() != null) {
                    adapter.clear();
                    for (DataSnapshot entity : snapshot.getChildren()) {
                            Message msg = gson.fromJson(entity.getValue().toString(), Message.class);
                            adapter.add(new MessageItem(msg));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase", "Error getting data" + error);
            }
        };
        mDatabase.addValueEventListener(postListener);
/*
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()) {

                    Gson gson = new Gson();
                    if (task.getResult().getValue() != null) {

                        DataSnapshot snapshot = task.getResult();
                        for (DataSnapshot entity : snapshot.getChildren()) {
                            Message msg = gson.fromJson(entity.getValue().toString(), Message.class);

                            adapter.add(new MessageItem(msg));
                        }

                    }
                } else {
                }
            }
        });
*/
    }

    public void sendMessage(View v) {
        String text = editChat.getText().toString();

        editChat.setText(null);

        final String fromId = FirebaseAuth.getInstance().getUid();
        final String toId = user.getUid();
        long timestamp = System.currentTimeMillis();

        final Message message = new Message();
        message.setFromId(fromId);
        message.setToId(toId);
        message.setTimestamp(timestamp);
        message.setText(text);

        if (!message.getText().isEmpty()) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(user.getUid() + "/chat");
            Gson gson = new Gson();
            mDatabase.push().setValue(gson.toJson(message));
        }
    }

    private class MessageItem extends Item<ViewHolder> {

        private final Message message;

        private MessageItem(Message message) {
            this.message = message;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            TextView txtMsg = viewHolder.itemView.findViewById(R.id.txt_msg);
            ImageView imgMessage = viewHolder.itemView.findViewById(R.id.img_message_user);

            txtMsg.setText(message.getText());

            Picasso.get()
                    .load(message.getFromId().equals(FirebaseAuth.getInstance().getUid())
                            ? user.getPhotoUrl()
                            : user.getPhotoUrl())
                    .into(imgMessage);
        }

        @Override
        public int getLayout() {
            return message.getFromId().equals(FirebaseAuth.getInstance().getUid())
                    ? R.layout.item_to_message
                    : R.layout.item_from_message;
        }
    }

    public Walker getIntentWalkerSelector() {
        return (Walker) getIntent().getSerializableExtra(MainActivity.CHAT_SELECT);
    }
}