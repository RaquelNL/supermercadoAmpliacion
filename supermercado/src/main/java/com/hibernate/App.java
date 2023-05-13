package com.hibernate;

import java.awt.Component;
import java.awt.EventQueue;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.hibernate.model.Categoria;
import com.hibernate.model.Producto;
import com.toedter.calendar.JCalendar;
import com.hibernate.dao.CategoriaDAO;
import com.hibernate.dao.ProductoDAO;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;

public class App {

	private JFrame frmAlmacnSupermercado;
	private JTable tableProductos;
	private JTable table;
	private JTextField textFieldId;
	private JTextField textFieldNomProd;
	private JTextField textFieldPrecio;
	private JTextField textFieldEnStock;
	
	
	void limpiarTexto() {
		textFieldId.setText("");
		textFieldNomProd.setText("");
		textFieldPrecio.setText("");
		textFieldEnStock.setText("");
	}

	Pattern patNom = Pattern.compile("^[A-Za-z]{1,50}$");
	Pattern patPrecio = Pattern.compile("^\\d+(?:\\.\\d{1,2})?$");
	Pattern patStock= Pattern.compile("^\\d$");
	
	String formatearFecha(LocalDate caducidadSQL) {
		DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    String caducidadFormat = caducidadSQL.format(formatoFecha);
		return caducidadFormat;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
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
		CategoriaDAO categoriaDAO = new CategoriaDAO();

		
		frmAlmacnSupermercado = new JFrame();
		frmAlmacnSupermercado.setTitle("Almacén Supermercado");
		frmAlmacnSupermercado.setBounds(100, 100, 1121, 619);
		frmAlmacnSupermercado.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAlmacnSupermercado.getContentPane().setLayout(null);

		JLabel lblEligeLaCategoria = new JLabel("ELIGE LA CATEGORÍA:");
		lblEligeLaCategoria.setVisible(false);
		lblEligeLaCategoria.setBounds(47, 118, 157, 13);
		frmAlmacnSupermercado.getContentPane().add(lblEligeLaCategoria);

		JDateChooser calendario = new JDateChooser();
		calendario.setBounds(384, 298, 180, 27);
		frmAlmacnSupermercado.getContentPane().add(calendario);

		
		
		
		JComboBox comboBoxIdCat = new JComboBox();
		comboBoxIdCat.addItem(new Categoria(1, "Bebidas"));
		comboBoxIdCat.addItem(new Categoria(2, "Carnes"));
		comboBoxIdCat.addItem(new Categoria(3, "Pescados"));
		comboBoxIdCat.setModel(new DefaultComboBoxModel(new String[] {"1: Bebidas", "2: Carnes", "3: Pescados"}));
		comboBoxIdCat.setBounds(384, 187, 180, 21);
		frmAlmacnSupermercado.getContentPane().add(comboBoxIdCat);
		
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Categoría");
		model.addColumn("Nombre");
		model.addColumn("Precio");
		model.addColumn("En Stock");
		model.addColumn("Caducidad");
		model.addColumn("Oferta");
		
		
		
				
			
				    
				
				
//				List<Producto> productos = productoDAO.selectAllProducto();
//				for (Producto p : productos) {
//					precio = p.getPrecio();
//				
//				if (comboBoxOferta.getSelectedIndex() == 1) {
//					List<Producto> productos = productoDAO.selectAllProducto();
//					for (Producto p : productos) {
//						precio = p.getPrecio() * 0.25;
//					}
//				} else if (comboBoxOferta.getSelectedIndex() == 2) {
//					List<Producto> productos = productoDAO.selectAllProducto();
//					for (Producto p : productos) {
//						precio = p.getPrecio() * 0.50;
//					}
//				} else {
//					List<Producto> productos = productoDAO.selectAllProducto();
//					for (Producto p : productos) {
//						precio = p.getPrecio() * 0.75;
//					}
//				}
		JComboBox comboBoxOferta = new JComboBox();
		comboBoxOferta.setModel(new DefaultComboBoxModel(new String[] {"Elige la oferta:", "25", "50", "75"}));
		comboBoxOferta.setBounds(384, 337, 180, 21);
		frmAlmacnSupermercado.getContentPane().add(comboBoxOferta);
		
		
		JComboBox comboBoxCat = new JComboBox();
		comboBoxCat.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (comboBoxCat.getSelectedIndex() == 0) {
					model.setRowCount(0);
					List<Producto> productos = productoDAO.selectAllBebidas();
					for (Producto p : productos) {
					    Object[] row = new Object[7];
					    row[0] = p.getCodprod();
					    row[1] = p.getCategoria().getNombre();
					    row[2] = p.getNomProd();
					    row[3] = p.getPrecio();
					    row[4] = p.getStock();
					    
					    LocalDate caducidadSQL = p.getCaducidad();
					    row[5] = caducidadSQL;
					    
					    if(caducidadSQL != null) {
					    	String caducidadFormat = formatearFecha(caducidadSQL);
						    row[5] = caducidadFormat;
					    }
					    
					    row[6] = p.getOferta();
					    
					    model.addRow(row);
					   
					    
					}
					
				} else if (comboBoxCat.getSelectedIndex() == 1) {
					model.setRowCount(0);
					List<Producto> productos = productoDAO.selectAllCarnes();
					for (Producto p : productos) {
					    Object[] row = new Object[7];
					    row[0] = p.getCodprod();
					    row[1] = p.getCategoria().getNombre();
					    row[2] = p.getNomProd();
					    row[3] = p.getPrecio();
					    row[4] = p.getStock();
					    
					    LocalDate caducidadSQL = p.getCaducidad();
					    row[5] = caducidadSQL;
					    
					    if(caducidadSQL != null) {
					    	String caducidadFormat = formatearFecha(caducidadSQL);
						    row[5] = caducidadFormat;
					    }
					    
					    row[6] = p.getOferta();
					
					    model.addRow(row);

					}
					
				} else {
					model.setRowCount(0);
					List<Producto> productos = productoDAO.selectAllPescados();
					for (Producto p : productos) {
					    Object[] row = new Object[7];
					    row[0] = p.getCodprod();
					    row[1] = p.getCategoria().getNombre();
					    row[2] = p.getNomProd();
					    row[3] = p.getPrecio();
					    row[4] = p.getStock();
					    
					    LocalDate caducidadSQL = p.getCaducidad();
					    row[5] = caducidadSQL;
					    
					    if(caducidadSQL != null) {
					    	String caducidadFormat = formatearFecha(caducidadSQL);
						    row[5] = caducidadFormat;
					    }
					    
					    row[6] = p.getOferta();
					    
					    model.addRow(row);
					   
					}
				}
				
				
			}
			
			
		});
		comboBoxCat.setModel(new DefaultComboBoxModel(new String[] {"Bebidas", "Carnes", "Pescados", "Limpieza"}));
		comboBoxCat.setVisible(false);
		comboBoxCat.setBounds(47, 143, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(comboBoxCat);
		List<Producto> productosSinStock = productoDAO.selectProductoSinStockId();
		
		JComboBox comboBoxOpcion = new JComboBox();
		comboBoxOpcion.setModel(new DefaultComboBoxModel(new String[] {"Todos los Productos", "Según la Categoría", "Productos sin Stock", "Productos caducados"}));
		comboBoxOpcion.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (comboBoxOpcion.getSelectedIndex() == 0) {
					lblEligeLaCategoria.setVisible(false);
					comboBoxCat.setVisible(false);
					
					model.setRowCount(0);
					List<Producto> productos = productoDAO.selectAllProducto();
					for (Producto p : productos) {
					    Object[] row = new Object[7];
					    row[0] = p.getCodprod();
					    row[1] = p.getCategoria().getNombre();
					    row[2] = p.getNomProd();
					    row[3] = p.getPrecio();
					    row[4] = p.getStock();
					    
					    LocalDate caducidadSQL = p.getCaducidad();
					    row[5] = caducidadSQL;
					    
					    if(caducidadSQL != null) {
					    	String caducidadFormat = formatearFecha(caducidadSQL);
						    row[5] = caducidadFormat;
					    }
			        	
					    row[6] = p.getOferta();
					    
					    model.addRow(row);

					}
					
					
				} else if (comboBoxOpcion.getSelectedIndex() == 1) {
					lblEligeLaCategoria.setVisible(true);
					comboBoxCat.setVisible(true);
					
				} else if (comboBoxOpcion.getSelectedIndex() == 2){
					lblEligeLaCategoria.setVisible(false);
					comboBoxCat.setVisible(false);
					
					model.setRowCount(0);
					List<Producto> productos = productoDAO.selectProductoSinStock();
					for (Producto p : productos) {
					    Object[] row = new Object[7];
					    row[0] = p.getCodprod();
					    row[1] = p.getCategoria().getNombre();
					    row[2] = p.getNomProd();
					    row[3] = p.getPrecio();
					    row[4] = p.getStock();
					    
					    LocalDate caducidadSQL = p.getCaducidad();
					    row[5] = caducidadSQL;
					    
					    if(caducidadSQL != null) {
					    	String caducidadFormat = formatearFecha(caducidadSQL);
						    row[5] = caducidadFormat;
					    }
					    
					    row[6] = p.getOferta();
					    
					    model.addRow(row);
					   
					}
					
				} else {
					
					LocalDate hoy = LocalDate.now();
					 
					model.setRowCount(0);
					List<Producto> productos = productoDAO.selectProductosCaducados(hoy);
					for (Producto p : productos) {
					    Object[] row = new Object[7];
					    row[0] = p.getCodprod();
					    row[1] = p.getCategoria().getNombre();
					    row[2] = p.getNomProd();
					    row[3] = p.getPrecio();
					    row[4] = p.getStock();
					    
					    LocalDate caducidadSQL = p.getCaducidad();
					    row[5] = caducidadSQL;
					    
					    if(caducidadSQL != null) {
					    	String caducidadFormat = formatearFecha(caducidadSQL);
						    row[5] = caducidadFormat;
					    }
					    
					    row[6] = p.getOferta();
					    
					    model.addRow(row);
					}
				}
					
				
			}
		});
		comboBoxOpcion.setBounds(47, 65, 170, 21);
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
				    if (model instanceof DefaultTableModel && model.getColumnCount() == 7) {
				        textFieldId.setText(model.getValueAt(index, 0).toString());
				        comboBoxCat.setToolTipText(model.getValueAt(index, 1).toString());
				        textFieldNomProd.setText(model.getValueAt(index, 2).toString());
				        textFieldPrecio.setText(model.getValueAt(index, 3).toString());
				        textFieldEnStock.setText(model.getValueAt(index, 4).toString());
				        
				        if (model.getValueAt(index, 5) != null) {
				        	
				        	String fechaString = model.getValueAt(index, 5).toString();
						    
					        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					        
				        	LocalDate fecha = LocalDate.parse(fechaString, formatoFecha);
				        	
				        	SimpleDateFormat formatoFechaChooser = new SimpleDateFormat("dd/MM/yyyy");
				        	calendario.setDateFormatString("dd/MM/yyyy");
				            try {
								calendario.setDate(formatoFechaChooser.parse(formatoFechaChooser.format(java.sql.Date.valueOf(fecha))));
							} catch (ParseException e1) {
								
							}

				            calendario.setDate(java.sql.Date.valueOf(fecha));
				        	
				        } 
				        else {
				        	calendario.setDate(null);
				        }
				        
				        comboBoxOferta.setToolTipText(model.getValueAt(index, 6).toString());
				        
//				        try {
//				        	String fechaString = model.getValueAt(index, 5).toString();
//						    
//					        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//					        
//				        	LocalDate fecha = LocalDate.parse(fechaString, formatoFecha);
//				        	
//				        	SimpleDateFormat formatoFechaChooser = new SimpleDateFormat("dd/MM/yyyy");
//				        	calendario.setDateFormatString("dd/MM/yyyy");
//				            calendario.setDate(formatoFechaChooser.parse(formatoFechaChooser.format(java.sql.Date.valueOf(fecha))));
//
//				        	calendario.setDate(java.sql.Date.valueOf(fecha));
//				        } catch (Exception s) {
//				            
//				        }
				    }
				 }
			}
		});
		scrollPane.setViewportView(table);
		
		
		
		JButton btnGuardarProd = new JButton("GUARDAR");
		btnGuardarProd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String opcionSeleccionada = (String) comboBoxIdCat.getSelectedItem();
		        String[] partes = opcionSeleccionada.split(": ");
		        int index = Integer.parseInt(partes[0].trim());
		        Categoria c = categoriaDAO.selectcategoriaById(index);
		        
		        Date fecha = calendario.getDate();
		        
		        LocalDate caducidad = null;
		        
		        if (fecha != null) {
		        	 calendario.setDateFormatString("dd/MM/yyyy");
				     caducidad = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		        }
		        
		        Double precioConOferta = null;
		        double precioInicial = Double.parseDouble(textFieldPrecio.getText());
		        //String ofertaSeleccionada = (String) comboBoxOferta.getSelectedItem();	
		        
		        switch (comboBoxOferta.getSelectedIndex()) {
			        case 1:
			        	precioConOferta = precioInicial - (precioInicial * 0.25);
			            break;
			            
			        case 2:
			        	precioConOferta = precioInicial - (precioInicial * 0.50);
			            break;
			            
			        case 3:
			        	precioConOferta = precioInicial - (precioInicial * 0.75);
			            break;
			    }
		        
				Producto producto = new Producto(textFieldNomProd.getText(), c,
                        precioInicial,
                        Integer.parseInt(textFieldEnStock.getText()), caducidad, precioConOferta); 
					productoDAO.insertProducto(producto);
		        
//		        Matcher matNom = patNom.matcher(TextFieldNomProd.getText());
//		    	Matcher matPrecio = patPrecio.matcher(TextFieldPrecio.getText());
//		    	Matcher matStock = patStock.matcher(TextFieldEnStock.getText());
//		    	
//		    	if (!matNom.matches()) {
//		    		JOptionPane.showMessageDialog(null, "El nombre es incorrecto");
//		    	} else if (!patPrecio.matches()) {
//		    		JOptionPane.showMessageDialog(null, "El precio es incorrecto");
//		    	} else if (!patStock.matches()) {
//		    		JOptionPane.showMessageDialog(null, "El stock es incorrecto");
//		    	} else {
//
//		    		try {
//		    			LocalDate fechaNac = LocalDate
//		    					.parse(textFieldDob2.getText() + "-" + textFieldDob1.getText() + "-" + dobTextField.getText());
//		    			
//		    			if (!(fechaNac.getYear() >= 18)) {
//		    				//Poner la fecha en rojo
//		    			} else {
//		    				DateTimeFormatter fechaFormateada = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//
//		    			}
		        
					JOptionPane.showMessageDialog(null, "Producto añadido");
					limpiarTexto();
					model.setRowCount(0);
					List<Producto> productos = productoDAO.selectAllProducto();
					for (Producto p : productos) {
					    Object[] row = new Object[7];
					    row[0] = p.getCodprod();
					    row[1] = p.getCategoria().getNombre();
					    row[2] = p.getNomProd();
					    row[3] = p.getPrecio();
					    row[4] = p.getStock();
					    LocalDate caducidadSQL = p.getCaducidad();
					    row[5] = caducidadSQL;
					    
					    if(caducidadSQL != null) {
					    	String caducidadFormat = formatearFecha(caducidadSQL);
						    row[5] = caducidadFormat;
					    }
					    
					    row[6] = p.getOferta();
					    
					    model.addRow(row);
					}
		    }});
		btnGuardarProd.setBounds(206, 399, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(btnGuardarProd);
		
		JButton btnActualizarProd = new JButton("ACTUALIZAR");
		btnActualizarProd.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        Producto productoActualizar = productoDAO.selectProductoById(Integer.parseInt(textFieldId.getText()));
		        productoActualizar.setNomProd(textFieldNomProd.getText());
		        
		        String opcionSeleccionada = (String) comboBoxIdCat.getSelectedItem();
		        String[] partes = opcionSeleccionada.split(": ");
		        int index = Integer.parseInt(partes[0].trim());
		        Categoria c = categoriaDAO.selectcategoriaById(index);
		        productoActualizar.setCategoria(c);
		       
		        double precioInicial = Double.parseDouble(textFieldPrecio.getText());
		        
		        productoActualizar.setPrecio(precioInicial);
		        productoActualizar.setStock(Integer.parseInt(textFieldEnStock.getText()));
		        
		        
		        Date fecha = calendario.getDate();
		        
		        if(fecha != null) {
		        	calendario.setDateFormatString("dd/MM/yyyy");
			        LocalDate caducidad = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			        productoActualizar.setCaducidad(caducidad);
		        } else {
		        	productoActualizar.setCaducidad(null);
		        }
		        
		        Double precioConOferta = null;
		       
		        String ofertaSeleccionada = (String) comboBoxOferta.getSelectedItem();	
		        
		        switch (comboBoxOferta.getSelectedIndex()) {
			        case 1:
			        	precioConOferta = precioInicial - (precioInicial * 0.25);
			            break;
			            
			        case 2:
			        	precioConOferta = precioInicial - (precioInicial * 0.50);
			            break;
			            
			        case 3:
			        	precioConOferta = precioInicial - (precioInicial * 0.75);
			            break;
			    }
		        
		        productoActualizar.setOferta(precioConOferta);
		        productoDAO.updateProducto(productoActualizar);
		        JOptionPane.showMessageDialog(null, "Producto actualizado");
		        limpiarTexto();
		        model.setRowCount(0);
				List<Producto> productos = productoDAO.selectAllProducto();
				for (Producto p : productos) {
				    Object[] row = new Object[7];
				    row[0] = p.getCodprod();
				    row[1] = p.getCategoria().getNombre();
				    row[2] = p.getNomProd();
				    row[3] = p.getPrecio();
				    row[4] = p.getStock();
				    LocalDate caducidadSQL = p.getCaducidad();
				    row[5] = caducidadSQL;
				    
				    if(caducidadSQL != null) {
				    	String caducidadFormat = formatearFecha(caducidadSQL);
					    row[5] = caducidadFormat;
				    }
				    
				    row[6] = p.getOferta();
				    
				    model.addRow(row);
		    }
		}});
		btnActualizarProd.setBounds(363, 399, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(btnActualizarProd);
		
		
		JButton btnBorrarProd = new JButton("BORRAR");
		btnBorrarProd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				productoDAO.deleteProducto(Integer.parseInt(textFieldId.getText()));
				JOptionPane.showMessageDialog(null, "Producto borrado");
				limpiarTexto();
				model.setRowCount(0);
				List<Producto> productos = productoDAO.selectAllProducto();
				for (Producto p : productos) {
				    Object[] row = new Object[7];
				    row[0] = p.getCodprod();
				    row[1] = p.getCategoria().getNombre();
				    row[2] = p.getNomProd();
				    row[3] = p.getPrecio();
				    row[4] = p.getStock();
				    row[5] = p.getCaducidad();
				    row[6] = p.getOferta();
				    model.addRow(row);
			}
		}});
		btnBorrarProd.setBounds(526, 399, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(btnBorrarProd);
		
		JLabel lblCategoria = new JLabel("ELIGE LA OPCIÓN:");
		lblCategoria.setBounds(47, 40, 157, 13);
		frmAlmacnSupermercado.getContentPane().add(lblCategoria);
		
		
		
		JLabel lblOferta = new JLabel("OFERTA:");
		lblOferta.setBounds(269, 342, 157, 13);
		frmAlmacnSupermercado.getContentPane().add(lblOferta);
		
		
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
		textFieldId.setBounds(384, 155, 180, 27);
		frmAlmacnSupermercado.getContentPane().add(textFieldId);
		textFieldId.setColumns(10);
		
		textFieldNomProd = new JTextField();
		textFieldNomProd.setColumns(10);
		textFieldNomProd.setBounds(384, 217, 180, 27);
		frmAlmacnSupermercado.getContentPane().add(textFieldNomProd);
		
		textFieldPrecio = new JTextField();
		textFieldPrecio.setColumns(10);
		textFieldPrecio.setBounds(384, 242, 180, 27);
		frmAlmacnSupermercado.getContentPane().add(textFieldPrecio);
		
		textFieldEnStock = new JTextField();
		textFieldEnStock.setColumns(10);
		textFieldEnStock.setBounds(384, 267, 180, 27);
		frmAlmacnSupermercado.getContentPane().add(textFieldEnStock);
		
		JLabel lblCaducidad = new JLabel("CADUCIDAD:");
		lblCaducidad.setBounds(269, 298, 105, 15);
		frmAlmacnSupermercado.getContentPane().add(lblCaducidad);
		
		
		
	
					
	
	}
}
