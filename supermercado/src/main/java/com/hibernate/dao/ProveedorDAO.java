package com.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hibernate.model.Categoria;
import com.hibernate.model.Producto;
import com.hibernate.model.Proveedor;
import com.hibernate.util.HibernateUtil;

public class ProveedorDAO {
	/**
	 * INSERCIÓN
	 */
	
	public void insertProveedor(Proveedor pr) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(pr);
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
	
	public void updateProveedor(Proveedor pr) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(pr);
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
	
	public void deleteProveedor(int id) {
		Transaction transaction = null;
		Proveedor pr = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			pr = session.get(Proveedor.class, id);
			session.remove(pr);
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
	
	public Proveedor selectProveedorById(int id) {
		Transaction transaction = null;
		Proveedor pr = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			pr = session.get(Proveedor.class, id);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return pr;
	}
	
	/**
	 * SELECCIÓN MÚLTIPLE
	 */
	
	public List<Proveedor> selectAllProveedor() {
		Transaction transaction = null;
		List<Proveedor> proveedores = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			proveedores = session.createQuery("from Proveedor", Proveedor.class).getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return proveedores;
	}
}