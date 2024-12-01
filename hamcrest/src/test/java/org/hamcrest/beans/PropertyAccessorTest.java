package org.hamcrest.beans;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItem;

class PropertyAccessorTest {

    @Test
    void testAccessesAllFieldsFromBean() {
        SamePropertyValuesAsTest.ExampleBean input = new SamePropertyValuesAsTest.ExampleBean("test", 1, null);
        PropertyAccessor accessor = new PropertyAccessor(input);

        Set<String> fields = accessor.fieldNames();

        assertThat(fields, hasSize(3));
        assertThat(fields, hasItems("stringProperty", "intProperty", "valueProperty"));
        assertThat(fields, not(hasItem("nonexistentField")));

        assertThat(accessor.fieldValue("stringProperty"), equalTo("test"));
        assertThat(accessor.fieldValue("intProperty"), equalTo(1));
        assertThat(accessor.fieldValue("valueProperty"), equalTo(null));
    }

    @Test
    void testReturnsTheNamesOfAllFieldsFromRecordLikeObject() {
        RecordLikeClass.SmallClass smallClass1 = new RecordLikeClass.SmallClass();
        RecordLikeClass.SmallClass smallClass2 = new RecordLikeClass.SmallClass("small", 3, BigDecimal.ONE, LocalDateTime.of(2024, 1, 2, 3, 4, 5));
        RecordLikeClass input = new RecordLikeClass("uno", 22, true, new Long[] {1L, 2L, 3L}, Arrays.asList(smallClass1, smallClass2));
        PropertyAccessor accessor = new PropertyAccessor(input);

        Set<String> fields = accessor.fieldNames();

        assertThat(fields, hasSize(5));
        assertThat(fields, hasItems("numberArray", "test", "smallClasses", "name", "age"));
        assertThat(fields, not(hasItem("notAGetter1")));
        assertThat(fields, not(hasItem("notAGetter2")));
        assertThat(fields, not(hasItem("field1")));
        assertThat(fields, not(hasItem("nonexistentField")));

        assertThat(accessor.fieldValue("name"), equalTo("uno"));
    }

    /**
     * A Java Record-like class to test the functionality of
     * {@link PropertyAccessor} with Java Records in JDK 8 environment.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/17/language/records.html">https://docs.oracle.com/en/java/javase/17/language/records.html</a>
     */
    @SuppressWarnings("unused")
    static final class RecordLikeClass {
        private final String name;
        private final int age;
        private final boolean test;
        private final Long[] numberArray;
        private final List<RecordLikeClass.SmallClass> smallClasses;

        public RecordLikeClass(String name, int age, boolean test, Long[] numberArray, List<RecordLikeClass.SmallClass> smallClasses) {
            this.name = name;
            this.age = age;
            this.test = test;
            this.numberArray = numberArray;
            this.smallClasses = smallClasses;
        }

        public String name() { return name; }
        public int age() { return age; }
        public boolean test() { return test; }
        public Long[] numberArray() { return numberArray; }
        public List<RecordLikeClass.SmallClass> smallClasses() { return smallClasses; }

        public void notAGetter1() {}
        public String notAGetter2() { return "I'm nothing"; }
        public String name(String fake1, String fake2) { return name; }
        public void name(String fake1) {}

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof RecordLikeClass)) return false;
            RecordLikeClass that = (RecordLikeClass) o;
            return this.age() == that.age() &&
                    this.test() == that.test() &&
                    Objects.equals(this.name(), that.name()) &&
                    Objects.deepEquals(this.numberArray(), that.numberArray())&&
                    Objects.equals(this.smallClasses(), that.smallClasses());
        }

        @Override
        public int hashCode() {
            return Objects.hash(name(), age(), test(), Arrays.hashCode(numberArray()), smallClasses());
        }

        @Override
        public String toString() {
            return "RecordLikeClass{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", test=" + test +
                    ", numberArray=" + Arrays.toString(numberArray) +
                    ", smallClasses=" + smallClasses +
                    '}';
        }

        static class SmallClass {
            private String field1;
            private Integer field2;
            private BigDecimal field3;
            private LocalDateTime field4;

            public SmallClass() {}

            public SmallClass(String field1, Integer field2, BigDecimal field3, LocalDateTime field4) {
                this.field1 = field1;
                this.field2 = field2;
                this.field3 = field3;
                this.field4 = field4;
            }

            @Override
            public String toString() {
                return "SmallClass{field1='" + field1 + "', field2=" + field2 + ", field3=" + field3 + ", field4=" + field4 + '}';
            }
        }
    }

}