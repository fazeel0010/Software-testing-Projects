package tests;

import org.junit.jupiter.api.Test;
import po.CurrencyRatesPage;
import po.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddNewCurrencyRate extends BaseTest {


	@Test
	public void addNewCurrencyRate() {
		CurrencyRatesPage currencies = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.goToSettings()
			.currencyRates()
			.addCurrencyRate()
			.setRate("1.2")
			.save();

		// Verify that the added currency rate's currency is "USD".
		assertEquals("USD", currencies.getRateCurrency());
		// Verify that the added currency rate's value is "1.20".
		assertEquals("1.20", currencies.getRateValue());
	}

}
