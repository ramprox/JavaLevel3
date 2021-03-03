package ru.ramil.homeworkLesson1.model;

import ru.ramil.homeworkLesson1.interfaces.StorageInBox;

public class Orange extends Fruit implements StorageInBox {
    @Override
    public float getWeight() {
        return 1.5f;
    }
}
