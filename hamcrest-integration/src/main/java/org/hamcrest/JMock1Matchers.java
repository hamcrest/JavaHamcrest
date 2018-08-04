/**
 * Copyright (c) 2000-2022 www.hamcrest.org. All rights reserved.
 *
 * This work is licensed under the terms of the BSD license.
 * For a copy, see LICENSE.txt in this repository.
 */
package org.hamcrest;

import org.hamcrest.integration.JMock1Adapter;
import org.hamcrest.core.IsEqual;
import org.jmock.core.Constraint;

public class JMock1Matchers {

    public static Constraint equalTo(String string) {
        return JMock1Adapter.adapt(IsEqual.equalTo(string));
    }
}
