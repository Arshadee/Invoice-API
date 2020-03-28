package io.ioco.invoiceapi.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Date;
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
import io.ioco.invoiceapi.services.InvoiceService;

@RunWith(SpringRunner.class)
@DataJpaTest
//@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class InvoiceRepositorySaveTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	
	@Test
	public void testSave() {
		Invoice invoice = getInvoice();
		Invoice saveInDb = entityManager.persist(invoice);
		Invoice getFromDb = invoiceRepository.findById(saveInDb.getId()).orElse(null);
		System.out.println("testSave() "+getFromDb);
		assertThat(getFromDb).isEqualTo(saveInDb);
		
	}
	
	
	private Invoice getInvoice() {
		Invoice invoice = new Invoice();
		invoice.setClient("Max");
		invoice.setInvoiceDate(new Date());
		return invoice;
	}
	
	
	
}
