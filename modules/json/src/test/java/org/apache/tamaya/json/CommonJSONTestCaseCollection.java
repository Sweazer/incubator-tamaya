/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.tamaya.json;

import org.apache.tamaya.ConfigException;
import org.apache.tamaya.spi.PropertySource;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.net.URL;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Class with a collection of common test cases each JSON processing
 * class must be able to pass.
 */
public abstract class CommonJSONTestCaseCollection {

    abstract PropertySource getPropertiesFrom(URL source) throws Exception;

    @Test
    public void canReadNonLatinCharacters() throws Exception {
        URL configURL = JSONPropertySourceTest.class
             .getResource("/configs/valid/cyrillic.json");

        assertThat(configURL, Matchers.notNullValue());

        PropertySource propertySource = getPropertiesFrom(configURL);

        assertThat(propertySource.get("name"), Matchers.notNullValue());
        assertThat(propertySource.get("name"), equalTo("\u041e\u043b\u0438\u0432\u0435\u0440"));
        assertThat(propertySource.get("\u0444\u0430\u043c\u0438\u043b\u0438\u044f"), Matchers.notNullValue());
        assertThat(propertySource.get("\u0444\u0430\u043c\u0438\u043b\u0438\u044f"), Matchers.equalTo("Fischer"));
    }

    @Test
    public void canReadNestedStringOnlyJSONConfigFile() throws Exception {
        URL configURL = JSONPropertySourceTest.class
                .getResource("/configs/valid/simple-nested-string-only-config-1.json");

        assertThat(configURL, CoreMatchers.notNullValue());

        PropertySource properties = getPropertiesFrom(configURL);

        assertThat(properties.getProperties().keySet(), hasSize(5));

        String keyB = properties.get("b");
        String keyDO = properties.get("d.o");
        String keyDP = properties.get("d.p");

        assertThat(keyB, notNullValue());
        assertThat(keyB, equalTo("B"));
        assertThat(keyDO, notNullValue());
        assertThat(keyDO, equalTo("O"));
        assertThat(keyDP, Matchers.notNullValue());
        assertThat(keyDP, is("P"));
    }

    @Test
    public void canReadNestedStringOnlyJSONConfigFileWithObjectInTheMiddle()
            throws Exception {
        URL configURL = JSONPropertySourceTest.class
                .getResource("/configs/valid/simple-nested-string-only-config-2.json");

        assertThat(configURL, CoreMatchers.notNullValue());

        PropertySource properties = getPropertiesFrom(configURL);

        assertThat(properties.getProperties().keySet(), hasSize(4));

        String keyA = properties.get("a");
        String keyDO = properties.get("b.o");
        String keyDP = properties.get("b.p");
        String keyC = properties.get("c");

        assertThat(keyA, notNullValue());
        assertThat(keyA, is("A"));
        assertThat(keyC, notNullValue());
        assertThat(keyC, equalTo("C"));
        assertThat(keyDO, notNullValue());
        assertThat(keyDO, equalTo("O"));
        assertThat(keyDP, notNullValue());
        assertThat(keyDP, is("P"));
    }

    @Test(expected = ConfigException.class)
    public void canHandleIllegalJSONFileWhichContainsAnArray() throws Exception {
        URL configURL = JSONPropertySourceTest.class.getResource("/configs/invalid/with-array.json");

        assertThat(configURL, CoreMatchers.notNullValue());

        getPropertiesFrom(configURL).getProperties();
    }

    @Test(expected = ConfigException.class)
    public void canHandleIllegalJSONFileConsistingOfOneOpeningBracket() throws Exception {
        URL configURL = JSONPropertySourceTest.class.getResource("/configs/invalid/only-opening-bracket.json");

        assertThat(configURL, CoreMatchers.notNullValue());

        getPropertiesFrom(configURL).getProperties();
    }

    @Test(expected = ConfigException.class)
    public void canHandleIllegalJSONFileWhichIsEmpty() throws Exception {
        URL configURL = JSONPropertySourceTest.class.getResource("/configs/invalid/empty-file.json");

        assertThat(configURL, CoreMatchers.notNullValue());

        getPropertiesFrom(configURL).getProperties();
    }

    @Test
    public void priorityInConfigFileOverwriteExplicitlyGivenPriority() throws Exception {
        URL configURL = JSONPropertySourceTest.class.getResource("/configs/valid/with-explicit-priority.json");

        assertThat(configURL, CoreMatchers.notNullValue());

        PropertySource properties = getPropertiesFrom(configURL);

        assertThat(properties.getOrdinal(), is(16784));
    }

    @Test
    public void canReadFlatStringOnlyJSONConfigFile() throws Exception {
        URL configURL = JSONPropertySourceTest.class.getResource("/configs/valid/simple-flat-string-only-config.json");

        assertThat(configURL, CoreMatchers.notNullValue());

        PropertySource properties = getPropertiesFrom(configURL);

        assertThat(properties.getProperties().keySet(), hasSize(3));

        String keyA = properties.get("a");
        String keyB = properties.get("b");
        String keyC = properties.get("c");

        assertThat(keyA, notNullValue());
        assertThat(keyA, equalTo("A"));
        assertThat(keyB, notNullValue());
        assertThat(keyB, is("B"));
        assertThat(keyC, notNullValue());
        assertThat(keyC, is("C"));
    }

    @Test(expected = ConfigException.class)
    public void emptyJSONFileResultsInConfigException() throws Exception {
        URL configURL = JSONPropertySourceTest.class.getResource("/configs/invalid/empty-file.json");

        assertThat(configURL, CoreMatchers.notNullValue());

        PropertySource properties = getPropertiesFrom(configURL);

        properties.getProperties();
    }

    @Test
    public void canHandleEmptyJSONObject() throws Exception {
        URL configURL = JSONPropertySourceTest.class.getResource("/configs/valid/empty-object-config.json");

        assertThat(configURL, CoreMatchers.notNullValue());

        PropertySource properties = getPropertiesFrom(configURL);

        assertThat(properties.getProperties().keySet(), hasSize(0));
    }
}
