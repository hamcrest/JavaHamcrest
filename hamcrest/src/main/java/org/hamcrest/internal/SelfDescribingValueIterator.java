/**
 * Copyright (c) 2000-2022 www.hamcrest.org. All rights reserved.
 *
 * This work is licensed under the terms of the BSD license.
 * For a copy, see LICENSE.txt in this repository.
 */
package org.hamcrest.internal;

import org.hamcrest.SelfDescribing;

import java.util.Iterator;

public class SelfDescribingValueIterator<T> implements Iterator<SelfDescribing> {
    private Iterator<T> values;
    
    public SelfDescribingValueIterator(Iterator<T> values) {
        this.values = values;
    }
    
    @Override
    public boolean hasNext() {
        return values.hasNext();
    }

    @Override
    public SelfDescribing next() {
        return new SelfDescribingValue<T>(values.next());
    }

    @Override
    public void remove() {
        values.remove();
    }
}
