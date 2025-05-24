package com.apcs.disunity.game.signals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Handles node communication through signals
 * 
 * @author Qinzhao Li
 */
public class Signals {

    /* ================ [ FIELDS ] ================ */

    // Maps signals to their functions
    private static final Map<String, List<Consumer<?>>> signals = new HashMap<>();

    /* ================ [ METHODS ] ================ */

    // Get signal name with source
    public static String getSignal(int source, String name) {
        if (source < 0) return name;
        return source + "_" + name;
    };
	
	// Get signal name with uuid
	public static String getSignal(UUID uuid, String name) {
		return uuid.toString() + "_" + name;
	}

    // Connect a signal to a function
    public static <T> void connect(String signal, Consumer<T> function) {
        if (!signals.containsKey(signal)) {
            signals.put(signal, new ArrayList<>());
        }

        signals.get(signal).add(function);
    }

    // Disconnect a signal from a function
    public static <T> void disconnect(String signal, Consumer<T> function) {
        if (signals.containsKey(signal)) {
            signals.get(signal).remove(function);
        }
    }

    // Trigger all functions connected to a signal
    public static <T> void trigger(String signal, T data) {
        if (signals.containsKey(signal)) {
            for (Consumer<?> consumer : signals.get(signal)) {
                ((Consumer<T>) consumer).accept(data);
            }
        }
    }
    
}