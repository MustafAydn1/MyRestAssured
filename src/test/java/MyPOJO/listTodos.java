package MyPOJO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class listTodos {
    private ArrayList<ArrayList<Todos>> arrayListTodos;
    private Todos [][] array;

    public ArrayList<ArrayList<Todos>> getListTodos() {
        return arrayListTodos;
    }

    public Todos[][] getArray() {
        return array;
    }

    public void setListTodos(ArrayList<ArrayList<Todos>> arrayListTodos) {
        this.arrayListTodos = arrayListTodos;
    }

    public void setArray(Todos[][] array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return "listTodos{" +
                "arrayListTodos=" + arrayListTodos +
                ", array=" + Arrays.toString(array) +
                '}';
    }
}
