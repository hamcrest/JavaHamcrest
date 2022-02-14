package org.hamcrest;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;

public class EmbeddedMatchersTest {
    public void testMatchMixedWrapperClassAndEmpty() {
        List<List<Integer>> listOfList = new ArrayList<>();
        listOfList.add(asList(1));
        assertThat("wrapper class Integer does not match", listOfList, contains(contains(1)));
        listOfList.add(new ArrayList<Integer>());
        assertThat("empty() does not match", listOfList.get(1), empty());
        assertThat("mixed wrapper class Integer and empty() does not match", listOfList, contains(contains(1), empty()));
    }

    public void testMatchMixedCustomClassAndEmpty() {
        class Foo {
            @Override
            public String toString() {
                return "Foo";
            }
        }
        Foo foo = new Foo();
        List<List<Foo>> listOfList = new ArrayList<>();
        listOfList.add(asList(foo));
        assertThat("custom class Foo does not match", listOfList, contains(contains(foo)));
        listOfList.add(new ArrayList<Foo>());
        assertThat("empty() does not match", listOfList.get(1), empty());
        assertThat("custom wrapper class Foo and empty() does not match", listOfList, contains(contains(foo), empty()));
    }
}
