package ru.ramil.homeworkLesson1.service;

import ru.ramil.homeworkLesson1.interfaces.StorageInBox;
import ru.ramil.homeworkLesson1.model.Fruit;
import java.util.*;

public class Box<T extends Fruit & StorageInBox> {

    private final List<T> list = new ArrayList<>();

    public void add(T t) {
        list.add(t);
    }

    public float getWeight() {
        float result = 0.0f;
        for(T t : list) {
            result += t.getWeight();
        }
        return result;
    }

    public boolean compare(Box<?> otherBox) {
        if(otherBox == null) {
            return false;
        }
        if(otherBox == this) {
            return true;
        }
        return getWeight() == otherBox.getWeight();
    }

    public void transferAllTo(Box<T> otherBox) {
        if(this == otherBox || otherBox == null) {
            return;
        }
        for(T fruit : list) {
            otherBox.add(fruit);
        }
        list.clear();
    }
}
