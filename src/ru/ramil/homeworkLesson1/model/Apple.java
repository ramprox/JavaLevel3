package ru.ramil.homeworkLesson1.model;

import ru.ramil.homeworkLesson1.interfaces.StorageInBox;

public class Apple extends Fruit implements StorageInBox {
    @Override
    public float getWeight() {
        return 1.0f;
    }
}
