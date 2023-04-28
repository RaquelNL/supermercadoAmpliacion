package com.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hibernate.model.Producto;
import com.hibernate.util.HibernateUtil;
import com.mysql.cj.Query;

public class ProductoDAO {

	/**
	 * INSERCIÓN
	 */
	
	public void insertProducto(Producto p) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(p);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	/**
	 * ACTUALIZACIÓN
	 */
	
	public void updateProducto(Producto p) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(p);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	/**
	 * BORRADO
	 */
	
	public void deleteProducto(int id) {
		Transaction transaction = null;
		Producto p = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			p = session.get(Producto.class, id);
			session.remove(p);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	/**
	 * SELECCIÓN SIMPLE
	 */
	
	public Producto selectProductoById(int id) {
		Transaction transaction = null;
		Producto p = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			p = session.get(Producto.class, id);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return p;
	}
	
	/**
	 * SELECCIÓN MÚLTIPLE
	 */
	
	public List<Producto> selectAllProducto() {
		Transaction transaction = null;
		List<Producto> productos = null;
		Producto p = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			productos = session.createQuery("select p from Producto p join  p.categoria c", Producto.class).getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return productos;
	}
	
	/**
	 * SELECCIÓN DE PRODUCTOS SEGÚN LA CATEGORÍA
	 */
	
	public List<Producto> selectAllProductoCat() {
		Transaction transaction = null;
		List<Producto> productos = null;
		Producto p = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			productos = session.createQuery("select p from Producto p where p.categoria.id = :id", Producto.class).getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return productos;
	}
	
}