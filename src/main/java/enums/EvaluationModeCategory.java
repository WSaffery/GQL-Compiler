package enums;

public enum EvaluationModeCategory {
    RESTRICTED,
    UNRESTRICTED;

    public static EvaluationModeCategory getEvaluationModeCategory(EvaluationMode mode)
    {
        if (mode == null) return null;
        if (mode == EvaluationMode.WALK) return UNRESTRICTED;
        return RESTRICTED;
    }

    public static EvaluationMode[] restrictedModes()
    {
        return new EvaluationMode[] {
            EvaluationMode.SIMPLE, EvaluationMode.ACYCLIC, EvaluationMode.TRAIL
        };
    }

    public static EvaluationMode[] unrestrictedModes()
    {
        return new EvaluationMode[] {EvaluationMode.WALK};
    }

    public static EvaluationMode unrestrictedMode()
    {
        return EvaluationMode.WALK;
    }


}
