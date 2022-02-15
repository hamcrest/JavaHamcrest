package org.hamcrest;

import java.util.Arrays;
import org.hamcrest.core.AnyOf;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class MatchersTest {

    @Test
    public void matchersContainsInAnyOrderCastTest() {
        String[] truth = new String[] { "1", "2" };
        assertThat("doesn't contain elements", Arrays.asList("1", "2"), containsInAnyOrder(truth));
        Object otherTruth = truth;
        assertThat("doesn't contain elements", Arrays.asList("1", "2"), containsInAnyOrder(otherTruth));
    }

    @Test
    public void matchersContainsCastTest() {
        String[] truth = new String[] { "1", "2" };
        assertThat("doesn't contain elements", Arrays.asList("1", "2"), contains(truth));
        Object otherTruth = truth;
        assertThat("doesn't contain elements", Arrays.asList("1", "2"), contains(otherTruth));
    }
}
