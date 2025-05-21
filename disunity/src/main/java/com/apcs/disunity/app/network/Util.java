package com.apcs.disunity.app.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Byte.SIZE;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static com.apcs.disunity.app.network.packet.CODEC.decodeInt;
import static com.apcs.disunity.app.network.packet.CODEC.encodeInt;

/**
 * A utility class for networking code
 * 
 * @author Sharvil Phadke
 */
public final class Util {

    public static byte[] pack(byte[] bytes) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        encodeInt(bytes.length, out);
        try {
            out.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out.toByteArray();
    }

    public static byte[] unpack(InputStream data) {
        try {
            int packetSize = decodeInt(data);
            return data.readNBytes(packetSize);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static Object[] flatten(Object o) {
        List<Object> objs = new LinkedList<>();
        if (o.getClass().isArray()) {
            objs.addAll(Arrays.asList((Object[]) o));
        } else {
            objs.add(o);
        }
        return objs.toArray();
    }

    public static void encodeBits(long bits, int byteSize, OutputStream out) {
        for (int i = 0; i < byteSize; i++, bits >>= SIZE) {
            try {
                out.write((byte) (bits & 0xFF));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static long decodeBits(int byteSize, InputStream in) {
        try {
            long val = 0;
            for (int i = 0; i < byteSize; i++) {
                long byt = in.read();
                if (byt < 0)
                    throw new RuntimeException("stream shorter than required size");
                val |= byt << (i * SIZE);
            }
            return val;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
