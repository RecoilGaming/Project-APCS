package com.apcs.disunity.server;

import java.io.IOException;
import java.io.InputStream;
import static java.lang.Byte.SIZE;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 
 * A utility class for converting data to and from byte arrays.
 * 
 * @author Sharvil Phadke
 * 
 */
public final class Util {

    public static final int PACKET_HEADER_SIZE = Integer.BYTES;

    public static final byte[] pack(byte[] bytes) {
        byte[] packet = new byte[PACKET_HEADER_SIZE + bytes.length];
        byte[] header = Util.getBytes(bytes.length);
        System.arraycopy(header, 0, packet, 0, PACKET_HEADER_SIZE);
        System.arraycopy(bytes, 0, packet, PACKET_HEADER_SIZE, bytes.length);
        return packet;
    }

    public static final byte[] unpack(byte[] data, int headerLoc) {
        int size = getInt(data, headerLoc);
        byte[] output = new byte[size];
        System.arraycopy(data, headerLoc + Integer.BYTES, output, 0, size);
        return output;
    }

    public static final byte[] bundleSubpackets(List<byte[]> subpackets) {
        int length = 0;
        for (byte[] subpacket : subpackets) {
            length += subpacket.length + PACKET_HEADER_SIZE;
        }
        byte[] output = new byte[length];
        int loc = 0;
        for (byte[] subpacket : subpackets) {
            System.arraycopy(pack(subpacket), 0, output, loc, subpacket.length);
            loc += subpacket.length + PACKET_HEADER_SIZE;
        }
        return output;
    }

    public static final List<byte[]> debundleSubpackets(byte[] data) {
        int loc = 0;
        List<byte[]> output = new ArrayList<>();
        while (loc < data.length) {
            byte[] subdata = unpack(data, loc);
            output.add(subdata);
            loc += subdata.length + PACKET_HEADER_SIZE;
        }
        return output;
    }

    public static final byte[] unpack(byte[] data) {
        return unpack(data, 0);
    }

    public static final byte[] unpack(InputStream data) {
        try {
        byte[] header = data.readNBytes(Integer.BYTES);
        int packetSize = getInt(header);
        byte[] packet = data.readNBytes(packetSize);
        return packet;
        } catch (IOException ioe) { return null; }
    }

    public static final byte[] getBytes(Object o) {
        if (o instanceof Byte val) return getBytes(val);
        if (o instanceof Short val) return getBytes(val);
        if (o instanceof Character val) return getBytes(val);
        if (o instanceof Integer val) return getBytes(val);
        if (o instanceof Long val) return getBytes(val);
        if (o instanceof Boolean val) return getBytes(val);
        if (o instanceof Float val) return getBytes(val);
        if (o instanceof Double val) return getBytes(val);
        if (o instanceof Syncable val) return val.supply(0);
        return null;
    }

    public static final byte[] getBytes(Byte val) { return new byte[] {val}; }

    public static final byte[] getBytes(Short val) { return getBytes(val & ~0L, Short.BYTES); }

    public static final byte[] getBytes(Character val) { return getBytes(val & ~0L, Character.BYTES); }

    public static final byte[] getBytes(Integer val) { return getBytes(val & ~0L, Integer.BYTES); }
    
    public static final byte[] getBytes(Long val) { return getBytes(val, Long.BYTES); }

    public static final byte[] getBytes(Boolean val) { return new byte[] { (byte) (val ? ~0 : 0) }; }

    public static final byte[] getBytes(Float val) { return getBytes(Float.floatToRawIntBits(val)); }

    public static final byte[] getBytes(Double val) { return getBytes(Double.doubleToRawLongBits(val)); }

    public static final byte[] getBytes(byte val) { return new byte[] {val}; }

    public static final byte[] getBytes(short val) { return getBytes(val & ~0L, Short.BYTES); }

    public static final byte[] getBytes(char val) { return getBytes(val & ~0L, Character.BYTES); }

    public static final byte[] getBytes(int val) { return getBytes(val & ~0L, Integer.BYTES); }
    
    public static final byte[] getBytes(long val) { return getBytes(val, Long.BYTES); }

    public static final byte[] getBytes(boolean val) { return new byte[] { (byte) (val ? ~0 : 0) }; }

    public static final byte[] getBytes(float val) { return getBytes(Float.floatToRawIntBits(val)); }

    public static final byte[] getBytes(double val) { return getBytes(Double.doubleToRawLongBits(val)); }

    public static final byte[] getBytes(long val, int size) {
        byte[] bytes = new byte[size];
        for (int i = 0; i < size; i++) bytes[i] = (byte) ((val & (~0L << (i * SIZE))) >> (i * SIZE));
        return bytes;
    }

    public static final Number getNum(byte[] data, int size, int loc) {
        int val = 0;
        for (int i = 0; i < size; i++) val |= (data[i + loc] & ~0L) << (i * SIZE);
        return val & (~0L >> (Long.BYTES - size));
    }

    public static final int getInt(byte[] data, int loc) {
        return getNum(data, Integer.BYTES, loc).intValue();
    }


    public static final long getLong(byte[] data, int loc) {
        return getNum(data, Long.BYTES, loc).longValue();
    }

    public static final Map<Class<?>,Function<byte[],?>> classMethods = new HashMap<>();
    static {
        classMethods.put(byte.class, Util::getByte);
        classMethods.put(char.class, Util::getChar);
        classMethods.put(int.class, Util::getInt);
        classMethods.put(long.class, Util::getLong);
        classMethods.put(float.class, Util::getFloat);
        classMethods.put(double.class, Util::getDouble);
        classMethods.put(boolean.class, Util::getBoolean);
        classMethods.put(byte.class, Util::getByte);
    }

    public static final Object getPrimativeData(byte[] data, Class<?> type) {
        return classMethods.get(type).apply(data);
    }

    public static final short getShort(byte[] data) { return getNum(data, Short.SIZE, 0).shortValue(); }

    public static final char getChar(byte[] data) { return (char) getShort(data); }

    public static final int getInt(byte[] data) { return getInt(data, 0); }

    public static final long getLong(byte[] data) { return getLong(data, 0); }

    public static final double getDouble(byte[] data) { return Double.doubleToLongBits(getLong(data)); }

    public static final float getFloat(byte[] data) { return Float.intBitsToFloat(getInt(data)); }

    public static final boolean getBoolean(byte[] data) { return data[0] == ~0; }

    public static final byte getByte(byte[] data) { return data[0]; }

    public static final Object[] flatten(Object o) {
        List<Object> objs = new LinkedList<>();
        if (o.getClass().isArray()) {
            objs.addAll(Arrays.asList((Object[]) o));
        } else {
            objs.add(o);
        }
        return objs.toArray();
    }

    public static final void forever(Runnable action, int period) {
        long prev = System.currentTimeMillis();
        while (!Thread.currentThread().isInterrupted()) {
            if (System.currentTimeMillis() - prev >= period) {
                prev = System.currentTimeMillis();
                action.run();
            }
        }
    }

}
