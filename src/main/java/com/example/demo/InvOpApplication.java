package com.example.demo;

import com.example.demo.entidades.Articulo;
import com.example.demo.repositorios.ArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private ArticuloRepository articuloRepository;

	public static void main(String[] args) {

		SpringApplication.run(InvOpApplication.class, args);
		System.out.println("Hola, estoy andando bien");
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

			articuloRepository.save(articulo1);

			System.out.println(articulo1.getNombre());
		};
	}



}
