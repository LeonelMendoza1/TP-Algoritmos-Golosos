package facilityLocation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CentroDistribucionJSON {
	private ArrayList<CentroDistribucion> centros;
	
	public CentroDistribucionJSON() {
		centros = new ArrayList<CentroDistribucion>();
	}
	
	public void addCoord(CentroDistribucion c) {
		centros.add(c);
	}

	public CentroDistribucion dame(int indice) {
		return centros.get(indice);
	}
	
	public int tamanio() {
		return centros.size();
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
