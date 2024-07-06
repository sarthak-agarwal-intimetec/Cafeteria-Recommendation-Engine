package src.main.java.com.cafeteria;

import java.util.HashMap;
import java.util.Map;

public class Constant {
    public static final Integer VEGETARIAN = 1;
    public static final Integer NON_VEGETARIAN = 2;
    public static final Integer EGGETARIAN = 3;

    public static final Integer SPICE_LEVEL_HIGH = 1;
    public static final Integer SPICE_LEVEL_MEDIUM = 2;
    public static final Integer SPICE_LEVEL_LOW = 3;

    public static final Integer NORTH_INDIAN = 1;
    public static final Integer SOUTH_INDIAN = 2;
    public static final Integer OTHER = 3;

    public static final Map<Integer, String> dietaryPreferenceMap = new HashMap<>() {
        {
            put(VEGETARIAN, "Vegetarian");
            put(NON_VEGETARIAN, "Non-Vegetarian");
            put(EGGETARIAN, "Eggtarian");
        }
    };

    public static final Map<Integer, String> spiceLeveleMap = new HashMap<>() {
        {
            put(SPICE_LEVEL_HIGH, "High");
            put(SPICE_LEVEL_MEDIUM, "Meduim");
            put(SPICE_LEVEL_LOW, "Low");
        }
    };

    public static final Map<Integer, String> cuisineTypeMap = new HashMap<>() {
        {
            put(NORTH_INDIAN, "North-Indian");
            put(SOUTH_INDIAN, "South-Indian");
            put(OTHER, "Other");
        }
    };
}
