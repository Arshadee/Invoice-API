package io.ioco.invoiceapi.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class LineItem {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Long quantity;
	private String description;
	@Column(name="unitPrice", unique = false, scale = 2, precision = 10)
	private BigDecimal unitPrice;
	@ManyToOne
	@JoinColumn(name="INVOICE_ID")
	private Invoice invoice;
	
	public LineItem() {}

	public LineItem(Long id, Long quantity, String description, BigDecimal unitPrice, Invoice invoice) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.description = description;
		this.unitPrice = unitPrice;
		this.invoice = invoice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	@Override
	public String toString() {
		return "LineItem [id=" + id + ", quantity=" + quantity + ", description=" + description + ", unitPrice="
				+ unitPrice + ", invoice=" + invoice + "]";
	}
	
	

}
