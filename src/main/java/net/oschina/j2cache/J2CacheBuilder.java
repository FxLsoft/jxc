/**
 * Copyright (c) 2015-2017, Winter Lau (javayou@gmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.oschina.j2cache;

import net.oschina.j2cache.util.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 使用自定义配置构建 J2Cache
 * @author Winter Lau (javayou@gmail.com)
 */
public class J2CacheBuilder {

    private final static Logger log = LoggerFactory.getLogger(J2CacheBuilder.class);

    private J2CacheConfig config;
    private CacheChannel channel;
    private static ClusterPolicy policy; //不同的广播策略

    private J2CacheBuilder(J2CacheConfig config) {
        this.config = config;

        this.initFromConfig(config);
        /* 初始化缓存接口 */
        this.channel = new CacheChannel(){
            @Override
            public void sendClearCmd(String region) {
                policy.sendClearCmd(region);
            }

            @Override
            public void sendEvictCmd(String region, String...keys) {
                policy.sendEvictCmd(region, keys);
            }

            @Override
            public void close() {
                policy.disconnect();
                CacheProviderHolder.shutdown();
            }
        };
    }

    /**
     * 初始化 J2Cache，这是一个很重的操作，请勿重复执行
     * @param config
     * @return
     */
    public final static J2CacheBuilder init(J2CacheConfig config) {
        return new J2CacheBuilder(config);
    }

    /**
     * 返回缓存操作接口
     * @return CacheChannel
     */
    public CacheChannel getChannel(){
        return this.channel;
    }

    /**
     * 关闭 J2Cache
     */
    public void close() {
        this.channel.close();
    }

    /**
     * 加载配置
     * @return
     * @throws IOException
     */
    private static void initFromConfig(J2CacheConfig config) {
        SerializationUtils.init(config.getSerialization());
        //初始化两级的缓存管理
        CacheProviderHolder.init(config, (region, key)->{
            //当一级缓存中的对象失效时，自动清除二级缓存中的数据
            CacheProviderHolder.getLevel2Cache(region).evict(key);
            log.debug(String.format("Level 1 cache object expired, evict level 2 cache object [%s,%s]", region, key));
            if(policy != null)
                policy.sendEvictCmd(region, key);
        });

        policy = ClusterPolicyFactory.init(config.getBroadcast(), config.getBroadcastProperties());
        log.info("Using cluster policy : " + policy.getClass().getName());
    }
}
