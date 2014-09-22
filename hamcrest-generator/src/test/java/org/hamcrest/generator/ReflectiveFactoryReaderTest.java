package org.hamcrest.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.Test;

@SuppressWarnings("unused")
public final class ReflectiveFactoryReaderTest {












    private static List<FactoryMethod> methodsReadBy(final ReflectiveFactoryReader reader) {
        final List<FactoryMethod> extractedMethods = new ArrayList<FactoryMethod>();
        for (FactoryMethod factoryMethod : reader) {
            extractedMethods.add(factoryMethod);
        }
        Collections.sort(extractedMethods, new Comparator<FactoryMethod>() {
            @Override public int compare(FactoryMethod o1, FactoryMethod o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return extractedMethods;
    }

    private static FactoryMethod readMethod(Class<?> cls, String methodName) {
        for (FactoryMethod method : new ReflectiveFactoryReader(cls)) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }
}