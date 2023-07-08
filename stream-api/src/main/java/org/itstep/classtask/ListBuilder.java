package org.itstep.classtask;

import java.util.ArrayList;
import java.util.List;
/**
 * Практика на білдер
 * Due 9 July 2023 02:59
 * Instructions
 * Створити ListBuilder(), що дозволяє ініціалізувати список ArrayList.
 * Меод add() приймає об'єкт, метод build() повертає неузагальнений список.
 *
 * List list = new ListBuilder()
 *                 .add(1)
 *                 .add(2)
 *                 .add(3)
 *                 .build();
 * System.out.println(list); / [1, 2, 3]
 *
 * Додаткове завдання: зробити узагальнений клас ListBuilder()
*/

public class ListBuilder<T> {
    private final List<T> list;

    public ListBuilder() {
        this.list = new ArrayList<>();
    }

    public ListBuilder<T> add(T element) {
        list.add(element);
        return this;
    }

    public List<T> build() {
        return list;
    }

    public static class Builder<T> {
        private final ListBuilder<T> listBuilder;

        public Builder() {
            this.listBuilder = new ListBuilder<>();
        }

        public Builder<T> add(T element) {
            listBuilder.add(element);
            return this;
        }

        public List<T> build() {
            return listBuilder.build();
        }
    }

    public static void main(String[] args) {
        List<Integer> list = new ListBuilder.Builder<Integer>()
                .add(1)
                .add(2)
                .add(3)
                .build();
        System.out.println(list); // [1, 2, 3]
    }
}
