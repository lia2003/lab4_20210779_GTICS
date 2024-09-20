package com.example.lab4_20210779.entity;

// Clase para el item del carrito
public class ItemCarrito {
    private Flor flor;
    private int cantidad;

    public ItemCarrito(Flor flor, int cantidad) {
        this.flor = flor;
        this.cantidad = cantidad;
    }
    // Getter y Setter para flor
    public Flor getFlor() {
        return flor;
    }

    public void setFlor(Flor flor) {
        this.flor = flor;
    }

    // Getter y Setter para cantidad
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

