package ru.ramil.homeworkLesson1;


import ru.ramil.homeworkLesson1.model.Apple;
import ru.ramil.homeworkLesson1.model.Orange;
import ru.ramil.homeworkLesson1.service.Box;

public class TestBox {
    public static void main(String[] args) {
        Box<Apple> appleBox1 = new Box<>();
        for(int i = 0; i < 10; i++) {
            appleBox1.add(new Apple());
        }

        Box<Orange> orangeBox = new Box<>();
        for(int i = 0; i < 20; i++) {
            orangeBox.add(new Orange());
        }

        System.out.println("Вес коробки 1 с яблоками " + appleBox1.getWeight());
        System.out.println("Вес коробки 2 с апельсинами " + orangeBox.getWeight());
        if(appleBox1.compare(orangeBox)) {
            System.out.println("Коробка 1 с яблоками имеет тот же вес что и коробка 2 с апельсинами");
        } else {
            System.out.println("Вес коробки 1 с яблоками отличается от веса коробки 2 с апельсинами");
        }
        System.out.println();

        Box<Apple> appleBox3 = new Box<>();
        for(int i = 0; i < 20; i++) {
            appleBox1.add(new Apple());
        }
        System.out.println("Вес коробки 3 с яблоками " + appleBox3.getWeight());
        System.out.println("Пересыпаем яблоки из коробки 1 в коробку 3 для яблок");
        appleBox1.transferAllTo(appleBox3);

        System.out.println("Вес коробки 1 с яблоками " + appleBox1.getWeight());
        System.out.println("Вес коробки 3 с яблоками " + appleBox3.getWeight());
        System.out.println("Вес коробки 2 с апельсинами " + orangeBox.getWeight());
        if(appleBox3.compare(orangeBox)) {
            System.out.println("Коробка 3 с яблоками имеет тот же вес что и коробка 2 с апельсинами");
        } else {
            System.out.println("Вес коробки 3 с яблоками отличается от веса коробки 2 с апельсинами");
        }
    }
}
