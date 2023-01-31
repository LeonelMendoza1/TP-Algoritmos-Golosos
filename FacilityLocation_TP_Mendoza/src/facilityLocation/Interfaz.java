package facilityLocation;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;


public class Interfaz {
	
	private JFrame frame;
	private JMapViewer _mapa;
	private ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	private ArrayList<CentroDistribucion> centrosDist = new ArrayList<CentroDistribucion>();
	private ArrayList<CentroDistribucion> centrosHabilitados = new ArrayList<CentroDistribucion>();
	private ArrayList< MapPolygonImpl> _losCaminos = new ArrayList<MapPolygonImpl>();
	private MapPolygonImpl camino;
	private int cantCentros = 0;
	private JTextField textField;
	private double costoSolucion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch(Exception e)
		{}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaz window = new Interfaz();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public Interfaz() throws IOException, ParseException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws ParseException 
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	public void initialize() throws IOException, ParseException {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Facility Location");
		
		_mapa = new JMapViewer();
		frame.getContentPane().add(_mapa);
		
		Coordinate coordinate = new Coordinate(-34.521, -58.7008);
		_mapa.setDisplayPosition(coordinate, 15);
		_mapa.setZoomContolsVisible(false);
		
		textField = new JTextField();
		textField.setBackground(Color.WHITE);
		textField.setBounds(724, 11, 38, 20);
		_mapa.add(textField);
		textField.setColumns(10);
		
		JButton btnButton = new JButton("Iniciar");
		
		btnButton.setBounds(685, 42, 89, 23);
		_mapa.add(btnButton);
		
		JLabel lblCosto = new JLabel("Costo de la solucion en KM:");
		lblCosto.setBackground(Color.RED);
		lblCosto.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblCosto.setBounds(553, 584, 169, 14);
		_mapa.add(lblCosto);
		
		JLabel lblSolucion = new JLabel("");
		lblSolucion.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblSolucion.setBounds(724, 584, 38, 14);
		_mapa.add(lblSolucion);
		
		JLabel lblCantidadDeCentros = new JLabel("Cantidad de centros habilitados: ");
		lblCantidadDeCentros.setForeground(Color.BLACK);
		lblCantidadDeCentros.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblCantidadDeCentros.setBounds(517, 11, 203, 23);
		_mapa.add(lblCantidadDeCentros);
		
		JTextArea textArea = new JTextArea();
		textArea.setEnabled(false);
		textArea.setBackground(Color.RED);
		textArea.setForeground(Color.RED);
		textArea.setBounds(538, 580, 236, 20);
		_mapa.add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setEnabled(false);
		textArea_1.setBackground(Color.RED);
		textArea_1.setBounds(516, 11, 203, 22);
		_mapa.add(textArea_1);

		
		leerClienteJSON();
		dibujarClientes();
		
		leerCentrosDistJSON();
		dibujarCentrosDist();
		
		btnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				if (algunoVacio()){
					JOptionPane.showMessageDialog(null, "No se admiten lugares vacios!");
					return;
				}	
				
				if (!esNumero(textField.getText())){
					JOptionPane.showMessageDialog(null, "Solo puede ingresar numeros!");
					return;
				}
				cantCentros = Integer.parseInt(textField.getText());
				
				if (!valoresCorrectos()) {
					JOptionPane.showMessageDialog(null, "Los valores deben estar entre 1 y 4");
					return;
				}
				
				calcularDistanciasCentrosDist();
				resolver();
				encontrarCentroMasCercano();
				btnButton.setEnabled(false);
				String solucion = String.format("%.3f", costoSolucion);
				lblSolucion.setText(solucion);
				JOptionPane.showMessageDialog(null,"El costo de la solucion es: " + solucion + " KM");
			}
			
			private boolean valoresCorrectos() {
				if (cantCentros <= 0 || cantCentros >4) {
					return false;
				}else {
					return true;
				}
			}
			
			public boolean algunoVacio() {
				if (textField.getText().equals("")) {
					return true;
				}
				else {
					return false;
				}	
			}
			
			public boolean esNumero (String numero){
				return numero.matches("[0-9]*");
			}
			
		});
		
		
	}
	
	
	private void calcularDistanciasCentrosDist() {
		for (int i = 0; i < centrosDist.size(); i++) {
			centrosDist.get(i).distanciaTotal(clientes);
		}
	}
		
	private void leerClienteJSON() throws IOException, ParseException  {
		JSONParser json = new JSONParser();
		FileReader reader = new FileReader("Clientes.JSON");
		Object obj = json.parse(reader);
		JSONObject jsonObj = (JSONObject) obj;
		JSONArray array = (JSONArray) jsonObj.get("clientes");
		for(int i=0; i<array.size(); i++) {
			JSONObject address = (JSONObject) array.get(i);
			double latitud = (double) address.get("latitud");
			double longitud = (double) address.get("longitud");
			Cliente nuevo = new Cliente(latitud, longitud);
			clientes.add(nuevo);
		}
		
	}
	
	private void leerCentrosDistJSON() throws IOException, ParseException  {
		JSONParser json = new JSONParser();
		FileReader reader = new FileReader("CentrosDistribucion.JSON");
		Object obj = json.parse(reader);
		JSONObject jsonObj = (JSONObject) obj;
		JSONArray array = (JSONArray) jsonObj.get("centros");
		for(int i=0; i<array.size(); i++) {
			JSONObject address = (JSONObject) array.get(i);
			double latitud = (double) address.get("latitud");
			double longitud = (double) address.get("longitud");
			CentroDistribucion nuevo = new CentroDistribucion(latitud, longitud);
			centrosDist.add(nuevo);
		}
		
	}
	
	  private void dibujarClientes() {
		  for(int i=0; i<clientes.size(); i++) {
			  Coordinate coordenada = new Coordinate(clientes.get(i).getLatitud(), clientes.get(i).getLongitud());
			  MapMarker marker = new MapMarkerDot("", coordenada);
			  marker.getStyle().setBackColor(Color.BLUE);
			  marker.getStyle().setColor(Color.BLUE);
			  _mapa.addMapMarker(marker);
		  }
	  }
	  
	  private void dibujarCentrosDist() {
		  for(int i=0; i<centrosDist.size(); i++) {
			  Coordinate coordenada = new Coordinate(centrosDist.get(i).getLatitud(), centrosDist.get(i).getLongitud());
			  MapMarker marker = new MapMarkerDot("", coordenada);
			  marker.getStyle().setBackColor(Color.BLACK);
			  marker.getStyle().setColor(Color.BLACK);
			 
			  _mapa.addMapMarker(marker);
		  }
	  }
	  
	  private void encontrarCentroMasCercano() {
		  for(int i=0; i<clientes.size(); i++) {
			  encontrarMasCorto(clientes.get(i));
		  }
	  }
	  
	  private void encontrarMasCorto(Cliente cliente) {
		double distancia = centrosHabilitados.get(0).distanciaRecta(cliente.getLatitud(), cliente.getLongitud());
		CentroDistribucion centroElegido = centrosHabilitados.get(0); 
		for(CentroDistribucion centro : centrosHabilitados) {
			if(centro.distanciaRecta(cliente.getLatitud(),cliente.getLongitud()) < distancia) {
				distancia = centro.distanciaRecta(cliente.getLatitud(),cliente.getLongitud());
				centroElegido = centro;
			}
		}
		costoSolucion += distancia;
		dibujarLineas(cliente,centroElegido);
	}

	
	private void dibujarLineas(Cliente cliente, CentroDistribucion centro) {
		Coordinate coordenadaCliente = new Coordinate(cliente.getLatitud(), cliente.getLongitud());
		Coordinate coordenadaCD = new Coordinate(centro.getLatitud(), centro.getLongitud());
		camino = new MapPolygonImpl(coordenadaCliente, coordenadaCD, coordenadaCliente);
		camino.setColor(Color.RED);
		_losCaminos.add(camino);
		_mapa.addMapPolygon(camino);
	}

		private void resolver(){
			@SuppressWarnings("unchecked")
			ArrayList<CentroDistribucion> ret = (ArrayList<CentroDistribucion>) centrosDist.clone();
			ret = Solver.ordenarArreglo(ret);
			for(CentroDistribucion centro : ret) {
				 if(cantCentros > 0) {
					centrosHabilitados.add(centro);
				 	cantCentros--;
				  }	
			 }
		}
}
