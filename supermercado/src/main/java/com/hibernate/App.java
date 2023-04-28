package com.hibernate;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hibernate.model.Producto;
import com.hibernate.dao.ProductoDAO;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class App {

	private JFrame frmAlmacnSupermercado;
	private JTable tableProductos;
	private JTable table;
	private JTextField textFieldId;
	private JTextField textFieldCat;
	private JTextField textFieldNomProd;
	private JTextField textFieldPrecio;
	private JTextField textFieldEnStock;
	
	void limpiarTexto() {
		textFieldId.setText("");
		textFieldNomProd.setText("");
		textFieldCat.setText("");
		textFieldPrecio.setText("");
		textFieldEnStock.setText("");
	}
	

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
		
		JButton btnGuardarProd = new JButton("GUARDAR");
		btnGuardarProd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Producto producto = new Producto(textFieldNomProd.getText(), Integer.parseInt(textFieldPrecio.getText()), 
						Integer.parseInt(textFieldEnStock.getText()), Integer.parseInt(textFieldCat.getText()));
				productoDAO.insertProducto(producto);
				JOptionPane.showMessageDialog(null, "Producto añadido");
				limpiarTexto();
			}
		});
		btnGuardarProd.setBounds(350, 346, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(btnGuardarProd);

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
		    row[1] = p.getCategoria();
		    row[2] = p.getNomProd();
		    row[3] = p.getPrecio();
		    row[4] = p.getStock();
		    model.addRow(row);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(245, 12, 625, 123);
		frmAlmacnSupermercado.getContentPane().add(scrollPane);
		
		table = new JTable(model);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				textFieldId.setText(model.getValueAt(index, 0).toString());
				textFieldNomProd.setText(model.getValueAt(index, 1).toString());
				textFieldCat.setText(model.getValueAt(index, 2).toString());
				textFieldPrecio.setText(model.getValueAt(index, 3).toString());
				textFieldEnStock.setText(model.getValueAt(index, 4).toString());
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnActualizarProd = new JButton("ACTUALIZAR");
		btnActualizarProd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Producto productoActualizar = productoDAO.selectProductoById(Integer.parseInt(textFieldId.getText()));
				productoActualizar.setNomProd(textFieldNomProd.getText());
				productoActualizar.setCategoria(Integer.parseInt(textFieldCat.getText()));
				productoActualizar.setPrecio(Integer.parseInt(textFieldPrecio.getText()));
				productoActualizar.setStock(Integer.parseInt(textFieldEnStock.getText()));
				productoDAO.updateProducto(productoActualizar);
				JOptionPane.showMessageDialog(null, "Producto actualizado");
				limpiarTexto();
			}
		});
		btnActualizarProd.setBounds(514, 346, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(btnActualizarProd);
		
		JButton btnBorrarProd = new JButton("BORRAR");
		btnBorrarProd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				productoDAO.deleteProducto(Integer.parseInt(textFieldId.getText()));
				JOptionPane.showMessageDialog(null, "Producto borrado");
				limpiarTexto();
			}
		});
		btnBorrarProd.setBounds(672, 346, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(btnBorrarProd);
		
		JLabel lblCategoria = new JLabel("ELIGE LA OPCIÓN:");
		lblCategoria.setBounds(47, 40, 157, 13);
		frmAlmacnSupermercado.getContentPane().add(lblCategoria);
		
		JComboBox comboBoxCat = new JComboBox();
		comboBoxCat.setBounds(47, 65, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(comboBoxCat);
		
		JLabel lblOferta = new JLabel("ELIGE LA OFERTA:");
		lblOferta.setBounds(47, 417, 157, 13);
		frmAlmacnSupermercado.getContentPane().add(lblOferta);
		
		JComboBox comboBoxOferta = new JComboBox();
		comboBoxOferta.setBounds(47, 444, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(comboBoxOferta);
		
		
		JLabel lblEligeLaCategora = new JLabel("ELIGE LA CATEGORÍA:");
		lblEligeLaCategora.setBounds(47, 118, 157, 13);
		frmAlmacnSupermercado.getContentPane().add(lblEligeLaCategora);
		
		JComboBox comboBoxCat_1 = new JComboBox();
		comboBoxCat_1.setBounds(47, 143, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(comboBoxCat_1);
		
		JLabel lblIdProd = new JLabel("ID:");
		lblIdProd.setBounds(271, 157, 70, 15);
		frmAlmacnSupermercado.getContentPane().add(lblIdProd);
		
		JLabel lblCat = new JLabel("CATEGORÍA:");
		lblCat.setBounds(271, 190, 114, 15);
		frmAlmacnSupermercado.getContentPane().add(lblCat);
		
		JLabel lblNomProd = new JLabel("NOMBRE:");
		lblNomProd.setBounds(271, 217, 70, 15);
		frmAlmacnSupermercado.getContentPane().add(lblNomProd);
		
		JLabel lblPrecio = new JLabel("PRECIO:");
		lblPrecio.setBounds(271, 244, 70, 15);
		frmAlmacnSupermercado.getContentPane().add(lblPrecio);
		
		JLabel lblEnStock = new JLabel("EN STOCK:");
		lblEnStock.setBounds(271, 271, 93, 15);
		frmAlmacnSupermercado.getContentPane().add(lblEnStock);
		
		textFieldId = new JTextField();
		textFieldId.setBounds(384, 155, 180, 19);
		frmAlmacnSupermercado.getContentPane().add(textFieldId);
		textFieldId.setColumns(10);
		
		textFieldCat = new JTextField();
		textFieldCat.setColumns(10);
		textFieldCat.setBounds(384, 188, 180, 19);
		frmAlmacnSupermercado.getContentPane().add(textFieldCat);
		
		textFieldNomProd = new JTextField();
		textFieldNomProd.setColumns(10);
		textFieldNomProd.setBounds(384, 217, 180, 19);
		frmAlmacnSupermercado.getContentPane().add(textFieldNomProd);
		
		textFieldPrecio = new JTextField();
		textFieldPrecio.setColumns(10);
		textFieldPrecio.setBounds(384, 242, 180, 19);
		frmAlmacnSupermercado.getContentPane().add(textFieldPrecio);
		
		textFieldEnStock = new JTextField();
		textFieldEnStock.setColumns(10);
		textFieldEnStock.setBounds(384, 267, 180, 19);
		frmAlmacnSupermercado.getContentPane().add(textFieldEnStock);
		
		
		
		
	}
}
