package com.myshopping.app;

import java.util.function.Supplier;

class Scenario {
    static <T> void thenDo(String message, Supplier<T> action) {
        System.out.print(" => " + message + " ... ");
        System.out.flush();

        try {
            T res = action.get();
            if (res != null) {
                System.out.println("résultat : " + String.valueOf(res));
            } else {
                System.out.println("fait !");
            }
        } catch (Exception e) {
            System.out.println("ÉCHEC !\n");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
