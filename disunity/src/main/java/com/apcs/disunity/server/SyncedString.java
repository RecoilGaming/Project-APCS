package com.apcs.disunity.server;

import java.io.InputStream;
import java.io.OutputStream;

import static com.apcs.disunity.server.CODEC.*;

/// # \0 IS RESERVED FOR NETWORK PURPOSE. DO NOT USE \0 IN A VALUE.
/// # IMPLEMENTED CODEC DOES NOT SUPPORT SENDING NULL.
/// encodes string as array of chars.
/// uses \0 as delimiter.
public class SyncedString extends SyncableWrapper<String> {
    public SyncedString(String initialValue) {
        super(initialValue,SyncedString::encodeString,SyncedString::decodeString);
    }

    public static void encodeString(String val, OutputStream out) {
        if(val == null) {
            throw new RuntimeException("null is not supported.");
        } else {
            for(char c: val.toCharArray()) {
                encodeChar(c, out);
            }
            encodeChar('\0', out);
        }
    }
    public static String decodeString(String __, InputStream in) {
        StringBuilder builder = new StringBuilder();
        for(char c = decodeChar(in); c != '\0'; c = decodeChar(in)) {
            builder.append(c);
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return String.format("synced[%s]", value());
    }
}
