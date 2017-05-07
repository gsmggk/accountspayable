package com.gsmggk.accountspayable.services.util;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.OperationTimeoutException;
import net.spy.memcached.internal.CheckedOperationTimeoutException;

@Component
public class MemClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(MemClient.class);
 
	
	
   
	private MemcachedClient memClient;

	public MemClient() {
		super();
		try {
			this.memClient = new MemcachedClient(new InetSocketAddress("localhost", 11211));
		} catch (IOException e) {
			LOGGER.error("memcached init error");

		}
	}

	/**
	 * Delete cached login info for clerk
	 * Cascade delete: <br>
	 * base64->Login detail <br>
	 * clerkId->base64 <br>
	 * 
	 * @param clerkId
	 *            clerk id
	 */
	public void deleteMemCached(Integer clerkId) {
		try {
			String base64 = (String) memClient.get(clerkId.toString());

			if (base64 != null) {
				memClient.delete(base64);
			}
			memClient.delete(clerkId.toString());
		} catch (OperationTimeoutException e) {
			LOGGER.error("memcached delete error");
		}

	}

	public UserSessionStorage getCach(String base64) {
		UserSessionStorage storage ;
		try {
			storage = (UserSessionStorage) memClient.get(base64);
		} catch (OperationTimeoutException e) {
			storage = null;
		}

		return storage;
	}

	/**
	 * Cascade set cach. <br>
	 * basse64-> Login info<br>
	 * clerkId->base64
	 * @param base64
	 * @param storageDb
	 */
	public void setCach(String base64, UserSessionStorage storageDb) {
		try {
			memClient.set(base64, 3600, storageDb);
			memClient.set(storageDb.getId().toString(), 3600, base64);
		} catch (OperationTimeoutException e) {
			LOGGER.error("memcached set error");
		}

	}

}
