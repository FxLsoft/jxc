package org.nutz.j2cache.shiro;

import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Initializable;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2Cache;

/**
 * 适配Shiro的CacheManager
 * @author wendal
 *
 */
public class J2CacheManager extends AbstractCacheManager implements Initializable, Destroyable {
	
	protected CacheChannel channel;

	public void init() throws ShiroException {
		channel = J2Cache.getChannel();
	}

	public void destroy() throws Exception {
		if (channel != null)
			channel.close();
	}

	protected Cache<Object, Object> createCache(String name) throws CacheException {
		return new ShiroJ2Cache<Object, Object>(name, channel);
	}

}
