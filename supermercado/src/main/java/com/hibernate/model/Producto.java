package com.hibernate.model;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="producto")
public class Producto {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int codprod;
	
	@Column(name="nombre")
	private String nomProd;
	
	@Column(name="precio")
	private double precio;
	
	@Column(name="stock")
	private int stock;
	
	@ManyToOne
	@JoinColumn(name = "categoria_id", referencedColumnName = "id", nullable = false)
	private Categoria categoria;
	
	@Column(name="caducidad")
	private LocalDate caducidad;
	
	@Column(name="oferta")
	private Double oferta;
	
	public Producto() {
		
	}
	
	public Producto(String nomProd, Categoria categoria, double precio, int stock, LocalDate caducidad, Double oferta) {
		super();
		this.nomProd = nomProd;
		this.categoria = categoria;
		this.precio = precio;
		this.stock = stock;
		this.caducidad = caducidad;
		this.oferta = oferta;

		
	}

	public int getCodprod() {
		return codprod;
	}

	public void setCodprod(int codprod) {
		this.codprod = codprod;
	}

	public String getNomProd() {
		return nomProd;
	}

	public void setNomProd(String nomProd) {
		this.nomProd = nomProd;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int d) {
		this.stock = d;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public LocalDate getCaducidad() {
		return caducidad;
	}

	public void setCaducidad(LocalDate caducidad) {
		this.caducidad = caducidad;
	}

	public Double getOferta() {
		return oferta;
	}

	public void setOferta(Double oferta) {
		this.oferta = oferta;
	}
	

}
