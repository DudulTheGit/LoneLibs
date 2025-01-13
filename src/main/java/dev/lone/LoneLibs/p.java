package dev.lone.LoneLibs;

/**
 * Utility class to simulate C++ pointers.
 *
 * @param <T> type of the pointed variable.
 */
public class p<T>
{
    /**
     * Variable pointed to by this {@link p}.
     */
    public T v;

    /**
     * Initialize the pointer.
     *
     * @param varToPointTo variable to point to.
     */
    p(T varToPointTo)
    {
        v = varToPointTo;
    }

    /**
     * Point to a variable.
     *
     * @param varToPointTo variable to point to
     * @param <T> type of the pointed variable
     * @return the new pointer instance.
     */
    public static <T> p<T> point(T varToPointTo)
    {
        return new p<>(varToPointTo);
    }

    /**
     * Creates an empty pointer.
     *
     * @param <T> future pointed variable type.
     * @return the new pointer instance.
     */
    public static <T> p<T> empty()
    {
        return new p<>(null);
    }

    /**
     * Check if the current {@link p} points to a variable.
     * @return true if the current {@link p} points to a variable.
     */
    public boolean points()
    {
        return v != null;
    }

    /**
     * Changes the pointed variable/value.
     * You can also directly set v instead.
     *
     * @param varToPointTo variable to point to.
     */
    public void to(T varToPointTo)
    {
        this.v = varToPointTo;
    }
}
