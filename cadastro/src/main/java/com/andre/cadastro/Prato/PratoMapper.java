package com.andre.cadastro.Prato;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface PratoMapper {

    PratoDTO toPratoDTO(Prato p);

    Prato toPrato(AdicionarPratoDTO dto);

    void toPrato(AtualizarPratoDTO dto, @MappingTarget Prato prato);

}
