package org.example.ultil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataUtil {
    public static List<Integer> generateRandomList(int n) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        return numbers;
    }
}
