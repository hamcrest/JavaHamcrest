/**
 * Copyright (c) 2000-2022 www.hamcrest.org. All rights reserved.
 *
 * This work is licensed under the terms of the BSD license.
 * For a copy, see LICENSE.txt in this repository.
 */
package org.hamcrest.internal;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;

import java.util.ArrayList;
import java.util.List;

public class NullSafety {
    @SuppressWarnings("unchecked")
    public static <E> List<Matcher<? super E>> nullSafe(Matcher<? super E>[] itemMatchers) {
        final List<Matcher<? super E>> matchers = new ArrayList<Matcher<? super E>>(itemMatchers.length);
        for (final Matcher<? super E> itemMatcher : itemMatchers) {
            matchers.add((Matcher<? super E>) (itemMatcher == null ? IsNull.nullValue() : itemMatcher));
        }
        return matchers;
    }
}
