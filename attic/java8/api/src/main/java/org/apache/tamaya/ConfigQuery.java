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
package org.apache.tamaya;

/**
 * Models a function that maps a given {@link org.apache.tamaya.Configuration} to something else. This can be used
 * to model additional functionality and applying it to a given {@link org.apache.tamaya.Configuration} instance by
 * calling the {@link org.apache.tamaya.Configuration#query(org.apache.tamaya.ConfigQuery)} method.
 * @param <T> the result type of the query.
 */
@FunctionalInterface
public interface ConfigQuery<T> {

    /**
     * Creates a result based on the given Configuration. Queries basically acts similar to
     * operators, whereas they returns any kind of result.
     *
     * @param config the input configuration, not null.
     * @return the query result.
     */
    T query(Configuration config);
}
