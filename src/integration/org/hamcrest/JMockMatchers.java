package org.hamcrest;

import org.hamcrest.integration.JMockAdapter;
import org.jmock.core.Constraint;

public class JMockMatchers {

    public static Constraint isTwoXs() {
        return JMockAdapter.adapt(Matchers.isTwoXs());
    }
}
