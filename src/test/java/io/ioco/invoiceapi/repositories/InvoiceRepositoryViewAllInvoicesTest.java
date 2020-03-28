package io.ioco.invoiceapi.repositories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import io.ioco.invoiceapi.entities.Invoice;

@RunWith(SpringRunner.class)
@DataJpaTest
//@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class InvoiceRepositoryViewAllInvoicesTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private InvoiceRepository invoiceRepository;

	Date date = parseDate("2014-02-14");

	@Test
	public void testViewAllInvoices() {
		Invoice invoice1 = getInvoice("Max", new Long(15), date);
		Invoice invoice2 = getInvoice("Joe", new Long(15), date);

		Invoice saveInDb1 = entityManager.persist(invoice1);
		System.out.println(saveInDb1);
		Invoice saveInDb2 = entityManager.persist(invoice2);
		System.out.println(saveInDb2);

		List<Invoice> invoicesFromDb = viewAllInvoices();
		List<Invoice> invoicesExpected = expectedInvoices();

		assertThat(invoicesFromDb.get(0).getClient()).isEqualTo(invoicesExpected.get(0).getClient());
		assertThat(invoicesFromDb.get(0).getVatRate()).isEqualTo(invoicesExpected.get(0).getVatRate());
		assertThat(invoicesFromDb.get(0).getInvoiceDate()).isEqualTo(invoicesExpected.get(0).getInvoiceDate());

		assertThat(invoicesFromDb.get(1).getClient()).isEqualTo(invoicesExpected.get(1).getClient());
		assertThat(invoicesFromDb.get(1).getVatRate()).isEqualTo(invoicesExpected.get(1).getVatRate());
		assertThat(invoicesFromDb.get(1).getInvoiceDate()).isEqualTo(invoicesExpected.get(1).getInvoiceDate());

	}

	private Invoice getInvoice(String client, Long vatRate, Date date) {
		Invoice invoice = new Invoice();
		invoice.setClient(client);
		invoice.setVatRate(vatRate);
		invoice.setInvoiceDate(date);
		return invoice;
	}

	private List<Invoice> expectedInvoices() {
		List<Invoice> invoices = new ArrayList<>();
		invoices.add(new Invoice(new Long(1), "Max", new Long(15), date));
		invoices.add(new Invoice(new Long(2), "Joe", new Long(15), date));
		return invoices;
	}

	private List<Invoice> viewAllInvoices() {
		List<Invoice> invoices = new ArrayList<>();
		invoiceRepository.findAll().forEach(invoices::add);
		return invoices;

	}

	private static Date parseDate(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			return null;
	    }
    }

}
