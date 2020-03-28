package io.ioco.invoiceapi.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import io.ioco.invoiceapi.controllers.InvoiceController;
import io.ioco.invoiceapi.entities.Invoice;
import io.ioco.invoiceapi.entities.LineItem;
import io.ioco.invoiceapi.repositories.InvoiceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class InvoiceApiIntegrationTest {
	

	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private InvoiceController invoiceController;

	Date date = parseDate("2014-02-14");
	
	
	@Test
	public void testInvoiceApi() {
		//Adding Invoice 1 Joe
		Invoice expectedInvoice1 = new Invoice();
		getExpectedInvoiceValues(expectedInvoice1,"Joe", new Long(15), date);
		expectedInvoice1.setLineItems(getLineItems1());
		BigDecimal expectedSubTotal1 = getExpectedSubTotal(expectedInvoice1.getLineItems());
		BigDecimal expectedVat1 = getExpectedVatTotal(expectedSubTotal1,expectedInvoice1.getVatRate());
		BigDecimal expectedTotal1 = getExpectedTotal(expectedSubTotal1,expectedVat1);
		invoiceController.addInvoice(expectedInvoice1);
		
		//Adding Invoice 2 Max
		Invoice expectedInvoice2 = new Invoice();
		getExpectedInvoiceValues(expectedInvoice2,"Max", new Long(15), date);
		expectedInvoice1.setLineItems(getLineItems1());
		BigDecimal expectedSubTotal2 = getExpectedSubTotal(expectedInvoice2.getLineItems());
		BigDecimal expectedVat2 = getExpectedVatTotal(expectedSubTotal2,expectedInvoice2.getVatRate());
		BigDecimal expectedTotal2 = getExpectedTotal(expectedSubTotal2,expectedVat2);
		invoiceController.addInvoice(expectedInvoice2);
		
		
		Invoice fromDbInvoice1 = invoiceRepository.findById(expectedInvoice1.getId()).orElse(null); 
		Invoice fromDbInvoice2 = invoiceRepository.findById(expectedInvoice2.getId()).orElse(null); 
		
		assertThat(fromDbInvoice1.getClient()).isEqualTo(expectedInvoice1.getClient());	
		assertThat(fromDbInvoice1.getLineItems().size()).isEqualTo(expectedInvoice1.getLineItems().size());
		assertThat(fromDbInvoice1.getSubtotal()).isEqualTo(expectedSubTotal1);	
		assertThat(fromDbInvoice1.getVat()).isEqualTo(expectedVat1);
		assertThat(fromDbInvoice1.getTotal()).isEqualTo(expectedTotal1);
		
		assertThat(fromDbInvoice2.getClient()).isEqualTo(expectedInvoice2.getClient());	
		assertThat(fromDbInvoice2.getLineItems().size()).isEqualTo(expectedInvoice2.getLineItems().size());
		assertThat(fromDbInvoice2.getSubtotal()).isEqualTo(expectedSubTotal2);	
		assertThat(fromDbInvoice2.getVat()).isEqualTo(expectedVat2);
		assertThat(fromDbInvoice2.getTotal()).isEqualTo(expectedTotal2);
		
		
		
		
	}
	
	
	private void getExpectedInvoiceValues(Invoice invoice, String client, Long vatRate, Date date) {
		invoice.setClient(client);
		invoice.setVatRate(vatRate);
		invoice.setInvoiceDate(date);
	}
	
	
	private void addLineItemsToInvoice(List<LineItem> lineItems, Invoice invoice) {
		lineItems.forEach(l -> {
			invoice.addLineItem(l);
		});
	}
	
	private BigDecimal getExpectedTotal(BigDecimal expectedSubTotal, BigDecimal expectedVatTotal) {
		BigDecimal result = expectedSubTotal.add(expectedVatTotal);
		result = result.setScale(2, RoundingMode.HALF_UP);
		return result;
	}
	
	private BigDecimal getExpectedVatTotal(BigDecimal expectedSubTotal, Long vatRate){
		
		BigDecimal result = expectedSubTotal.multiply(new BigDecimal(vatRate))
				.divide(new BigDecimal(100));
		result = result.setScale(2, RoundingMode.HALF_UP);
		return result;
	}
	
	private List<LineItem> getLineItems1(){
		List<LineItem> lineItems = new ArrayList<>();
		lineItems.add(new LineItem(null, new Long(10),"Chips", new BigDecimal(2),null));
		lineItems.add(new LineItem(null, new Long(3),"Candy", new BigDecimal(5),null));
		return lineItems;
	}
	
	private List<LineItem> getLineItems2(){
		List<LineItem> lineItems = new ArrayList<>();
		lineItems.add(new LineItem(null, new Long(2),"Drinks", new BigDecimal(10),null));
		lineItems.add(new LineItem(null, new Long(5),"Chocs", new BigDecimal(5),null));
		return lineItems;
	}
	
	private BigDecimal getExpectedSubTotal(List<LineItem> lineItems) {
		BigDecimal result  = lineItems.stream()
				.map(p -> p.getUnitPrice().multiply(new BigDecimal(p.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		result = result.setScale(2, RoundingMode.HALF_UP);
		return result;
	}
	
	
	private Invoice getInvoice(String client, Long vatRate, Date date) {
		Invoice invoice = new Invoice();
		invoice.setClient(client);
		invoice.setVatRate(vatRate);
		invoice.setInvoiceDate(date);
		return invoice;
	}
	
	
	private static Date parseDate(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			return null;
	    }
    }
	
	

}
