package com.marketplace.eventstoredb;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

public sealed class AppendResult {

    public static final class Success extends AppendResult {

        private static final int hash = 31;

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            return Objects.equals(this, obj);
        }

        static final Success instance = new Success();

        public static boolean matches(AppendResult appendResult) {
            return appendResult instanceof Success;
//            return appendResult.equals(instance);
        }

    }

    @AllArgsConstructor
    @Getter
    public static final class Failure extends AppendResult {

        private final String message;
    }
}
