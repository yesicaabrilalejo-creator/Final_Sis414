package com.sicjac.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "distribuidores")
public class Distribuidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false, length = 160)
    private String razonSocial;

    @Column(nullable = false, length = 120)
    private String contacto;

    @Column(nullable = false, length = 80)
    private String departamento;

    @Column(nullable = false, length = 80)
    private String ciudad;

    @Column(nullable = false, length = 250)
    private String direccion;

    @Column(length = 30)
    private String telefono;

    @Column(length = 30)
    private String celular;

    @Column(length = 30)
    private String whatsapp;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(length = 250)
    private String facebook;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoDistribuidor tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoDistribuidor estado;
}
