package com.example.demo.parametros.multiplicadorCostoAlmacenamiento;

import com.example.demo.entidades.Base;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MultiplicadorCostoAlmacenamiento extends Base {

    private float valor;

}
