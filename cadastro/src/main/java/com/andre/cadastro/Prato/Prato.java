package com.andre.cadastro.Prato;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.andre.cadastro.Restaurante.Restaurante;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="prato")
@Getter
@Setter
public class Prato extends PanacheEntityBase {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
	
	public String nome;
	
	public String descricao;
	
	@ManyToOne
	public Restaurante restaurante;
	
	public BigDecimal preco;
	
}
