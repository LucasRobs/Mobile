package lucas.robs.entrega2.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import lucas.robs.entrega2.R;
import lucas.robs.entrega2.WalkerAdapter.ViewHolderWalker;

public class Chat extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }
    /*private FirebaseUser user;
    private EditText editChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        user = FirebaseAuth.getInstance().getCurrentUser();
        RecyclerView rv = findViewById(R.id.recycler_chat);
        editChat = findViewById(R.id.edit_chat);
        Button btnChat = findViewById(R.id.btn_chat);

        btnChat.setOnClickListener(new View().OnClickListener() {
            @Override
            public void onClick (View v){
                sendMessage();
            }
        });

    }

    private void sendMessage() {
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
            mDatabase.push().setValue(gson.toJson(message))
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("Teste", documentReference.getId());

                            Contact contact = new Contact();
                            contact.setUuid(toId);
                            contact.setUsername(user.getUsername());
                            contact.setPhotoUrl(user.getProfileUrl());
                            contact.setTimestamp(message.getTimestamp());
                            contact.setLastMessage(message.getText());

                            FirebaseFirestore.getInstance().collection("/last-messages")
                                    .document(fromId)
                                    .collection("contacts")
                                    .document(toId)
                                    .set(contact);

                            if (!user.isOnline()) {
                                Notification notification = new Notification();
                                notification.setFromId(message.getFromId());
                                notification.setToId(message.getToId());
                                notification.setTimestamp(message.getTimestamp());
                                notification.setText(message.getText());
                                notification.setFromName(me.getUsername());

                                FirebaseFirestore.getInstance().collection("/notifications")
                                        .document(user.getToken())
                                        .set(notification);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Teste", e.getMessage(), e);
                        }
                    });

            FirebaseFirestore.getInstance().collection("/conversations")
                    .document(toId)
                    .collection(fromId)
                    .add(message)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("Teste", documentReference.getId());

                            Contact contact = new Contact();
                            contact.setUuid(toId);
                            contact.setUsername(me.getUsername());
                            contact.setPhotoUrl(me.getProfileUrl());
                            contact.setTimestamp(message.getTimestamp());
                            contact.setLastMessage(message.getText());

                            FirebaseFirestore.getInstance().collection("/last-messages")
                                    .document(toId)
                                    .collection("contacts")
                                    .document(fromId)
                                    .set(contact);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Teste", e.getMessage(), e);
                        }
                    });
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
                    ? R.layout.item_from_message
                    : R.layout.item_to_message;
        }
    }

    public Walker getIntentWalkerSelector() {
        return (Walker) getIntent().getSerializableExtra(MainActivity.CHAT_SELECT);
    }*/
}