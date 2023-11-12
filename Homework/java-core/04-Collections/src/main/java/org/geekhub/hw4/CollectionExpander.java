package org.geekhub.hw4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CollectionExpander implements Expander {
    @Override
    public double getMinValue(Collection<? extends Number> collection) {
        double minValue = Double.MAX_VALUE;

        if (collection.isEmpty()) {
            return minValue;
        } else {
            for (Number number : collection) {
                double value = number.doubleValue();
                if (value < minValue) {
                    minValue = value;
                }
            }
        }
        return minValue;
    }

    @Override
    public double getMaxValue(Collection<? extends Number> collection) {
        double maxValue = Double.MIN_VALUE;

        if (collection.isEmpty()) {
            return maxValue;
        } else {
            for (Number number : collection) {
                double value = number.doubleValue();
                if (value > maxValue) {
                    maxValue = value;
                }
            }
        }
        return maxValue;
    }

    @Override
    public double getSum(Collection<? extends Number> collection) {
        double sumOfNumbers = 0.0;

        if (!collection.isEmpty()) {
            for (Number number : collection) {
                sumOfNumbers += number.doubleValue();
            }
        }
        return sumOfNumbers;
    }

    @Override
    public String join(Collection<?> collection, char delimiter) {
        StringBuilder stringBuilder = new StringBuilder();

        if (collection.isEmpty()) {
            return "";
        }
        for (Object object : collection) {
            stringBuilder.append(object).append(delimiter);
        }
        int indexLastObj = stringBuilder.lastIndexOf(String.valueOf(delimiter));
        stringBuilder.deleteCharAt(indexLastObj);
        return stringBuilder.toString();
    }

    @Override
    public List<Double> reversed(List<? extends Number> collection) {
        List<Double> reverseCollection = new ArrayList<>();

        for (Number number : collection) {
            reverseCollection.add(number.doubleValue());
        }
        Collections.reverse(reverseCollection);
        return reverseCollection;
    }

    @Override
    public List<List<Object>> chunked(Collection<?> collection, int amount) {
        List<List<Object>> collectionPieces = new ArrayList<>();
        List<Object> chunk = new ArrayList<>(collection);

        for (int chunkIndex = 0; chunkIndex < amount; chunkIndex++) {
            List<Object> subList = new ArrayList<>();
            for (int elementIndex = chunkIndex; elementIndex < chunk.size(); elementIndex += amount) {
                subList.add(chunk.get(elementIndex));
            }
            collectionPieces.add(subList);
        }
        return collectionPieces;
    }

    @Override
    public List<?> dropElements(List<?> list, Object criteria) {
        Iterator<?> iterator = list.iterator();

        if (criteria instanceof Integer index) {
            list.remove(list.get(index));
        } else {
            while (iterator.hasNext()) {
                if (iterator.next().equals(criteria)) {
                    iterator.remove();
                }
            }
        }
        return list;
    }

    @Override
    public <T> List<T> getClassList(T t) {
        List<T> classes = new ArrayList<>();
        classes.add(t);
        return classes;
    }

    @Override
    public <T> List<T> removeDuplicatesAndNull(List<T> collection) {
        Set<T> uniqueElements = new LinkedHashSet<>(collection);
        uniqueElements.remove(null);
        return new ArrayList<>(uniqueElements);
    }

    @Override
    public <T> Map<T, Collection<T>> grouping(Collection<T> collection) {
        Map<T, Collection<T>> groupMap = new HashMap<>();

        for (T key : collection) {
            groupMap.putIfAbsent(key, new ArrayList<>());
            groupMap.get(key).add(key);
        }
        return groupMap;
    }

    @Override
    public <T, U> Map<T, U> merge(Map<T, U> map1, Map<T, U> map2) {
        Map<T, U> mergedMap = new HashMap<>(map1);
        mergedMap.putAll(map2);
        return mergedMap;
    }

    @Override
    public <T, U> Map<T, U> applyForNull(Map<T, U> map, U defaultValue) {

        for (Map.Entry<T, U> entry : map.entrySet()) {
            if (entry.getValue() == null) {
                map.replace(entry.getKey(), defaultValue);
            }
        }
        return map;
    }

    @Override
    public <T> Collection<T> collectingList(Map<T, T> map1, Map<T, T> map2) {
        Set<T> set = new HashSet<>();
        for (Map.Entry<T, T> entry2 : map2.entrySet()) {
            for (Map.Entry<T, T> entry1 : map1.entrySet()) {
                if (entry1.getKey().equals(entry2.getValue())) {
                    set.add(entry1.getKey());
                }
                if (entry2.getKey().equals(entry1.getValue())) {
                    set.add(entry2.getKey());
                }
            }
        }
        return set;
    }
}
