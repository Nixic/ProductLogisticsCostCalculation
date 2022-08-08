package org.example.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ArrayUtil {

    /**
     *
     * @param list original list
     * @param sublistLength length of sublist
     * @param <T> type
     * @return list of sublist
     */
    public <T> List<List<T>> divideListToSubLists(List<T> list, final int sublistLength) {
        List<List<T>> parts = new ArrayList<>();
        final int N = list.size();
        for (int i = 0; i < N; i += sublistLength) {
            parts.add(new ArrayList<>(
                    list.subList(i, Math.min(N, i + sublistLength)))
            );
        }
        return parts;
    }

}
