package lucas.robs.entrega2;

import java.io.Serializable;

public class Pet implements Serializable {
    String id;
    String name;
    int age;
    String race;
    String icone;

    public Pet(String name, int age, String race, String icone) {
        this.name = name;
        this.age = age;
        this.race = race;
        this.icone = icone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return System.getProperty("line.separator")+"{Icone: "+icone +" Nome: "+ name +", Idade: " + age +", Raça: " + race+"}";
    }
}
