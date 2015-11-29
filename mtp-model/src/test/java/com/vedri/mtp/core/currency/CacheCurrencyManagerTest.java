package com.vedri.mtp.core.currency;

import com.vedri.mtp.core.rate.NoRateException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.vedri.mtp.core.country.Country;
import com.vedri.mtp.core.country.CountryManager;
import org.testng.annotations.Test;

import java.util.Set;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CacheCurrencyManagerTest {

	@Mock
	private CountryManager countryManager;

	private CacheCurrencyManager cacheCurrencyManager;

	private Country country1;

	@BeforeClass
	public void init() {

		MockitoAnnotations.initMocks(this);

		Mockito.reset(countryManager);

		country1 = new Country("c1", "c11", "name1", "offname1", Sets.newHashSet("cu1", "cu2"));

		Mockito.when(countryManager.getCountries())
				.thenReturn(Lists.newArrayList(
						country1, new Country("c2", "c22", "name2", "offname2", Sets.newHashSet("cu2", "cu3"))));

		cacheCurrencyManager = new CacheCurrencyManager(countryManager);
	}

    @Test
    public void testCountryManagerInit() {
        cacheCurrencyManager.init();
        verify(countryManager, times(1)).getCountries();
        Assert.assertEquals(cacheCurrencyManager.getCurrencies().size(), 3);
        Assert.assertTrue(cacheCurrencyManager.getCurrencies().contains(new Currency("cu1")));
        Assert.assertTrue(cacheCurrencyManager.getCurrencies().contains(new Currency("cu2")));
        Assert.assertTrue(cacheCurrencyManager.getCurrencies().contains(new Currency("cu3")));
    }

    @Test(dependsOnMethods = "testCountryManagerInit")
    public void getCountriesFromCurrency() throws NoRateException {
        final Currency cu0 = cacheCurrencyManager.getCurrencyFromCode("cu0");
        Assert.assertNotNull(cu0);
    }
}