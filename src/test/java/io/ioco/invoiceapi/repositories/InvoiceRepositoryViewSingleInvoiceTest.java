package io.ioco.invoiceapi.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

@RunWith(SpringRunner.class)
@DataJpaTest
//@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class InvoiceRepositoryViewSingleInvoiceTest {
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private InvoiceRepository invoiceRepository;

	Date date = parseDate("2014-02-14");

	
	@Test
	public void testViewInvSingleInvoices() {
		Invoice expectedInvoice = getInvoice("Max", new Long(15), date);
		expectedInvoice.setId(new Long(1));
		
		Invoice invoice  = getInvoice("Max", new Long(15), date);
		entityManager.persist(invoice);
		Invoice fromDatabaseInvoice = invoiceRepository.findById(new Long(1)).orElse(null);
		
		assertThat(fromDatabaseInvoice.getId()).isEqualTo(expectedInvoice.getId());
		assertThat(fromDatabaseInvoice.getClient()).isEqualTo(expectedInvoice.getClient());
		assertThat(fromDatabaseInvoice.getVatRate()).isEqualTo(expectedInvoice.getVatRate());
		assertThat(fromDatabaseInvoice.getInvoiceDate()).isEqualTo(expectedInvoice.getInvoiceDate());
		
		
	}
	
	private Invoice getInvoice(String client, Long vatRate, Date date) {
		Invoice invoice = new Invoice();
		//invoice.setId(new Long(1));
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
