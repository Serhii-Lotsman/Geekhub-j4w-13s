package org.geekhub.hw4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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

        for (Object object : collection) {
            stringBuilder.append(object.toString()).append(delimiter);
        }
        if (!collection.isEmpty()) {
            int indexLastObj = stringBuilder.lastIndexOf(String.valueOf(delimiter));
            stringBuilder.deleteCharAt(indexLastObj);
        }
        return stringBuilder.toString();
    }

    @Override
    public List<Double> reversed(List<? extends Number> collection) {
        List<Double> reverseCollection = new ArrayList<>();

        for (int num = collection.size() - 1; num >= 0; num--) {
            reverseCollection.add(collection.get(num).doubleValue());
        }
        return reverseCollection;
    }

    @Override
    public List<List<Object>> chunked(Collection<?> collection, int amount) {
        List<List<Object>> collectionPieces = new ArrayList<>();
        List<Object> chunk = new ArrayList<>(collection);

        for (int listIterator = 0; listIterator < amount; listIterator++) {
            List<Object> subList = new ArrayList<>();
            for (int element = listIterator; element < chunk.size(); element += amount) {
                subList.add(chunk.get(element));
            }
            collectionPieces.add(subList);
        }
        return collectionPieces;
    }

    @Override
    public List<?> dropElements(List<?> list, Object criteria) {
        Iterator<?> iterator = list.iterator();

        if (criteria instanceof Integer) {
            list.remove(list.get((int) criteria));
        } else {
            while (iterator.hasNext()) {
                Object object = iterator.next();
                if (object.equals(criteria)) {
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
        Set<T> uniqueElements = new HashSet<>();
        List<T> result = new ArrayList<>();

        for (T element : collection) {
            if (element != null && uniqueElements.add(element)) {
                result.add(element);
            }
        }

        return result;
    }

    @Override
    public <T> Map<T, Collection<T>> grouping(Collection<T> collection) {
        Map<T, Collection<T>> groupMap = new HashMap<>();
        Iterator<T> iterator = collection.iterator();
        Collection<T> list;

        while (iterator.hasNext()) {
            var key = iterator.next();
            if (groupMap.containsKey(key)) {
                list = groupMap.get(key);
                list.add(key);
            } else {
                list = new ArrayList<>();
                list.add(key);
                groupMap.put(key, list);
            }
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

        for (Map.Entry<T, U> key : map.entrySet()) {
            if (key.getValue() == null) {
                map.replace(key.getKey(), defaultValue);
            }
        }
        return map;
    }

    @Override
    public <T> Collection<T> collectingList(Map<T, T> map1, Map<T, T> map2) {
        Set<T> set = new HashSet<>();
        for (Map.Entry<T,T> entry2 : map2.entrySet()) {
            for (Map.Entry<T,T> entry1 : map1.entrySet()) {
                if (entry1.getKey() == entry2.getValue()) {
                    set.add(entry1.getKey());
                }
                if (entry2.getKey() == entry1.getValue()) {
                    set.add(entry2.getKey());
                }
            }
        }
        return set;
    }
}
