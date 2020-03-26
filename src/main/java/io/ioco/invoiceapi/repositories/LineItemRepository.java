package io.ioco.invoiceapi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.ioco.invoiceapi.entities.LineItem;



public interface LineItemRepository extends CrudRepository<LineItem, Long>{
	
	public List<LineItem> findByInvoiceId(Long invoiceId);

}
