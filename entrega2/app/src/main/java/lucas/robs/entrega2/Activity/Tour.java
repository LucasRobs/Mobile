package lucas.robs.entrega2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import lucas.robs.entrega2.R;
import lucas.robs.entrega2.Walker;


public class Tour extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
    }

    public void pushChat(View view) {
        Intent intent = new Intent(view.getContext(), Chat.class);
        view.getContext().startActivity(intent);
    }
}