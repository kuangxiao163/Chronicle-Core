/*
 * Copyright 2016 higherfrequencytrading.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package net.openhft.chronicle.core.onoes;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/*
 * Created by Peter Lawrey on 13/06/2016.
 */
public class RecordingExceptionHandler implements ExceptionHandler {
    private final LogLevel level;
    private final Map<ExceptionKey, Integer> exceptionKeyCountMap;

    public RecordingExceptionHandler(LogLevel level, Map<ExceptionKey, Integer> exceptionKeyCountMap) {
        this.level = level;
        this.exceptionKeyCountMap = exceptionKeyCountMap;
    }

    @Override
    public void on(Class clazz, String message, Throwable thrown) {
        synchronized (exceptionKeyCountMap) {
            @NotNull ExceptionKey key = new ExceptionKey(level, clazz, message, thrown);
            exceptionKeyCountMap.merge(key, 1, (p, v) -> p == null ? v : p + v);
        }
    }
}
