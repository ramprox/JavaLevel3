package ru.ramil.homeworkLesson1.model;

import ru.ramil.homeworkLesson1.interfaces.StorageInBox;

import java.util.*;

public class Box<T extends Fruit & StorageInBox> {

    private final List<T> list = new ArrayList<>();

    public void add(T t) {
        list.add(t);
    }

    public void addAll(List<T> list) {
        this.list.addAll(list);
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
        otherBox.addAll(list);
        list.clear();
    }
}
