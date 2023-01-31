package facilityLocation;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {

	public static ArrayList<CentroDistribucion> ordenarArreglo(ArrayList<CentroDistribucion> centros) {
		ArrayList<CentroDistribucion> ret = new ArrayList<CentroDistribucion>();
		for(CentroDistribucion centro : centroOrdenados(centros)) {
			ret.add(centro);
		 }
		return ret;
	}
	
	
	private static ArrayList<CentroDistribucion> centroOrdenados(ArrayList<CentroDistribucion> centros){
		Collections.sort(centros);
		
		return centros;
	}
}
