package org.hamcrest;

import org.hamcrest.integration.EasyMockAdapter;

/**
 *
 * @author Joe Walnes
 */
public class EasyMockMatchers {

    public static String isTwoXs() {
        EasyMockAdapter.adapt(Matchers.isTwoXs());
        return null;
    }

}
