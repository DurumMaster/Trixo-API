package trixo.api.trixo_api.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.cloud.Timestamp;

import trixo.api.trixo_api.controllers.TimestampDeserializer;
import trixo.api.trixo_api.controllers.TimestampSerializer;

public class Product {
    private int id;
    private String nombre;
    private double precio;
    private String talla;
    private String userID;
    @JsonDeserialize(using = TimestampDeserializer.class)
    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp fechaCreacion;
    private int stock;
    private boolean activo;
    private String descripcion;
    private String materiales;
    private String envio;
    private double valoracion;

    public Product() {
    }

    public Product(int id, String nombre, double precio, String talla, Timestamp fechaCreacion, int stock,
            boolean activo, String descripcion, String materiales, String envio, double valoracion) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.talla = talla;
        this.fechaCreacion = fechaCreacion;
        this.stock = stock;
        this.activo = activo;
        this.descripcion = descripcion;
        this.materiales = materiales;
        this.envio = envio;
        this.valoracion = valoracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEnvio() {
        return envio;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public int getId() {
        return id;
    }

    public String getMateriales() {
        return materiales;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public String getTalla() {
        return talla;
    }

    public double getValoracion() {
        return valoracion;
    }

    public String getUserID() {
        return userID;
    }
    
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEnvio(String envio) {
        this.envio = envio;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMateriales(String materiales) {
        this.materiales = materiales;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public void setValoracion(double valoracion) {
        this.valoracion = valoracion;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
