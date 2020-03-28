package io.ioco.invoiceapi.repositories;

import org.springframework.data.repository.CrudRepository;
import io.ioco.invoiceapi.entities.Invoice;


public interface InvoiceRepository extends CrudRepository<Invoice, Long>{
	
	public Invoice findByClient(String client);

}
