package com.xmap_api.util;

import java.util.List;

public class UICode {
    public static class Spots {
        public static class ViewMode {
            /**
             * Карточки спотов
             */
            public final static String CARDS = "CARDS";
            /**
             * Яндекс карта
             */
            public final static String YMAP = "YMAP";

            public final static List<String> ALL = List.of(CARDS, YMAP);
        }
    }
}
