package lucas.robs.helloworldandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.maps.MapView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "lucas.robs.helloworldandroid.MESSAGE";
    public ArrayList<String> nomes = new ArrayList<String>();
    MyFragmentPagerAdapter myFragmentPagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLongClick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.google_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.bliu: {
                Toast.makeText(this,"BLIU",Toast.LENGTH_SHORT).show();
                break;
            }
            // case blocks for other MenuItems (if any)
        }
        return true;
    }

    /** Called when the user taps the Send button */

    public void pushQuest3(View view) {
        Intent intent = new Intent(this, Quest4.class);
        startActivity(intent);
    }

    public void pushQuest5(View view) {
        Intent intent = new Intent(this, Quest5.class);
        startActivity(intent);
    }

    public void pushQuest12(View view) {
        Intent intent = new Intent(this, Quest12.class);
        startActivity(intent);
    }
    public void pushQuest13(View view) {
        Intent intent = new Intent(this, Quest13.class);
        startActivity(intent);
    }
    public void pushQuest16(View view) {
        Intent intent = new Intent(this, Quest16.class);
        startActivity(intent);
    }
    public void sendMessage(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        String message = editText.getText().toString();
        editText.setText("");
        nomes.add(message);
        TextView textView = findViewById(R.id.textView2);
        textView.setText(nomes.toString());
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_pirates:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radio_ninjas:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }

    public void onClickPopUp(View view) {
        Button button = (Button) findViewById(R.id.buttonPopUp);
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(MainActivity.this, button);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.poupup_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(
                        MainActivity.this,
                        "You Clicked : " + item.getTitle(),
                        Toast.LENGTH_SHORT
                ).show();
                return true;
            }
        });

        popup.show(); //showing popup menu
    }
    public void initLongClick(){
        TextView txtView = (TextView) findViewById(R.id.button4);
        txtView.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(),
                        "long press :)", 2000).show();
                return true;
            }
        });
        txtView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "Not long press :(",
                        1000).show();
            }
        });
    }

    public void soltaSomDj(View vie){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.ratinhooo);
        mediaPlayer.start();
    }

}