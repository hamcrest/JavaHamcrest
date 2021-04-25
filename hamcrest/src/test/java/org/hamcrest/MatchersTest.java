
package org.hamcrest;

import org.hamcrest.Matchers;
import org.hamcrest.MatcherAssert;

import java.util.List;
import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static java.util.Arrays.asList;

public final class MatchersTest{
    public void testMatchMixedWrapperclassAndEmpty(){
        List<List<Integer>> listoflist = new ArrayList<>();
        listoflist.add(asList(1));
        assertThat("wrapper class Integer does not match",listoflist,contains(contains(1)));
        listoflist.add(new ArrayList<Integer>());
        assertThat("empty() does not match",listoflist.get(1),empty());
        assertThat("mixed wrapper class Integer and empty() does not macth",listoflist,contains(contains(1),empty()));
    }

    public void testMatchMixedCustomclassAndEmpty(){
        class Foo{
            @Override
            public String toString(){
                return "Foo";
            }
        }
        Foo foo = new Foo();
        List<List<Foo>> listoflist = new ArrayList<>();
        listoflist.add(asList(foo));
        assertThat("custom class Foo does not match",listoflist,contains(contains(foo)));
        listoflist.add(new ArrayList<Foo>());
        assertThat("empty() does not match",listoflist.get(1),empty());
        assertThat("custom wrapper class Foo and empty() does not macth",listoflist,contains(contains(foo),empty()));
    }
}