package facilityLocation;

public class CreacionJSON {

	public static void main(String[] args) {
		
		Cliente c1 = new Cliente(-34.516466, -58.704028);
		Cliente c2 = new Cliente(-34.524237, -58.695359);
		Cliente c3 = new Cliente(-34.521373, -58.712096);
		Cliente c4 = new Cliente(-34.517280, -58.693943);
		Cliente c5= new Cliente(-34.523776, -58.703622);
		Cliente c6 = new Cliente(-34.525395, -58.710551);
		Cliente c7 = new Cliente(-34.514447, -58.696561);
		Cliente c8 = new Cliente(-34.528144, -58.695746);
		Cliente c9 = new Cliente(-34.516971, -58.686175);
		Cliente c10 = new Cliente(-34.513046, -58.702526);
		Cliente c11 = new Cliente(-34.513788, -58.711367);
		
		ClientesJSON clientes = new ClientesJSON();
		clientes.addCoord(c1);
		clientes.addCoord(c2);
		clientes.addCoord(c3);
		clientes.addCoord(c4);
		clientes.addCoord(c5);
		clientes.addCoord(c6);
		clientes.addCoord(c7);
		clientes.addCoord(c8);
		clientes.addCoord(c9);
		clientes.addCoord(c10);
		clientes.addCoord(c11);
		
		
		CentroDistribucion cd1 = new CentroDistribucion(-34.521568, -58.700252);
		CentroDistribucion cd2 = new CentroDistribucion(-34.513682, -58.707504);
		CentroDistribucion cd3 = new CentroDistribucion(-34.523689, -58.714886);
		CentroDistribucion cd4 = new CentroDistribucion(-34.515238, -58.691969);
		
		CentroDistribucionJSON centros = new CentroDistribucionJSON();
		centros.addCoord(cd1);
		centros.addCoord(cd2);
		centros.addCoord(cd3);
		centros.addCoord(cd4);
		
		
		String jsonPrettyCliente = clientes.generarJSONPretty();
		String JsonPrettyCD = centros.generarJSONPretty();
		
		clientes.guardarJSON(jsonPrettyCliente, "Clientes.JSON");
		centros.guardarJSON(JsonPrettyCD, "CentrosDistribucion.JSON");

	}

}
