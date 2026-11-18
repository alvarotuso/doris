// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.apache.doris.datasource;

import org.apache.doris.common.Config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IcebergCatalogExecutorThreadNumTest {
    private final int originalThreadNum = Config.iceberg_catalog_executor_thread_num;

    @AfterEach
    public void tearDown() {
        Config.iceberg_catalog_executor_thread_num = originalThreadNum;
    }

    @Test
    public void testDefaultUsesAvailableProcessors() {
        Config.iceberg_catalog_executor_thread_num = -1;
        Assertions.assertEquals(Runtime.getRuntime().availableProcessors(),
                ExternalCatalog.getIcebergCatalogExecutorThreadNum());
    }

    @Test
    public void testNonPositiveUsesAvailableProcessors() {
        Config.iceberg_catalog_executor_thread_num = 0;
        Assertions.assertEquals(Runtime.getRuntime().availableProcessors(),
                ExternalCatalog.getIcebergCatalogExecutorThreadNum());
    }

    @Test
    public void testPositiveOverridesAvailableProcessors() {
        Config.iceberg_catalog_executor_thread_num = 7;
        Assertions.assertEquals(7, ExternalCatalog.getIcebergCatalogExecutorThreadNum());
    }
}
