/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.loadtests.hashmap;

import java.util.IdentityHashMap;
import org.apache.ignite.cache.store.CacheStore;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.internal.processors.affinity.AffinityTopologyVersion;
import org.apache.ignite.internal.processors.cache.CacheAffinitySharedManager;
import org.apache.ignite.internal.processors.cache.CacheDiagnosticManager;
import org.apache.ignite.internal.processors.cache.CacheOsConflictResolutionManager;
import org.apache.ignite.internal.processors.cache.CacheType;
import org.apache.ignite.internal.processors.cache.GridCacheAffinityManager;
import org.apache.ignite.internal.processors.cache.GridCacheContext;
import org.apache.ignite.internal.processors.cache.GridCacheDeploymentManager;
import org.apache.ignite.internal.processors.cache.GridCacheEventManager;
import org.apache.ignite.internal.processors.cache.GridCacheEvictionManager;
import org.apache.ignite.internal.processors.cache.GridCacheIoManager;
import org.apache.ignite.internal.processors.cache.GridCacheMvccManager;
import org.apache.ignite.internal.processors.cache.GridCachePartitionExchangeManager;
import org.apache.ignite.internal.processors.cache.GridCacheSharedContext;
import org.apache.ignite.internal.processors.cache.GridCacheSharedTtlCleanupManager;
import org.apache.ignite.internal.processors.cache.GridCacheTtlManager;
import org.apache.ignite.internal.processors.cache.WalStateManager;
import org.apache.ignite.internal.processors.cache.datastructures.CacheDataStructuresManager;
import org.apache.ignite.internal.processors.cache.distributed.dht.topology.PartitionsEvictManager;
import org.apache.ignite.internal.processors.cache.dr.GridOsCacheDrManager;
import org.apache.ignite.internal.processors.cache.jta.CacheNoopJtaManager;
import org.apache.ignite.internal.processors.cache.persistence.IgniteCacheDatabaseSharedManager;
import org.apache.ignite.internal.processors.cache.persistence.snapshot.IgniteCacheSnapshotManager;
import org.apache.ignite.internal.processors.cache.query.GridCacheDistributedQueryManager;
import org.apache.ignite.internal.processors.cache.query.continuous.CacheContinuousQueryManager;
import org.apache.ignite.internal.processors.cache.store.CacheOsStoreManager;
import org.apache.ignite.internal.processors.cache.transactions.IgniteTxManager;
import org.apache.ignite.internal.processors.cache.version.GridCacheVersionManager;
import org.apache.ignite.internal.processors.plugin.CachePluginManager;
import org.apache.ignite.lang.IgniteUuid;
import org.apache.ignite.testframework.junits.GridTestKernalContext;

import static org.apache.ignite.testframework.junits.GridAbstractTest.defaultCacheConfiguration;

/**
 * Cache test context.
 */
public class GridCacheTestContext<K, V> extends GridCacheContext<K, V> {
    /**
     * @param ctx Context.
     * @throws Exception If failed.
     */
    public GridCacheTestContext(GridTestKernalContext ctx) throws Exception {
        super(
            ctx,
            new GridCacheSharedContext<>(
                ctx,
                new IgniteTxManager(),
                new GridCacheVersionManager(),
                new GridCacheMvccManager(),
                null,
                null,
                new WalStateManager(null),
                new IgniteCacheDatabaseSharedManager(ctx),
                null,
                new IgniteCacheSnapshotManager(),
                new GridCacheDeploymentManager<K, V>(),
                new GridCachePartitionExchangeManager<K, V>(),
                new CacheAffinitySharedManager<K, V>(),
                new GridCacheIoManager(),
                new GridCacheSharedTtlCleanupManager(),
                new PartitionsEvictManager(),
                new CacheNoopJtaManager(),
                null,
                null,
                null,
                new CacheDiagnosticManager(),
                null
            ),
            defaultCacheConfiguration(),
            null,
            CacheType.USER,
            AffinityTopologyVersion.ZERO,
            IgniteUuid.randomUuid(),
            true,
            true,
            false,
            false,
            new GridCacheEventManager(),
            new CacheOsStoreManager(null, new CacheConfiguration()),
            new GridCacheEvictionManager(),
            new GridCacheDistributedQueryManager<>(),
            new CacheContinuousQueryManager(),
            new CacheDataStructuresManager(),
            new GridCacheTtlManager(),
            new GridOsCacheDrManager(),
            new CacheOsConflictResolutionManager<K, V>(),
            new CachePluginManager(ctx, new CacheConfiguration()),
            new GridCacheAffinityManager(),
            null
        );

        store().initialize(null, new IdentityHashMap<CacheStore, ThreadLocal>());
    }
}
