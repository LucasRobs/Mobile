package lucas.robs.helloworldandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Quest13 extends AppCompatActivity {
    String[] dados = new String[] { "Bliu1", "Bliu2", "Bliu3"};
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dados);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest13);
        init();
    }
    public void init(){
        ListView listview = (ListView) findViewById(R.id.list);
        listview.setAdapter(adapter);
    }

}