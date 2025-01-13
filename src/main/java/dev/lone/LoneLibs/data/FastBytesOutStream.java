package dev.lone.LoneLibs.data;

import java.io.*;

/**
 * Faster ByteArrayOutputSteam with direct buffer access.
 */
public class FastBytesOutStream extends ByteArrayOutputStream
{
    public FastBytesOutStream() {}

    public FastBytesOutStream(int size)
    {
        super(size);
    }

    public byte[] raw()
    {
        return this.buf;
    }

    public void writeAt(byte[] bytes, int idx)
    {
        System.arraycopy(bytes, 0, buf, idx, bytes.length);
    }

    public void seekAdd(int i)
    {
        this.count += i;
    }

    public void writeToOther(DataOutput other) throws IOException
    {
        other.write(raw(), 0, size());
    }

    public void writeToOther(OutputStream other) throws IOException
    {
        other.write(raw(), 0, size());
    }

    public ByteArrayInputStream toInputStream()
    {
        return new ByteArrayInputStream(raw(), 0, size());
    }

    /**
     * Warning! The new array must have the same size fo the buffer! Call raw().length before to make sure it's correct.
     */
    public FastBytesOutStream overwrite(byte[] newBytes)
    {
        System.arraycopy(newBytes, 0, buf, 0, newBytes.length);
        return this;
    }
}
