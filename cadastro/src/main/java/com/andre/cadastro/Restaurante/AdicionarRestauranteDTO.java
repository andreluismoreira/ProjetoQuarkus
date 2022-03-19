package com.andre.cadastro.Restaurante;

import com.andre.cadastro.Exception.DTO;
import com.andre.cadastro.Exception.ValidDTO;
import com.andre.cadastro.Localizacao.LocalizacaoDTO;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ValidDTO
public class AdicionarRestauranteDTO  implements DTO {

    @NotEmpty
    @NotNull
    public String proprietario;

    @Pattern(regexp = "[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}\\/[0-9]{4}\\-[0-9]{2}")
    @NotNull
    public String cnpj;

    @Size(min = 3,max = 30)
    public String nomeFantasia;

    public LocalizacaoDTO localizacao;

    @Override
    public boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
       constraintValidatorContext.disableDefaultConstraintViolation();
       if(Restaurante.find("cnpj", cnpj).count() > 0){
           constraintValidatorContext.buildConstraintViolationWithTemplate("CNPJ já Cadastrado")
                   .addPropertyNode("cnpj")
                   .addConstraintViolation();
           return false;
       }
       return true;
    }
}
