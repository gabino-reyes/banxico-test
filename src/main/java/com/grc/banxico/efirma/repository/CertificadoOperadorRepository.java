package com.grc.banxico.efirma.repository;

import com.grc.banxico.efirma.repository.model.CertificadoOperador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificadoOperadorRepository extends JpaRepository<CertificadoOperador, Long> {

    @Query(value = "select * from certificado_operador co where numero_serie = :serie and nombre_comun = :nc", nativeQuery = true)
    Optional<CertificadoOperador> findCertificadoBySerialAndNombreComun(@Param("serie") String serie, @Param("nc") String nc);
}
