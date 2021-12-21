package com.dzen03.core;

public enum ActionType {
    ACT{
        @Override
        public String getVerb(FaceType face) {
            return "дела" + face.getEnding() + ": ";
        }
    },
    THINK{
        @Override
        public String getVerb(FaceType face) {
            return "дума" + face.getEnding() + ": ";
        }
    },
    SEE{
        @Override
        public String getVerb(FaceType face) {
            return "рассматрива" + face.getEnding() + "";
        }
    },
    REMEMBER{
        @Override
        public String getVerb(FaceType face) {
            return "вспомина" + face.getEnding() + "";
        }
    },
    EQUALS{
        @Override
        public String getVerb(FaceType face) {
            return "соответствова" + face.getEnding() + "";
        }
    },
    GUESS{
        @Override
        public String getVerb(FaceType face) {
            return "гада" + face.getEnding() + "";
        }
    },
    PREVENT{
        @Override
        public String getVerb(FaceType face) {
            return "препятствова" + face.getEnding() + "";
        }
    },
    HOLD_BACK{
        @Override
        public String getVerb(FaceType face) {
            return "сдержива" + face.getEnding() + "";
        }
    },
    WORRY_TEASE{
        @Override
        public String getVerb(FaceType face) {
            return "волнова" + face.getEnding() + " и "  + "дразни" + face.getEnding() + ": ";
        }
    },
    PILOT{
        @Override
        public String getVerb(FaceType face) {
            return "ве" + face.getEnding();
        }
    },
    SHOOT{
        @Override
        public String getVerb(FaceType face) {
            return "снима" + face.getEnding();
        }
    },
    SWAP{
        @Override
        public String getVerb(FaceType face) {
            return "замеща" + face.getEnding();
        }
    },
    BE{
        @Override
        public String getVerb(FaceType face) {
            return "бы" + face.getEnding();
        }
    },
    SCARE_WORRY{
        @Override
        public String getVerb(FaceType face) {
            return "пуга" + face.getEnding() + ", насторажива" + face.getEnding();
        }
    },
    CRUMBLE_ROUND_SELF{
        @Override
        public String getVerb(FaceType face)
        {
            return "искроши" + face.getEnding() + "сь и " + "закругли" + face.getEnding() + "сь";
        }
    },
    HELP_TO_STAY{
        @Override
        public String getVerb(FaceType face)
        {
            return "помог" + face.getEnding() + " выстоять";
        }
    },
    FEEL
    {
        @Override
        public String getVerb(FaceType face) {
            return "чувствова" + face.getEnding();
        }
    }
    ;

    public abstract String getVerb(FaceType face);
}
