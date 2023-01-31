package facilityLocation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ClientesJSON {
	private ArrayList<Cliente> clientes;
	
	public ClientesJSON() {
		clientes = new ArrayList<Cliente>();
	}
	
	public void addCoord(Cliente c) {
		clientes.add(c);
	}

	public Cliente dame(int indice) {
		return clientes.get(indice);
	}
	
	public int tamanio() {
		return clientes.size();
	}
	
	public String generarJSONPretty() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(this);
		
		return json;
	}
	
	public void guardarJSON(String jsonParaGuardar, String archivosDestino) {
		try {
			FileWriter writer = new FileWriter(archivosDestino);
			writer.write(jsonParaGuardar);
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ClientesJSON leerJSON(String archivo) {
		Gson gson = new Gson();
		ClientesJSON ret = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			ret = gson.fromJson(br, ClientesJSON.class);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		return ret;
	}

}
