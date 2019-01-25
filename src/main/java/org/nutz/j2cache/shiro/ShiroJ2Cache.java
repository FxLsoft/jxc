package org.nutz.j2cache.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import net.oschina.j2cache.CacheChannel;

/**
 * 封装j2cache为shiro的Cache接口
 * @author wendal
 */
@SuppressWarnings("unchecked")
public class  ShiroJ2Cache<K, V> implements Cache<K, V> {
	
	protected String region;
	
	protected CacheChannel channel;
	
	public ShiroJ2Cache(String region, CacheChannel channel) {
		this.region = region;
		this.channel = channel;
	}

	public V get(K key) throws CacheException {
		return (V) this.channel.get(region, key.toString()).getValue();
	}

	public V put(K key, V value) throws CacheException {
		this.channel.set(region, key.toString(), value);
		return null;
	}

	public V remove(K key) throws CacheException {
		this.channel.evict(region, key.toString());
		return null;
	}

	public void clear() throws CacheException {
		this.channel.clear(region);
	}

	public int size() {
		return this.channel.keys(region).size();
	}

	public Set<K> keys() {

		return new HashSet<K>((Collection<K>)this.channel.keys(region));
	}

	public Collection<V> values() {
		List<V> list = new ArrayList<V>();
		for (K k : keys()) {
			list.add(get(k));
		}
		return list;
	}

}
