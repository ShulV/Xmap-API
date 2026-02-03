package ru.spotic_api.util;

public class DBCode {
    public static class S3File {
        public enum FileType {
            /**
             * Картинка спота
             */
            SPOT_IMAGE,
        }
    }

    public enum Authority {
        USER,
        ACCEPTOR,
        ADMIN,
    }
}
