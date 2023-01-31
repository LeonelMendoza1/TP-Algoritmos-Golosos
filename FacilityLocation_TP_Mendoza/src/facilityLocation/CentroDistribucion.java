package facilityLocation;

import java.util.ArrayList;

public class CentroDistribucion implements Comparable<CentroDistribucion> {
	private double latitud;
	private double longitud;
	private double latAux;
	private double longAux;
	private double promedioDistancia;
	
	public CentroDistribucion(double latitud, double longitud) {
		this.latitud = latitud;
		this.longitud = longitud;
		latAux = latitud;
		longAux = longitud;
		promedioDistancia = 0;
	}

	public double getLatitud() {
		return latitud;
	}

	public double getLongitud() {
		return longitud;
	}
	
	public double getPromedioDistancia() {
		return this.promedioDistancia;
	}
	
	public double distanciaRecta(double lat2, double lng2) {  
        double radioTierra = 6371;
        double dLat = Math.toRadians(lat2 - latAux);  
        double dLng = Math.toRadians(lng2 - longAux);  
        double sindLat = Math.sin(dLat / 2);  
        double sindLng = Math.sin(dLng / 2);  
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)  
                * Math.cos(Math.toRadians(latAux)) * Math.cos(Math.toRadians(lat2));  
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));  
        double distancia = radioTierra * va2;  
   
        return  distancia;  
    }  
	
	
	public void distanciaTotal(ArrayList<Cliente> clientes) {
		double resultado = 0;
		for (int i = 0; i < clientes.size() ; i++) {
			double radioTierra = 6371;//en kilómetros 
	        double dLat = Math.toRadians(clientes.get(i).getLatitud() - this.latitud);
	        double dLng = Math.toRadians(clientes.get(i).getLongitud() - this.longitud);
	        double sindLat = Math.sin(dLat / 2);
	        double sindLng = Math.sin(dLng / 2);
	        
	        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)  * Math.cos(Math.toRadians(this.latitud)) * Math.cos(Math.toRadians(clientes.get(i).getLatitud()));  
	        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));  
	        resultado += radioTierra * va2;  
		}
		this.promedioDistancia = resultado;
	}


	@Override
	public int compareTo(CentroDistribucion otro) {
		return (int) (this.promedioDistancia - otro.getPromedioDistancia());
	}
}
