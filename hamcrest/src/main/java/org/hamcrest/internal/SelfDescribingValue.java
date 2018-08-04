/**
 * Copyright (c) 2000-2022 www.hamcrest.org. All rights reserved.
 *
 * This work is licensed under the terms of the BSD license.
 * For a copy, see LICENSE.txt in this repository.
 */
package org.hamcrest.internal;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

public class SelfDescribingValue<T> implements SelfDescribing {
    private T value;
    
    public SelfDescribingValue(T value) {
        this.value = value;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(value);
    }
}
