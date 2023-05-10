package com.hibernate.dao;

import java.time.LocalDate;

import java.sql.Date;

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
	 * SELECCIÓN DE PRODUCTOS SEGÚN BEBIDAS
	 */
	
	public List<Producto> selectAllBebidas() {
		Transaction transaction = null;
		List<Producto> productos = null;
		Producto p = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			productos = session.createQuery("select p from Producto p where p.categoria.id = 1", Producto.class).getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return productos;
	}
	
	/**
	 * SELECCIÓN DE PRODUCTOS SEGÚN CARNES
	 */
	
	public List<Producto> selectAllCarnes() {
		Transaction transaction = null;
		List<Producto> productos = null;
		Producto p = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			productos = session.createQuery("select p from Producto p where p.categoria.id = 2", Producto.class).getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return productos;
	}

	/**
	 * SELECCIÓN DE PRODUCTOS SEGÚN PESCADOS
	 */
	
	public List<Producto> selectAllPescados() {
		Transaction transaction = null;
		List<Producto> productos = null;
		Producto p = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			productos = session.createQuery("select p from Producto p where p.categoria.id = 3", Producto.class).getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return productos;
	}

	
	/**
	 * SELECCIÓN PRODUCTOS SIN STOCK
	 */
	
	public List<Producto> selectProductoSinStock() {
		Transaction transaction = null;
		List<Producto> productos = null;
		Producto p = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			productos = session.createQuery("select p from Producto p join  p.categoria c where p.stock = 0", Producto.class).getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return productos;
	}
	
	/**
	 * SELECCIÓN PRODUCTOS SIN STOCK CON ID
	 */
	
	public List<Producto> selectProductoSinStockId() {
		Transaction transaction = null;
		List<Producto> productos = null;
		Producto p = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			productos = session.createQuery("select p.id from Producto p join  p.categoria c where p.stock = 0", Producto.class).getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return productos;
	}
	
	/**
	 * SELECCIÓN PRODUCTOS CADUCADOS
	 */
	
	public List<Producto> selectProductosCaducados(LocalDate fechaActual) {
		Transaction transaction = null;
		List<Producto> productos = null;
		Producto p = null;
		
		//Date fechaActualToSQL = Date.valueOf(fechaActual);
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			productos = session.createQuery("select p from Producto p join  p.categoria c where p.caducidad <= :fechaActual", Producto.class)
				.setParameter("fechaActual", fechaActual)
				.getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return productos;
	}
	
}