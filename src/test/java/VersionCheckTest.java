import dev.lone.LoneLibs.LoneLibs;
import dev.lone.LoneLibs.Main;

class VersionCheckTest
{
    /**
     * Method to compare two versions.
     * Returns:
     * 1 if installed version is lower than the expected version
     * -1 if expectedVersion is smaller than the installed version
     * 0 if equal
     */
    public static LoneLibs.CompareVersionResult compareVersion(String expectedVersion, String other)
    {
        int i = LoneLibs.compareVersion(expectedVersion, other);
        switch (i)
        {
            case 1:
                return LoneLibs.CompareVersionResult.INSTALLED_IS_LOWER;
            case -1:
                return LoneLibs.CompareVersionResult.INSTALLED_IS_NEWER;
            case 0:
                return LoneLibs.CompareVersionResult.INSTALLED_IS_SAME;
        }

        return LoneLibs.CompareVersionResult.UNKNOWN; // Should never happen
    }

    // Driver method to check above
    // comparison function
    public static void main(String[] args)
    {
        String currentVer = "1.0";
        System.out.println("1.0.24 " + compareVersion("1.0.24", currentVer));
        System.out.println("1.0.23 " + compareVersion("1.0.23", currentVer));
        System.out.println("1.0.22 " + compareVersion("1.0.22", currentVer));
        System.out.println("1.0.30 " + compareVersion("1.0.30", currentVer));
        System.out.println("1.0.0 " + compareVersion("1.0.0", currentVer));
        System.out.println("1.0 " + compareVersion("1.0", currentVer));
    }
}