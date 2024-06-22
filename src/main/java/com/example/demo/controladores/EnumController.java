package com.example.demo.controladores;

import com.example.demo.enums.ModeloInventario;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins =  "*")
@RequestMapping(path = "/ModInv")
public class EnumController {

    @GetMapping("/all")
    public List<String> getEnumValues() {
        return Arrays.stream(ModeloInventario.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}