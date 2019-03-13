/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Original Work: Apache License, Version 2.0, Copyright 2017 Hans-Peter Grahsl.
 */

package com.mongodb.kafka.connect.processor.field.renaming;

import java.util.Map;

import com.mongodb.kafka.connect.MongoSinkConnectorConfig;

public class RenameByMapping extends Renamer {

    private Map<String, String> fieldMappings;

    public RenameByMapping(final MongoSinkConnectorConfig config, final String collection) {
        this(config, config.parseRenameFieldnameMappings(collection), collection);
    }

    public RenameByMapping(final MongoSinkConnectorConfig config,
                           final Map<String, String> fieldMappings, final String collection) {
        super(config, collection);
        this.fieldMappings = fieldMappings;
    }

    @Override
    protected boolean isActive() {
        return !fieldMappings.isEmpty();
    }

    protected String renamed(final String path, final String name) {
        String newName = fieldMappings.get(path + SUB_FIELD_DOT_SEPARATOR + name);
        return newName != null ? newName : name;
    }

}