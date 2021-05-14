package ru.ramil.homeworkLesson6.testing;

public class WorkWithArrays {
    public int[] getArrayAfterLastNumber(int[] array, int number) {
        int lastIndexOfNumber = -1;
        for(int i = array.length - 1; i >= 0; i--) {
            if(array[i] == number) {
                lastIndexOfNumber = i;
                break;
            }
        }
        if(lastIndexOfNumber == -1) {
            throw new RuntimeException("Исходный массив не содержит ни одного числа " + number);
        }
        int[] result = new int[array.length - 1 - lastIndexOfNumber];
        for(int i = lastIndexOfNumber + 1; i < array.length; i++) {
            result[i - lastIndexOfNumber - 1] = array[i];
        }
        return result;
    }

    public boolean isContainsNumbers(int[] array, int number1, int number2) {
        if(array == null) {
            return false;
        }
        boolean isContainsNumber1 = false;
        boolean isContainsNumber2 = false;
        for(int i = 0; i < array.length; i++) {
            if(array[i] != number1 && array[i] != number2) {
                return false;
            }
            if(array[i] == number1) {
                isContainsNumber1 = true;
            }
            if(array[i] == number2) {
                isContainsNumber2 = true;
            }
        }
        return isContainsNumber1 && isContainsNumber2;
    }
}
