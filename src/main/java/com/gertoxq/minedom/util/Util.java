package com.gertoxq.minedom.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Util {
    public static <T> List<T> getRemovedElements(List<T> oldList, List<T> newList) {
        return oldList.stream().filter(t -> !newList.contains(t)).collect(Collectors.toList());
    }
    public static <T> List<T> getNewElements(List<T> oldList, List<T> newList) {
        return newList.stream().filter(t -> !oldList.contains(t)).collect(Collectors.toList());
    }
}
