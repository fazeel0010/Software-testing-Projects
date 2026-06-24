package tests;

import org.junit.jupiter.api.Test;
import po.CurrencyRatesPage;
import po.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SetReferenceCurrency extends BaseTest {


	@Test
	public void setReference() {
		CurrencyRatesPage currencies = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.goToSettings()
			.currencyRates()
			.changeReferenceCurrency()
			.selectCurrency("EUR - Euro")
			.save();

		// Verify that the reference currency is set to "Reference currency: EUR".
		assertEquals("Reference currency: EUR", currencies.getReferenceCurrency());

	}


}
