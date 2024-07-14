package src.main.java.com.cafeteria.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

    public static final Integer SHOW_MENU = 1;
    public static final Integer ADD_MENU_ITEM = 2;
    public static final Integer UPDATE_MENU_ITEM = 3;
    public static final Integer DELETE_MENU_ITEM = 4;
    public static final Integer VIEW_DISCARD_MENU_ITEM = 5;
    public static final Integer REMOVE_DISCARD_MENU_ITEM = 6;
    public static final Integer DISCARD_MENU_ITEM_NOTIFICATION = 7;
    public static final Integer SHOW_RECOMMENDATION = 1;
    public static final Integer ROLL_OUT_MENU = 2;
    public static final Integer VIEW_DISCARD_MENU_ITEM_CHEF = 3;
    public static final Integer REMOVE_DISCARD_MENU_ITEM_CHEF = 4;
    public static final Integer DISCARD_MENU_ITEM_NOTIFICATION_CHEF = 5;
    public static final Integer DAILY_MENU_ITEM = 1;
    public static final Integer VOTE = 2;
    public static final Integer FEEDBACK = 3;
    public static final Integer NOTIFICATION = 4;
    public static final Integer PROFILE = 5;
    public static final Integer LOGOUT = 9;

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

    public static final Map<Integer, String> adminCommandMap = new HashMap<>() {
        {
            put(SHOW_MENU, "Show Menu");
            put(ADD_MENU_ITEM, "Add Menu Item");
            put(UPDATE_MENU_ITEM, "Update Menu Item");
            put(DELETE_MENU_ITEM, "Delete Menu Item");
            put(VIEW_DISCARD_MENU_ITEM, "View Discard Menu Item");
            put(REMOVE_DISCARD_MENU_ITEM, "Remove Discard Menu Item");
            put(DISCARD_MENU_ITEM_NOTIFICATION, "Discard Menu Item Notification");
        }
    };

    public static final Map<Integer, String> chefCommandMap = new HashMap<>() {
        {
            put(SHOW_RECOMMENDATION, "Show Recommendation");
            put(ROLL_OUT_MENU, "Rollout Menu");
            put(VIEW_DISCARD_MENU_ITEM_CHEF, "View Discard Menu Item");
            put(REMOVE_DISCARD_MENU_ITEM_CHEF, "Remove Discard Menu Item");
            put(DISCARD_MENU_ITEM_NOTIFICATION_CHEF, "Discard Menu Item Notification");
        }
    };

    public static final Map<Integer, String> employeeCommandMap = new HashMap<>() {
        {
            put(DAILY_MENU_ITEM, "View Daily Menu Item");
            put(VOTE, "Provided Vote");
            put(FEEDBACK, "Provided Feedback");
            put(NOTIFICATION, "View Notification");
            put(PROFILE, "Profile Update");
        }
    };

    public static final List<String> positiveWords = Arrays.asList(
            "delicious", "tasty", "savory", "flavorful", "appetizing",
            "delectable", "scrumptious", "yummy", "mouthwatering", "luscious",
            "succulent", "exquisite", "heavenly", "tempting", "gourmet", "rich",
            "satisfying", "juicy", "nutritious", "fresh", "aromatic", "zesty",
            "flavor-packed", "divine", "tantalizing", "lip-smacking", "indulgent",
            "gratifying", "delightful", "palatable", "bursting with flavor",
            "sumptuous", "decadent", "mouth-filling", "melt-in-your-mouth", "irresistible",
            "crispy", "tender", "velvety", "bright", "fragrant", "clean", "comforting",
            "homemade", "authentic", "freshly baked", "wholesome", "energizing",
            "nourishing", "excellent", "outstanding", "superb", "fantastic", "brilliant",
            "exceptional", "impressive", "marvelous", "remarkable", "wonderful",
            "great", "amazing", "terrific", "fabulous", "awesome", "stellar", "extraordinary",
            "magnificent", "perfect", "commendable", "good");

    public static final List<String> negativeWords = Arrays.asList(
            "poor", "disappointing", "unsatisfactory", "inadequate", "subpar",
            "mediocre", "lacking", "unacceptable", "deficient", "inferior", "weak",
            "flawed", "ineffective", "insufficient", "dismal", "unimpressive",
            "below standard", "troubling", "frustrating", "incomplete", "bland",
            "tasteless", "soggy", "stale", "rancid", "greasy", "unappetizing",
            "mushy", "watery", "overcooked", "undercooked", "burnt", "sour",
            "rotten", "spoiled", "unpleasant", "bitter", "salty", "dry", "tough",
            "chewy", "gritty", "fatty", "artificial", "frozen", "processed", "stodgy",
            "heavy", "unhealthy", "unpalatable", "flat", "stagnant", "insipid",
            "disappointing", "mismatched", "unbalanced", "off-putting", "uninspired",
            "repulsive", "sickening", "stuffy", "stifling", "unappealing", "stinky",
            "dull", "distasteful", "unimaginative", "fake", "lacking", "ordinary", "bad");

    public static final List<String> negationWords = Arrays.asList(
            "not", "no", "never", "none");
}
