package com.gertoxq.minedom.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Utility methods
 */
public class Util {
    /**
     * Returns the elements that exist in the old list, but don't exist in the new one.
     * @param oldList List 1
     * @param newList List 2
     * @return Removed element list
     */
    public static <T> List<T> getRemovedElements(List<T> oldList, List<T> newList) {
        return oldList.stream().filter(t -> !newList.contains(t)).collect(Collectors.toList());
    }

    /**
     * Returns the elements that exist in the new list, but don't exist in the old one.
     * @param oldList List 1
     * @param newList List 2
     * @return New element list
     */
    public static <T> List<T> getNewElements(List<T> oldList, List<T> newList) {
        return newList.stream().filter(t -> !oldList.contains(t)).collect(Collectors.toList());
    }
}
