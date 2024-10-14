package edu.coderhouse.jpa;

import edu.coderhouse.jpa.entity.Client;
import edu.coderhouse.jpa.entity.Invoice;
import edu.coderhouse.jpa.entity.InvoiceDetail;
import edu.coderhouse.jpa.entity.Product;
import edu.coderhouse.jpa.service.DaoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class JpaApplication implements CommandLineRunner {

	private final DaoFactory daoFactory;

	@Autowired
	public JpaApplication(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		validarConexionMySQL();

		Client client = new Client("Francisco", "Sastre", "55145269");
		daoFactory.create(client);

		Product product1 = new Product("lapicera", "loquita", 4, 1210.00);
		Product product2 = new Product("goma", "borratodo", 10, 500.00);
		daoFactory.create(product1);
		daoFactory.create(product2);

		Invoice invoice = new Invoice(client, LocalDateTime.now(), 1710.00);
		daoFactory.create(invoice);

		InvoiceDetail detail1 = new InvoiceDetail(invoice, product1, 1, 1210.00);
		InvoiceDetail detail2 = new InvoiceDetail(invoice, product2, 1, 500.00);
		daoFactory.create(detail1);
		daoFactory.create(detail2);

		mostrarFacturaGuardada(invoice);


		List<Product> productos = daoFactory.getAllProducts();
		System.out.println("Lista de productos:");
		productos.forEach(System.out::println);
	}

	private void validarConexionMySQL() {
		try (Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/ventasonline", "root", "")) {
			if (connection != null) {
				System.out.println("Conexión exitosa a MySQL");
			} else {
				System.out.println("Error en la conexión a MySQL");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void mostrarFacturaGuardada(Invoice invoice) {
		Invoice savedInvoice = daoFactory.getInvoiceById(invoice.getId());
		System.out.println("Factura guardada: " + savedInvoice);
	}

}
