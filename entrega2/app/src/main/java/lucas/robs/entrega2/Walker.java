package lucas.robs.entrega2;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class Walker implements Serializable{
    public int id;
    public String name;

    public Walker(int id, String nome) {
        this.id = id;
        this.name = nome;
    }
}
