/**
 * Copyright (c) 2000-2022 www.hamcrest.org. All rights reserved.
 *
 * This work is licensed under the terms of the BSD license.
 * For a copy, see LICENSE.txt in this repository.
 */
package org.hamcrest.core;

public class SampleBaseClass {
    String value;

    public SampleBaseClass(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SampleBaseClass && value.equals(((SampleBaseClass) obj).value);
    }

    @Override
    public int hashCode() {
      return value.hashCode();
    }
}
