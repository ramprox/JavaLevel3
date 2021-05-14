package ru.ramil.homeworkLesson1;
import java.util.ArrayList;
import java.util.Arrays;

public class TestGenerics {
    public static void main(String[] args) {
        Integer[] arr = new Integer[] { 1, 2, 3 };
        System.out.println("Содержимое массива Integer[] : " + Arrays.toString(arr));
        swap(arr, 0, 2);
        System.out.println("Содержимое массива Integer[] после замены местами элементов 0 и 2 : " + Arrays.toString(arr));
        ArrayList<Integer> arrInt = toArrayList(arr);
        System.out.println("Содержимое объекта ArrayList<Integer> : " + arrInt);
    }

    /**
     * Метод меняющий местами элементы массива
     * @param arr - массив, в котором нужно поменять элементы
     * @param index1 - индекс первого элемента
     * @param index2 - индекс второго элемента
     * @param <T> - параметр, обозначающий тип элементов массива
     */
    private static <T> void swap(T[] arr, int index1, int index2) {
        T tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }

    /**
     * Метод для преобразования массива элементов ссылочного типа T[] в тип ArrayList<T>
     * @param arr - исходный массив
     * @param <T> - параметр, обозначающий тип элементов массива
     * @return объект ArrayList<T>, содержащий элементы исходного массива.
     *         Если arr имеет значение null или его длина равна 0, то
     *         возвращается объект ArrayList<T> с нулевым размером.
     */
    private static <T> ArrayList<T> toArrayList(T[] arr) {
        ArrayList<T> result = new ArrayList<>(arr == null ? 0 : arr.length);
        for(T element : arr) {
            result.add(element);
        }
        return result;
    }
}
