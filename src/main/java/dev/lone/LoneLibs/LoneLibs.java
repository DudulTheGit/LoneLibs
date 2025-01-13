package dev.lone.LoneLibs;

public class LoneLibs
{
    public enum CompareVersionResult
    {
        INSTALLED_IS_NEWER,
        INSTALLED_IS_LOWER,
        INSTALLED_IS_SAME,
        UNKNOWN // Should never happen
    }

    public static String getVersion()
    {
        return Main.inst.getDescription().getVersion();
    }

    /**
     * Method to compare two versions.
     * Returns:
     * 1 if installed version is lower than the expected version
     * -1 if expectedVersion is smaller than the installed version
     * 0 if equal
     */
    public static CompareVersionResult compareVersion(String expectedVersion)
    {
        int i = compareVersion(expectedVersion, getVersion());
        switch (i)
        {
            case 1:
                return CompareVersionResult.INSTALLED_IS_LOWER;
            case -1:
                return CompareVersionResult.INSTALLED_IS_NEWER;
            case 0:
                return CompareVersionResult.INSTALLED_IS_SAME;
        }

        return CompareVersionResult.UNKNOWN; // Should never happen
    }

    public static boolean isCompatible(String expectedVersion)
    {
        CompareVersionResult compareVersionResult = compareVersion(expectedVersion);
        return compareVersionResult != CompareVersionResult.UNKNOWN && compareVersionResult != CompareVersionResult.INSTALLED_IS_LOWER;
    }

    /**
     * Method to compare two versions.
     * Returns 1 if v2 is smaller, -1 if v1 is smaller, 0 if equal
     * https://www.geeksforgeeks.org/compare-two-version-numbers/
     */
    public static int compareVersion(String v1, String v2)
    {
        // vnum stores each numeric part of version
        int vnum1 = 0, vnum2 = 0;

        // loop until both String are processed
        for (int i = 0, j = 0; (i < v1.length()
                || j < v2.length()); )
        {
            // Storing numeric part of version 1 in vnum1
            while (i < v1.length()
                    && v1.charAt(i) != '.')
            {
                vnum1 = vnum1 * 10
                        + (v1.charAt(i) - '0');
                i++;
            }

            // Storing numeric part of version 2 in vnum2
            while (j < v2.length()
                    && v2.charAt(j) != '.')
            {
                vnum2 = vnum2 * 10
                        + (v2.charAt(j) - '0');
                j++;
            }

            if (vnum1 > vnum2)
                return 1;
            if (vnum2 > vnum1)
                return -1;

            // if equal, reset variables and go for next numeric part
            vnum1 = vnum2 = 0;
            i++;
            j++;
        }
        return 0;
    }
}
