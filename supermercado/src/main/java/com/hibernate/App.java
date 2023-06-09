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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.hibernate.model.Categoria;
import com.hibernate.model.Producto;
import com.hibernate.model.Proveedor;
import com.toedter.calendar.JCalendar;
import com.hibernate.dao.CategoriaDAO;
import com.hibernate.dao.ProductoDAO;
import com.hibernate.dao.ProveedorDAO;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
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
import javax.swing.JCheckBox;
import javax.swing.JTextArea;

/**
 * Clase principal de la aplicación de supermercado
 * @author raqnl
 *
 */
public class App {

/**
 * Componentes de Swing
 */
	private JFrame frmAlmacnSupermercado;
	private JTable tableProductos;
	private JTable table;
	private JTextField textFieldId;
	private JTextField textFieldNomProd;
	private JTextField textFieldPrecio;
	private JTextField textFieldEnStock;
	
/**
 * Función para limpiar el texto al gardar, actualizar o borrar un producto
 */
	void limpiarTexto() {
		textFieldId.setText("");
		textFieldNomProd.setText("");
		textFieldPrecio.setText("");
		textFieldEnStock.setText("");
	}

/**
 * Expresiones regulares para el control de errores
 */
	Pattern patNom = Pattern.compile("^[A-Za-z]{1,50}$");
	Pattern patPrecio = Pattern.compile("^\\d+(?:\\.\\d{1,2})?$");
	Pattern patStock= Pattern.compile("^\\d$");
	
/**
 * Función para cambiar el formato de la fecha
 * @param caducidadSQL
 * @return caducidadFormat, que es la fecha con el formato cambiado
 */
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
		
/**
 * Objetos de las clases del paquete com.hibernate.dao
 */
		ProductoDAO productoDAO = new ProductoDAO();
		CategoriaDAO categoriaDAO = new CategoriaDAO();
		ProveedorDAO proveedorDAO = new ProveedorDAO();

		
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
		calendario.setBounds(358, 228, 180, 27);
		frmAlmacnSupermercado.getContentPane().add(calendario);

		
		JComboBox comboBoxIdProveedor = new JComboBox();
		comboBoxIdProveedor.addItem(new Proveedor(1, "Mercadona", 800500220, "España"));
		comboBoxIdProveedor.addItem(new Proveedor(2, "Carrefour", 914908900, "Francia"));
		comboBoxIdProveedor.setModel(new DefaultComboBoxModel(new String[] {"1: Mercadona", "2: Carrefour"}));
		comboBoxIdProveedor.setBounds(700, 259, 180, 27);
		frmAlmacnSupermercado.getContentPane().add(comboBoxIdProveedor);
		
		
		
		JComboBox comboBoxIdCat = new JComboBox();
		comboBoxIdCat.addItem(new Categoria(1, "Bebidas"));
		comboBoxIdCat.addItem(new Categoria(2, "Carnes"));
		comboBoxIdCat.addItem(new Categoria(3, "Pescados"));
		comboBoxIdCat.setModel(new DefaultComboBoxModel(new String[] {"Selecciona:", "1: Bebidas", "2: Carnes", "3: Pescados"}));
		comboBoxIdCat.setBounds(358, 189, 180, 27);
		frmAlmacnSupermercado.getContentPane().add(comboBoxIdCat);
		
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Categoría");
		model.addColumn("Nombre");
		model.addColumn("Precio");
		model.addColumn("En Stock");
		model.addColumn("Caducidad");
		model.addColumn("Oferta");
		model.addColumn("Proveedor");
		

		JComboBox comboBoxOferta = new JComboBox();
		comboBoxOferta.setModel(new DefaultComboBoxModel(new String[] {"Selecciona:", "25", "50", "75"}));
		comboBoxOferta.setBounds(358, 261, 180, 27);
		frmAlmacnSupermercado.getContentPane().add(comboBoxOferta);
		
		
		JComboBox comboBoxCat = new JComboBox();
		comboBoxCat.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (comboBoxCat.getSelectedIndex() == 1) {
					model.setRowCount(0);
					List<Producto> productos = productoDAO.selectAllBebidas();
					for (Producto p : productos) {
					    Object[] row = new Object[8];
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
					    row[7] = p.getproveedor().getNombre();
					    
					    model.addRow(row);
					   
					    
					}
					
				} else if (comboBoxCat.getSelectedIndex() == 2) {
					model.setRowCount(0);
					List<Producto> productos = productoDAO.selectAllCarnes();
					for (Producto p : productos) {
					    Object[] row = new Object[8];
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
					    row[7] = p.getproveedor().getNombre();
					
					    model.addRow(row);

					}
					
				} else {
					model.setRowCount(0);
					List<Producto> productos = productoDAO.selectAllPescados();
					for (Producto p : productos) {
					    Object[] row = new Object[8];
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
					    row[7] = p.getproveedor().getNombre();
					    
					    model.addRow(row);
					   
					}
				}
				
				
			}
			
			
		});
		comboBoxCat.setModel(new DefaultComboBoxModel(new String[] {"Selecciona:", "Bebidas", "Carnes", "Pescados", "Limpieza"}));
		comboBoxCat.setVisible(false);
		comboBoxCat.setBounds(47, 143, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(comboBoxCat);
		List<Producto> productosSinStock = productoDAO.selectProductoSinStockId();
		
		JComboBox comboBoxOpcion = new JComboBox();
		comboBoxOpcion.setModel(new DefaultComboBoxModel(new String[] {"Selecciona:", "Todos los Productos", "Según la Categoría", "Productos sin Stock", "Productos caducados"}));
		comboBoxOpcion.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (comboBoxOpcion.getSelectedIndex() == 1) {
					lblEligeLaCategoria.setVisible(false);
					comboBoxCat.setVisible(false);
					
					model.setRowCount(0);
					List<Producto> productos = productoDAO.selectAllProducto();
					for (Producto p : productos) {
					    Object[] row = new Object[8];
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
					    row[7] = p.getproveedor().getNombre();
					    
					    model.addRow(row);

					}
					
					
				} else if (comboBoxOpcion.getSelectedIndex() == 2) {
					lblEligeLaCategoria.setVisible(true);
					comboBoxCat.setVisible(true);
					
				} else if (comboBoxOpcion.getSelectedIndex() == 3){
					lblEligeLaCategoria.setVisible(false);
					comboBoxCat.setVisible(false);
					
					model.setRowCount(0);
					List<Producto> productos = productoDAO.selectProductoSinStock();
					for (Producto p : productos) {
					    Object[] row = new Object[8];
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
					    row[7] = p.getproveedor().getNombre();
					    
					    model.addRow(row);
					   
					}
					
				} else {
					
					LocalDate hoy = LocalDate.now();
					 
					model.setRowCount(0);
					List<Producto> productos = productoDAO.selectProductosCaducados(hoy);
					for (Producto p : productos) {
					    Object[] row = new Object[8];
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
					    row[7] = p.getproveedor().getNombre();
					    
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
		DefaultTableCellRenderer centrador = new DefaultTableCellRenderer();
        centrador.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centrador);
        }

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				if (index != -1) {
					DefaultTableModel model = (DefaultTableModel) table.getModel();
				    if (model instanceof DefaultTableModel && model.getColumnCount() == 8) {
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
				        comboBoxIdProveedor.setToolTipText(model.getValueAt(index, 7).toString());
				        
				    }
				 }
			}
		});
		scrollPane.setViewportView(table);
		
		
/**
 * Botón de guardar producto dónde se llama al constructor de Producto para añadir las opciones seleccionadas en los componentes de Swing	
 */
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

		        String opcionSeleccionadaProv = (String) comboBoxIdProveedor.getSelectedItem();
		        String[] partesProv = opcionSeleccionadaProv.split(": ");
		        int indexProv = Integer.parseInt(partesProv[0].trim());
		        Proveedor pr = proveedorDAO.selectProveedorById(indexProv);

		        Matcher matNom = patNom.matcher(textFieldNomProd.getText());
		        Matcher matPrecio = patPrecio.matcher(textFieldPrecio.getText());
		        Matcher matStock = patStock.matcher(textFieldEnStock.getText());

		        if (!matNom.matches()) {
		            JOptionPane.showMessageDialog(null, "El nombre es incorrecto");
		        } else if (!matPrecio.matches()) {
		            JOptionPane.showMessageDialog(null, "El precio es incorrecto");
		        } else if (!matStock.matches()) {
		            JOptionPane.showMessageDialog(null, "El stock es incorrecto");
		        } else {
		            Producto producto = new Producto(textFieldNomProd.getText(), c,
		                    precioInicial,
		                    Integer.parseInt(textFieldEnStock.getText()), caducidad, precioConOferta, pr);
		            productoDAO.insertProducto(producto);

		            JOptionPane.showMessageDialog(null, "Producto añadido");
		            limpiarTexto();
		            model.setRowCount(0);
		            List<Producto> productos = productoDAO.selectAllProducto();
		            for (Producto p : productos) {
		                Object[] row = new Object[8];
		                row[0] = p.getCodprod();
		                row[1] = p.getCategoria().getNombre();
		                row[2] = p.getNomProd();
		                row[3] = p.getPrecio();
		                row[4] = p.getStock();
		                LocalDate caducidadSQL = p.getCaducidad();
		                row[5] = caducidadSQL;

		                if (caducidadSQL != null) {
		                    String caducidadFormat = formatearFecha(caducidadSQL);
		                    row[5] = caducidadFormat;
		                }

		                row[6] = p.getOferta();
		                row[7] = p.getproveedor().getNombre();

		                model.addRow(row);
		            }
		        }
		    }
		});
		btnGuardarProd.setBounds(362, 355, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(btnGuardarProd);

		
/**
* Botón de actualizar producto dónde se llama al constructor de Producto para añadir las opciones seleccionadas en los componentes de Swing	
*/
		
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
		        
		        String opcionSeleccionadaProv = (String) comboBoxIdProveedor.getSelectedItem();
		        String[] partesProv = opcionSeleccionadaProv.split(": ");
		        int indexProv = Integer.parseInt(partesProv[0].trim());
		        Proveedor pr = proveedorDAO.selectProveedorById(indexProv);
		        productoActualizar.setProveedor(pr);
		        productoDAO.updateProducto(productoActualizar);
		        JOptionPane.showMessageDialog(null, "Producto actualizado");
		        limpiarTexto();
		        model.setRowCount(0);
				List<Producto> productos = productoDAO.selectAllProducto();
				for (Producto p : productos) {
				    Object[] row = new Object[8];
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
				    row[7] = p.getproveedor().getNombre();
				    
				    model.addRow(row);
		    }
		}});
		btnActualizarProd.setBounds(519, 355, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(btnActualizarProd);
		
		
/**
 * Botón de borrar Prodcuto
 */
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
				    Object[] row = new Object[8];
				    row[0] = p.getCodprod();
				    row[1] = p.getCategoria().getNombre();
				    row[2] = p.getNomProd();
				    row[3] = p.getPrecio();
				    row[4] = p.getStock();
				    row[5] = p.getCaducidad();
				    row[6] = p.getOferta();
				    row[7] = p.getproveedor();
				    model.addRow(row);
			}
		}});
		btnBorrarProd.setBounds(682, 355, 122, 21);
		frmAlmacnSupermercado.getContentPane().add(btnBorrarProd);
		
		JLabel lblCategoria = new JLabel("ELIGE LA OPCIÓN:");
		lblCategoria.setBounds(47, 40, 157, 13);
		frmAlmacnSupermercado.getContentPane().add(lblCategoria);
		
		
		
		JLabel lblOferta = new JLabel("OFERTA:");
		lblOferta.setBounds(245, 265, 157, 13);
		frmAlmacnSupermercado.getContentPane().add(lblOferta);
		
		
		JLabel lblIdProd = new JLabel("ID:");
		lblIdProd.setBounds(245, 159, 70, 15);
		frmAlmacnSupermercado.getContentPane().add(lblIdProd);
		
		JLabel lblCat = new JLabel("CATEGORÍA:");
		lblCat.setBounds(245, 192, 114, 15);
		frmAlmacnSupermercado.getContentPane().add(lblCat);
		
		JLabel lblNomProd = new JLabel("NOMBRE:");
		lblNomProd.setBounds(608, 157, 70, 15);
		frmAlmacnSupermercado.getContentPane().add(lblNomProd);
		
		JLabel lblPrecio = new JLabel("PRECIO:");
		lblPrecio.setBounds(608, 190, 70, 15);
		frmAlmacnSupermercado.getContentPane().add(lblPrecio);
		
		JLabel lblEnStock = new JLabel("EN STOCK:");
		lblEnStock.setBounds(608, 229, 93, 15);
		frmAlmacnSupermercado.getContentPane().add(lblEnStock);
		
		textFieldId = new JTextField();
		textFieldId.setEnabled(false);
		textFieldId.setEditable(false);
		textFieldId.setBounds(358, 157, 180, 27);
		frmAlmacnSupermercado.getContentPane().add(textFieldId);
		textFieldId.setColumns(10);
		
		textFieldNomProd = new JTextField();
		textFieldNomProd.setColumns(10);
		textFieldNomProd.setBounds(680, 151, 180, 27);
		frmAlmacnSupermercado.getContentPane().add(textFieldNomProd);
		
		textFieldPrecio = new JTextField();
		textFieldPrecio.setColumns(10);
		textFieldPrecio.setBounds(680, 184, 180, 27);
		frmAlmacnSupermercado.getContentPane().add(textFieldPrecio);
		
		textFieldEnStock = new JTextField();
		textFieldEnStock.setColumns(10);
		textFieldEnStock.setBounds(690, 223, 180, 27);
		frmAlmacnSupermercado.getContentPane().add(textFieldEnStock);
		
		JLabel lblCaducidad = new JLabel("CADUCIDAD:");
		lblCaducidad.setBounds(245, 231, 105, 15);
		frmAlmacnSupermercado.getContentPane().add(lblCaducidad);
		
		JLabel lblProveedor = new JLabel("PROVEEDOR:");
		lblProveedor.setBounds(608, 263, 82, 13);
		frmAlmacnSupermercado.getContentPane().add(lblProveedor);
		
		JLabel lblIncidencia = new JLabel("INCIDENCIA:");
		lblIncidencia.setBounds(245, 300, 93, 15);
		frmAlmacnSupermercado.getContentPane().add(lblIncidencia);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Selecciona:", "1: Roto", "2: Caducado", "3: Defectuoso"}));
		comboBox.setBounds(358, 295, 180, 24);
		frmAlmacnSupermercado.getContentPane().add(comboBox);
		
		
		
	
					
	
	}
}
