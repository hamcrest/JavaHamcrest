package org.hamcrest;

import org.hamcrest.integration.EasyMock2Adapter;

/**
 *
 * @author Joe Walnes
 */
public class EasyMock2Matchers {

    public static String equalTo(String string) {
        EasyMock2Adapter.adapt(Matchers.equalTo(string));
        return null;
    }

}
