package dk.erikzielke.falconsocialassignment;

import java.util.List;

public class ListUtils {
    public static <T> T last(List<T> list) {
        if (list.isEmpty()) {
            return null;
        }
        return list.get(list.size() - 1);
    }
}
