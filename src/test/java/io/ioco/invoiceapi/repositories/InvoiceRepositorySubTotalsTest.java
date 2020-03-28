package io.ioco.invoiceapi.repositories;

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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import io.ioco.invoiceapi.entities.Invoice;
import io.ioco.invoiceapi.entities.LineItem;

@RunWith(SpringRunner.class)
@DataJpaTest
//@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class InvoiceRepositorySubTotalsTest {
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private InvoiceRepository invoiceRepository;

	Date date = parseDate("2014-02-14");
	
	@Test
	public void testSubTotalAmount() {
		List<LineItem> lineItems = getLineItems();
		Invoice invoice = getInvoice("Max", new Long(15), date);
		addLineItemsToInvoice(lineItems, invoice);
		BigDecimal expectedSubTotal = getExpectedSubTotal(lineItems);
		
		Invoice saveInDbInvoice = entityManager.persist(invoice);
		Invoice getFromDbInvoice = invoiceRepository.findById(saveInDbInvoice.getId()).orElse(null); 
		BigDecimal subTotalfromDb = getFromDbInvoice.getSubtotal();
		
		assertThat(subTotalfromDb).isEqualTo(expectedSubTotal);
		
	}
	

	private void addLineItemsToInvoice(List<LineItem> lineItems, Invoice invoice) {
		lineItems.forEach(l -> {
			invoice.addLineItem(l);
		});
	}
	
	
	private List<LineItem> getLineItems(){
		List<LineItem> lineItems = new ArrayList<>();
		lineItems.add(new LineItem(null, new Long(10),"Chocs", new BigDecimal(5),null));
		lineItems.add(new LineItem(null, new Long(20),"Chips", new BigDecimal(5),null));
		lineItems.add(new LineItem(null, new Long(5),"drinks", new BigDecimal(10),null));
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
