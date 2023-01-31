package facilityLocation;

public class Cliente {
	private  double latitud;
	private  double longitud;
	private static double latAux;
	private static double longAux;
	
	public Cliente(double latitud, double longitud) {
		this.latitud = latitud;
		this.longitud = longitud;
		latAux = latitud;
		longAux = longitud;
	}

	@Override
	public String toString() {
		return "Persona [latitud=" + latitud + ", longitud=" + longitud + "]";
	}

	public double getLatitud() {
		return latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	 public int calcularDistancia(double lon2, double lat2) {

			 double earthRadius = 6371; // km
			 
			 double lat1 = latAux;
			 double lon1 = longAux;

			 lat1 = Math.toRadians(lat1);
			 lon1 = Math.toRadians(lon1);
			 lat2 = Math.toRadians(lat2);
			 lon2 = Math.toRadians(lon2);

			 double dlon = (lon2 - lon1);
			 double dlat = (lat2 - lat1);

			 double sinlat = Math.sin(dlat / 2);
			 double sinlon = Math.sin(dlon / 2);

			 double a = (sinlat * sinlat) + Math.cos(lat1)*Math.cos(lat2)*(sinlon*sinlon);
			 double c = 2 * Math.asin (Math.min(1.0, Math.sqrt(a)));

			 double distanceInMeters = earthRadius * c * 1000;

			 return (int) distanceInMeters;

	}
}
