package org.hamcrest;

import org.hamcrest.integration.EasyMock2Adapter;
import org.hamcrest.core.IsEqual;

/**
 *
 * @author Joe Walnes
 */
public class EasyMock2Matchers {

    public static String equalTo(String string) {
        EasyMock2Adapter.adapt(IsEqual.equalTo(string));
        return null;
    }

}
