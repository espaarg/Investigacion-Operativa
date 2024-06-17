package com.example.demo.parametros.multiplicadorCostoAlmacenamiento;

import com.example.demo.entidades.Articulo;
import com.example.demo.repositorios.ArticuloRepository;
import com.example.demo.repositorios.BaseRepository;
import com.example.demo.servicios.ArticuloService;
import com.example.demo.servicios.BaseServiceImpl;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class MultiplicadorCostoAlmacenamientoServiceImpl extends BaseServiceImpl<MultiplicadorCostoAlmacenamiento, Long> implements MultiplicadorCostoAlmacenamientoService {

    @Autowired
    private MultiplicadorCostoAlmacenamientoRepository multiplicadorCostoAlmacenamientoRepository;

    public MultiplicadorCostoAlmacenamientoServiceImpl(BaseRepository<MultiplicadorCostoAlmacenamiento, Long> baseRepository, MultiplicadorCostoAlmacenamientoRepository articuloRepository) {
        super(baseRepository);
        this.multiplicadorCostoAlmacenamientoRepository = multiplicadorCostoAlmacenamientoRepository;
    }

    @Override
    public List<MultiplicadorCostoAlmacenamiento> traerMCA() throws Exception {
        try {
            List<MultiplicadorCostoAlmacenamiento> mCAList = multiplicadorCostoAlmacenamientoRepository.traerMCA();
            return mCAList;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    };

    @Override
    public Page findAllPageable(Pageable pageable) throws Exception {
        return null;
    }

}
