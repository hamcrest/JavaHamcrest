/**
 * Copyright (c) 2000-2022 www.hamcrest.org. All rights reserved.
 *
 * This work is licensed under the terms of the BSD license.
 * For a copy, see LICENSE.txt in this repository.
 */
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
