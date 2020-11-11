package com.android.almg.chalchiteko.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Palabra> ITEMS = new ArrayList<Palabra>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Palabra> ITEM_MAP = new HashMap<String, Palabra>();

    private static final int COUNT = 0;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(Palabra item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Palabra createDummyItem(int position) {
        return new Palabra(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Palabra {
        private String id;
        private String spanish;
        private String chalchiteko;

        public Palabra() {

        }

        public Palabra(String spanish, String chalchiteko) {
            this.spanish = spanish;
            this.chalchiteko = chalchiteko;
        }

        public Palabra(String id, String spanish, String chalchiteko) {
            this.id = id;
            this.spanish = spanish;
            this.chalchiteko = chalchiteko;
        }

        @Override
        public String toString() {
            return spanish;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSpanish() {
            return spanish;
        }

        public void setSpanish(String spanish) {
            this.spanish = spanish;
        }

        public String getChalchiteko() {
            return chalchiteko;
        }

        public void setChalchiteko(String chalchiteko) {
            this.chalchiteko = chalchiteko;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Palabra palabra = (Palabra) o;
            return Objects.equals(id, palabra.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}