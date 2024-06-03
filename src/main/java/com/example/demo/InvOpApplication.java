package com.example.demo;

import com.example.demo.entidades.Articulo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class InvOpApplication {

	public static Connection ConectarBD(){

		Connection conexion;
		String host = "jdbc:mysql://localhost/";
		String user = "root";
		String pass = "";
		String bd = "prueba";

		System.out.println("Conectando...");

		try {
			conexion = DriverManager.getConnection(host+bd,user,pass);
			System.out.println("Conexion exitosa");
		} catch (SQLException e){
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}

		return conexion;

	}

	public static void main(String[] args) {

		SpringApplication.run(InvOpApplication.class, args);
		System.out.println("Hola, estoy andando bien");
		Connection bd = ConectarBD();
	}

	@Bean
	CommandLineRunner init(){
		return args -> {
			Date fechaActual = new Date();

			// Crear un formateador de fecha con el patrón dd/MM/yyyy
			SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

			// Formatear la fecha actual como cadena
			String fechaFormateada = formateador.format(fechaActual);

			// Imprimir la fecha formateada
			System.out.println("Fecha formateada: " + fechaFormateada);

			Articulo articulo1 = Articulo.builder()
					.nombre("Cocaína")
					.fechaAlta(fechaActual)
					.precioCompra(100)
					.stockActual(2)
					.stockMinimo(45)
					.fechaModificacion(fechaActual)
					.build();


			System.out.println(articulo1.getNombre());
		};
	}



}
