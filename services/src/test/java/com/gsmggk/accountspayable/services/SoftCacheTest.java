package com.gsmggk.accountspayable.services;

import java.lang.ref.SoftReference;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gsmggk.accountspayable.dao4api.modelmap.DebtorRepo;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtor;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.services.util.SoftCach;
import com.gsmggk.accountspayable.services.util.cach.CachKey;

public class SoftCacheTest extends AbstractTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(SoftCacheTest.class);
	@Inject
	private SoftCach cache;
	@Inject
	private IDebtorService service;
	private final static Integer interval = 3600;

	@Test

	public void cacheTest() {
		DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date from = null;
		try {
			from = dFormat.parse("01/01/2017 00:00:00");
		} catch (ParseException e) {

			e.printStackTrace();
		}
		ParamsDebtor params = new ParamsDebtor();
		params.setLimit(1000);
		params.setOffset(0);
		for (int i = 1; i < 6; i++) {
			Date to = null;
			try {
				to = dFormat.parse("31/12/2017 00:00:0" + i);
			} catch (ParseException e) {

				e.printStackTrace();
			}
			CachKey key = request2key(from, to, params); // Generate KEY

			List<DebtorRepo> value = service.getDebtorRepo(from, to, params);
			cache.putCache(key, interval, value);
		}

		cache.cache2disk();

		Debtor getValue = (Debtor) cache.getCache("select debtor id-3");
		LOGGER.debug(getValue.toString());

	}

	@Test
	@Ignore
	public void calendartest() {
		Calendar now = Calendar.getInstance();
		DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		System.out.println(dFormat.format(now.getTime()));
		int expirePeriod = 3600;
		now.add(Calendar.SECOND, expirePeriod);
		System.out.println(dFormat.format(now.getTime()));
	}

	private CachKey request2key(Date from, Date to, ParamsDebtor params) {
		CachKey key = new CachKey();
		key.setFrom(from);
		key.setTo(to);
		key.setParams(params);

		return key;
	}
}
