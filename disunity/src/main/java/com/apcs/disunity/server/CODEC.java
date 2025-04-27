package com.apcs.disunity.server;

import com.apcs.disunity.annotations.syncedfield.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

import static com.apcs.disunity.server.Util.decodeBits;
import static com.apcs.disunity.server.Util.encodeBits;

/// defines codec for primitives.
public enum CODEC {
    BOOLEAN(
        CODEC::encodeBoolean,
        CODEC::decodeBoolean,
        SyncedBoolean.class
    ),
    BYTE(
        CODEC::encodeByte,
        CODEC::decodeByte,
        SyncedByte.class
    ),
    CHAR(
        CODEC::encodeChar,
        CODEC::decodeChar,
        SyncedChar.class
    ),
    SHORT(
        CODEC::encodeShort,
        CODEC::decodeShort,
        SyncedShort.class
    ),
    INT(
        CODEC::encodeInt,
        CODEC::decodeInt,
        SyncedInt.class
    ),
    LONG(
        CODEC::encodeLong,
        CODEC::decodeLong,
        SyncedLong.class
    ),
    FLOAT(
        CODEC::encodeFloat,
        CODEC::decodeFloat,
        SyncedFloat.class
    ),
    DOUBLE(
        CODEC::encodeDouble,
        CODEC::decodeDouble,
        SyncedDouble.class
    ),
    OBJECT(
        CODEC::encodeObject,
        CODEC::decodeObject,
        SyncedObject.class
    );

    public final Encoder<Object> ENCODER;
    public final Decoder<Object> DECODER;
    public final Class<? extends Annotation> ANNOTATION;

    CODEC(Encoder<Object> encoder, Decoder<Object> decoder, Class<? extends Annotation> annotation) {
        ENCODER = encoder;
        DECODER = decoder;
        ANNOTATION = annotation;
    }

    public static void encodeBoolean(boolean val, OutputStream out) { encodeBits(val ? 1 : 0, 1, out); }
    public static void encodeBoolean(Object val, OutputStream out) { encodeBoolean((boolean) val, out); }
    public static boolean decodeBoolean(InputStream in) {
        return switch ((byte) decodeBits(1,in)) {
            case 1 -> true;
            case 0 -> false;
            default -> throw new IllegalArgumentException("value was neither 1 or 0.");
        };
    }
    public static boolean decodeBoolean(Object __, InputStream in) { return decodeBoolean(in); }

    public static void encodeByte(byte val, OutputStream out) { encodeBits(val,Byte.BYTES,out); }
    public static void encodeByte(Object val, OutputStream out) { encodeByte((byte) val, out); }
    public static byte decodeByte(InputStream in) { return (byte) decodeBits(Byte.BYTES,in); }
    public static byte decodeByte(Object __, InputStream in) { return decodeByte(in); }

    public static void encodeChar(char val, OutputStream out) { encodeBits(val,Character.BYTES,out); }
    public static void encodeChar(Object val, OutputStream out) { encodeChar((char) val, out); }
    public static char decodeChar(InputStream in) { return (char) decodeBits(Character.BYTES, in); }
    public static char decodeChar(Object __, InputStream in) { return decodeChar(in); }

    public static void encodeShort(short val, OutputStream out) { encodeBits(val,Short.BYTES,out); }
    public static void encodeShort(Object val, OutputStream out) { encodeShort((short) val, out); }
    public static short decodeShort(InputStream in) { return (byte) decodeBits(Short.BYTES,in); }
    public static short decodeShort(Object __, InputStream in) { return decodeShort(in); }

    public static void encodeInt(int val, OutputStream out) { encodeBits(val,Integer.BYTES,out); }
    public static void encodeInt(Object val, OutputStream out) { encodeInt((int) val, out); }
    public static int decodeInt(InputStream in) { return (int) decodeBits(Integer.BYTES,in); }
    public static int decodeInt(Object __, InputStream in) { return decodeInt(in); }

    public static void encodeLong(long val, OutputStream out) { encodeBits(val,Long.BYTES,out); }
    public static void encodeLong(Object val, OutputStream out) { encodeLong((long) val, out); }
    public static long decodeLong(InputStream in) {return decodeBits(Long.BYTES,in);}
    public static long decodeLong(Object __, InputStream in) {return decodeLong(in);}

    public static void encodeFloat(float val, OutputStream out) { encodeInt(Float.floatToRawIntBits(val),out); }
    public static void encodeFloat(Object val, OutputStream out) { encodeFloat((float) val, out); }
    public static float decodeFloat(InputStream in) { return Float.intBitsToFloat(decodeInt(in)); }
    public static float decodeFloat(Object __, InputStream in) { return decodeFloat(in); }

    public static void encodeDouble(double val, OutputStream out) { encodeLong(Double.doubleToRawLongBits(val), out); }
    public static void encodeDouble(Object val, OutputStream out) { encodeDouble((double) val, out); }
    public static double decodeDouble(InputStream in) { return Double.longBitsToDouble(decodeLong(in)); }
    public static double decodeDouble(Object __, InputStream in) { return decodeDouble(in); }

    public static void encodeObject(Object val, OutputStream out) {
        if(val instanceof SelfCodec<?>) ((SelfCodec<?>) val).encode(out);
        else for(CODEC codec: values()) {
            for(Field field: Util.getAnnotatedFields(val.getClass(), codec.ANNOTATION).toList()) {
                try {
                    codec.ENCODER.encode(field.get(val), out);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public static <T> T decodeObject(T self, InputStream in) {
        if(self instanceof SelfCodec<?> decodable) return (T) decodable.decode(in);
        else for(CODEC codec: values()) {
            for(Field field: Util.getAnnotatedFields(self.getClass(), codec.ANNOTATION).toList()) {
                try {
                    field.set(self, codec.DECODER.decode(field.get(self), in));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return self;
    }
}
