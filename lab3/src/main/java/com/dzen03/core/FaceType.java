package com.dzen03.core;

public enum FaceType {
    NONE {
        @Override
        public String getEnding()
        {
            return "ть";
        }
    },
    I {
        @Override
        public String getEnding()
        {
            return "л";
        }
    },
    US {
        @Override
        public String getEnding() {
            return "ли";
        }
    },
    HE {
        @Override
        public String getEnding() {
            return "л";
        }
    },
    SHE {
        @Override
        public String getEnding() {
            return "ла";
        }
    },
    THEY {
        @Override
        public String getEnding() {
            return "ли";
        }
    },
    IT {
        @Override
        public String getEnding() {
            return "ло";
        }
    };

    public abstract String getEnding();
}
