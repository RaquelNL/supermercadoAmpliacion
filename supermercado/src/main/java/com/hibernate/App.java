package com.hibernate;

import java.awt.Component;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hibernate.model.Categoria;
import com.hibernate.model.Producto;
import com.hibernate.dao.ProductoDAO;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class App {

	private JFrame frmAlmacnSupermercado;
	private JTable tableProductos;
	private JTable table;
	private JTextField textFieldId;
	private JTextField textFieldNomProd;
	private JTextField textFieldPrecio;
	private JTextField textFieldEnStock;
	private JTextField textField;
	
	void limpiarTexto() {
		textFieldId.setText("");
		textFieldNomProd.setText("");
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

		JLabel lblEligeLaCategoria = new JLabel("ELIGE LA CATEGORÍA:");
		lblEligeLaCategoria.setVisible(false);
		lblEligeLaCategoria.setBounds(47, 118, 157, 13);
		frmAlmacnSupermercado.getContentPane().add(lblEligeLaCategoria);

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Categoría");
		model.addColumn("Nombre");
		model.addColumn("Precio");
		model.addColumn("En Stock");
		
	
		JComboBox comboBoxCat = new JComboBox();
		comboBoxCat.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (comboBoxCat.getSelectedIndex() == 0) {
					model.setRowCount(0);
					List<Producto> productos = productoDAO.selectAllBebidas();
					for (Producto p : productos) {
					    Object[] row = new Object[5];
					    row[0] = p.getCodprod();
					    row[1] = p.getCategoria().getNombre();
					    row[2] = p.getNomProd();
					    row[3] = p.getPrecio();
					    row[4] = p.getStock();
					    model.addRow(row);
					   
					    
					}
					
				} else if (comboBoxCat.getSelectedIndex() == 1) {
					model.setRowCount(0);
					List<Producto> productos = productoDAO.selectAllCarnes();
					for (Producto p : productos) {
					    Object[] row = new Object[5];
					    row[0] = p.getCodprod();
					    row[1] = p.getCategoria().getNombre();
					    row[2] = p.getNomProd();
					    row[3] = p.getPrecio();
					    row[4] = p.getStock();
					    model.addRow(row);

					   
					}
				} else {
					model.setRowCount(0);
					List<Producto> productos = productoDAO.selectAllPescados();
					for (Producto p : productos) {
					    Object[] row = new Object[5];
					    row[0] = p.getCodprod();
					    row[1] = p.getCategoria().getNombre();
					    row[2] = p.getNomProd();
					    row[3] = p.getPrecio();
					    row[4] = p.getStock();
					    model.addRow(row);
					   
					}
				}
				
				
			}
			
			
		});
		comboBoxCat.setModel(new DefaultComboBoxModel(new String[] {"Bebidas", "Carnes", "Pescados", "Limpieza"}));
		comboBoxCat.setVisible(false);
		comboBoxCat.setBounds(47, 143, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(comboBoxCat);
		
		JLabel lblProductosStock = new JLabel("PEDIR PRODUCTOS:");
		lblProductosStock.setVisible(false);
		lblProductosStock.setBounds(813, 146, 201, 15);
		frmAlmacnSupermercado.getContentPane().add(lblProductosStock);
		
		JLabel lblEligeProducto = new JLabel("Elige el producto:");
		lblEligeProducto.setVisible(false);
		lblEligeProducto.setBounds(813, 190, 148, 15);
		frmAlmacnSupermercado.getContentPane().add(lblEligeProducto);
		
		JComboBox comboBoxProducto = new JComboBox();
		comboBoxProducto.setVisible(false);
		List<Producto> productosSinStock = productoDAO.selectProductoSinStockId();
		comboBoxProducto.addItem(productosSinStock);
		comboBoxProducto.setBounds(941, 185, 117, 24);
		frmAlmacnSupermercado.getContentPane().add(comboBoxProducto);
		
		JLabel lblEligeLaCantidad = new JLabel("Elige la cantidad:");
		lblEligeLaCantidad.setVisible(false);
		lblEligeLaCantidad.setBounds(813, 217, 128, 15);
		frmAlmacnSupermercado.getContentPane().add(lblEligeLaCantidad);
		
		textField = new JTextField();
		textField.setVisible(false);		
		textField.setBounds(941, 217, 114, 19);
		frmAlmacnSupermercado.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnPedir = new JButton("PEDIR");
		btnPedir.setVisible(false);
		btnPedir.setBounds(885, 286, 117, 25);
		frmAlmacnSupermercado.getContentPane().add(btnPedir);
		
		JComboBox comboBoxOpcion = new JComboBox();
		comboBoxOpcion.setModel(new DefaultComboBoxModel(new String[] {"Todos los Productos", "Según la Categoría", "Productos sin Stock"}));
		comboBoxOpcion.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (comboBoxOpcion.getSelectedIndex() == 0) {
					lblEligeLaCategoria.setVisible(false);
					comboBoxCat.setVisible(false);
					
					model.setRowCount(0);
					List<Producto> productos = productoDAO.selectAllProducto();
					for (Producto p : productos) {
					    Object[] row = new Object[5];
					    row[0] = p.getCodprod();
					    row[1] = p.getCategoria().getNombre();
					    row[2] = p.getNomProd();
					    row[3] = p.getPrecio();
					    row[4] = p.getStock();
					    model.addRow(row);

					}
					
					
				} else if (comboBoxOpcion.getSelectedIndex() == 1) {
					lblEligeLaCategoria.setVisible(true);
					comboBoxCat.setVisible(true);
					
				} else {
					lblEligeLaCategoria.setVisible(false);
					comboBoxCat.setVisible(false);
					lblProductosStock.setVisible(true);
					lblEligeProducto.setVisible(true);
					comboBoxProducto.setVisible(true);
					lblEligeLaCantidad.setVisible(true);
					textField.setVisible(true);
					btnPedir.setVisible(true);
					
					model.setRowCount(0);
					List<Producto> productos = productoDAO.selectProductoSinStock();
					for (Producto p : productos) {
					    Object[] row = new Object[5];
					    row[0] = p.getCodprod();
					    row[1] = p.getCategoria().getNombre();
					    row[2] = p.getNomProd();
					    row[3] = p.getPrecio();
					    row[4] = p.getStock();
					    model.addRow(row);
					   
					   
					    
					}
					
				}
				
			}
		});
		comboBoxOpcion.setBounds(47, 65, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(comboBoxOpcion);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(245, 12, 625, 123);
		frmAlmacnSupermercado.getContentPane().add(scrollPane);
		
		table = new JTable(model);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				if (index != -1) {
					DefaultTableModel model = (DefaultTableModel) table.getModel();
				    if (model instanceof DefaultTableModel && model.getColumnCount() == 5) {
				        textFieldId.setText(model.getValueAt(index, 0).toString());
				        comboBoxCat.setToolTipText(model.getValueAt(index, 1).toString());
				        textFieldNomProd.setText(model.getValueAt(index, 2).toString());
				        textFieldPrecio.setText(model.getValueAt(index, 3).toString());
				        textFieldEnStock.setText(model.getValueAt(index, 4).toString());
				    }
				 }
			}
		});
		scrollPane.setViewportView(table);
		
		
		
		JButton btnGuardarProd = new JButton("GUARDAR");
		btnGuardarProd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				Categoria categoria = new Categoria();
				categoria.setIdcat(comboBoxCat.getSelectedIndex()+1);
				categoria.setNombre(comboBoxCat.getSelectedItem().toString());

				Producto producto = new Producto(textFieldNomProd.getText(),
                        Double.parseDouble(textFieldPrecio.getText()),
                        Integer.parseInt(textFieldEnStock.getText()),
                        categoria);
					productoDAO.insertProducto(producto);
					JOptionPane.showMessageDialog(null, "Producto añadido");
					limpiarTexto();
					model.setRowCount(0);
					List<Producto> productos = productoDAO.selectAllProducto();
					for (Producto p : productos) {
					    Object[] row = new Object[5];
					    row[0] = p.getCodprod();
					    row[1] = p.getCategoria().getNombre();
					    row[2] = p.getNomProd();
					    row[3] = p.getPrecio();
					    row[4] = p.getStock();
					    model.addRow(row);
			}
		}});
		btnGuardarProd.setBounds(207, 461, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(btnGuardarProd);
		
		JButton btnActualizarProd = new JButton("ACTUALIZAR");
		btnActualizarProd.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        Producto productoActualizar = productoDAO.selectProductoById(Integer.parseInt(textFieldId.getText()));
		        productoActualizar.setNomProd(textFieldNomProd.getText());
		        Categoria c = new Categoria();
		        c.setIdcat(comboBoxCat.getSelectedIndex()+1);
		        c.setNombre(comboBoxCat.getSelectedItem().toString());
		        productoActualizar.setCategoria(c);
		        productoActualizar.setPrecio(Double.parseDouble(textFieldPrecio.getText()));
		        productoActualizar.setStock(Integer.parseInt(textFieldEnStock.getText()));
		        productoDAO.updateProducto(productoActualizar);
		        JOptionPane.showMessageDialog(null, "Producto actualizado");
		        limpiarTexto();
		        model.setRowCount(0);
				List<Producto> productos = productoDAO.selectAllProducto();
				for (Producto p : productos) {
				    Object[] row = new Object[5];
				    row[0] = p.getCodprod();
				    row[1] = p.getCategoria().getNombre();
				    row[2] = p.getNomProd();
				    row[3] = p.getPrecio();
				    row[4] = p.getStock();
				    model.addRow(row);
			
		    }
		}});
		btnActualizarProd.setBounds(371, 461, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(btnActualizarProd);
		
		
		JButton btnBorrarProd = new JButton("BORRAR");
		btnBorrarProd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				productoDAO.deleteProducto(Integer.parseInt(textFieldId.getText()));
				JOptionPane.showMessageDialog(null, "Producto borrado");
				//btnTablaActSinStock.doClick();
				limpiarTexto();
			}
		});
		btnBorrarProd.setBounds(529, 461, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(btnBorrarProd);
		
		JLabel lblCategoria = new JLabel("ELIGE LA OPCIÓN:");
		lblCategoria.setBounds(47, 40, 157, 13);
		frmAlmacnSupermercado.getContentPane().add(lblCategoria);
		
		
		
		JLabel lblOferta = new JLabel("ELIGE LA OFERTA:");
		lblOferta.setBounds(47, 417, 157, 13);
		frmAlmacnSupermercado.getContentPane().add(lblOferta);
		
		JComboBox comboBoxOferta = new JComboBox();
		comboBoxOferta.setBounds(47, 444, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(comboBoxOferta);
		
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
		textFieldId.setEnabled(false);
		textFieldId.setEditable(false);
		textFieldId.setBounds(384, 155, 180, 19);
		frmAlmacnSupermercado.getContentPane().add(textFieldId);
		textFieldId.setColumns(10);
		
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
		
		JComboBox comboBoxIdCat = new JComboBox();
		comboBoxIdCat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBoxIdCat.getSelectedIndex() == 0) {
					comboBoxCat.setToolTipText("Bebidas: 1");
				} else if (comboBoxIdCat.getSelectedIndex() == 1) {
					comboBoxCat.setToolTipText("Carnes: 2");
				} else {
					comboBoxCat.setToolTipText("Pescados: 3");
				}
			}
		});
		comboBoxIdCat.setModel(new DefaultComboBoxModel(new String[] {"Bebidas: 1", "Carnes: 2", "Pescados: 3"}));
		comboBoxIdCat.setBounds(384, 187, 180, 21);
		frmAlmacnSupermercado.getContentPane().add(comboBoxIdCat);
		
		
		
		
		
		
	}
}
