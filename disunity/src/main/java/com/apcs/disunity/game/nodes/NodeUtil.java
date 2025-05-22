package com.apcs.disunity.game.nodes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * A utility class for nodes
 * 
 * @author Qinzhao Li
 */
public class NodeUtil {

	// Get fields with a given annotation
	public static Stream<Field> getAnnotatedFields(Class<?> cls, Class<? extends Annotation> annotation) {
		if (cls == null) {
			return Stream.empty();
		}
		return Stream.concat(Arrays.stream(cls.getDeclaredFields()).filter(f -> f.isAnnotationPresent(annotation))
				.peek(field -> field.setAccessible(true)), getAnnotatedFields(cls.getSuperclass(), annotation));
	}

}
