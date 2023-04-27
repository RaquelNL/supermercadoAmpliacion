package com.hibernate;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import com.hibernate.model.Producto;
import com.hibernate.dao.ProductoDAO;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class App {

	private JFrame frmAlmacnSupermercado;
	private JTable tableProductos;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frmAlmacnSupermercado.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		ProductoDAO productoDAO = new ProductoDAO();
		
		frmAlmacnSupermercado = new JFrame();
		frmAlmacnSupermercado.setTitle("Almacén Supermercado");
		frmAlmacnSupermercado.setBounds(100, 100, 1121, 619);
		frmAlmacnSupermercado.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAlmacnSupermercado.getContentPane().setLayout(null);
		
		JLabel lblProductos = new JLabel("TODOS LOS PRODUCTOS");
		lblProductos.setBounds(408, 40, 211, 13);
		frmAlmacnSupermercado.getContentPane().add(lblProductos);
		
		JButton btnGuardarProd = new JButton("GUARDAR");
		btnGuardarProd.setBounds(229, 210, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(btnGuardarProd);
		
		JButton btnActualizarProd = new JButton("ACTUALIZAR");
		btnActualizarProd.setBounds(412, 210, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(btnActualizarProd);
		
		JButton btnBorrarProd = new JButton("BORRAR");
		btnBorrarProd.setBounds(611, 210, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(btnBorrarProd);
		
		JLabel lblCategoria = new JLabel("ELIGE LA CATEGORÍA:");
		lblCategoria.setBounds(47, 278, 157, 13);
		frmAlmacnSupermercado.getContentPane().add(lblCategoria);
		
		JComboBox comboBoxCat = new JComboBox();
		comboBoxCat.setBounds(47, 316, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(comboBoxCat);
		
		JLabel lblOferta = new JLabel("ELIGE LA OFERTA:");
		lblOferta.setBounds(47, 417, 157, 13);
		frmAlmacnSupermercado.getContentPane().add(lblOferta);
		
		JComboBox comboBoxOferta = new JComboBox();
		comboBoxOferta.setBounds(47, 444, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(comboBoxOferta);
		
		JLabel lblSinStock = new JLabel("PRODUCTOS SIN STOCK:");
		lblSinStock.setBounds(758, 278, 187, 13);
		frmAlmacnSupermercado.getContentPane().add(lblSinStock);
		
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Categoría");
		model.addColumn("Nombre");
		model.addColumn("Precio");
		model.addColumn("En Stock");
		
		List<Producto> productos = productoDAO.selectAllProducto();
		for (Producto p : productos) {
		    Object[] row = new Object[5];
		    row[0] = p.getCodprod();
		    row[1] = p.getNomProd();
		    row[2] = p.getCategoria();
		    row[3] = p.getPrecio();
		    row[4] = p.getStock();
		    model.addRow(row);
		}
		
		tableProductos = new JTable(model);
		tableProductos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableProductos.setBounds(153, 185, 684, -111);
		frmAlmacnSupermercado.getContentPane().add(tableProductos);
		
		JScrollPane scrollPaneProductos = new JScrollPane(tableProductos);
		scrollPaneProductos.setBounds(153, 185, 684, -111);
		frmAlmacnSupermercado.getContentPane().add(scrollPaneProductos);
	}
}
