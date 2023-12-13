package entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Libro {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "titulo")
    private String titulo;
    @Basic
    @Column(name = "autor_id")
    private Integer autorId;
    @ManyToOne
    @JoinColumn(name = "autor_id", referencedColumnName = "id")
    private Autor autorByAutorId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAutorId() {
        return autorId;
    }

    public void setAutorId(Integer autorId) {
        this.autorId = autorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return id == libro.id && Objects.equals(titulo, libro.titulo) && Objects.equals(autorId, libro.autorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, autorId);
    }

    public Autor getAutorByAutorId() {
        return autorByAutorId;
    }

    public void setAutorByAutorId(Autor autorByAutorId) {
        this.autorByAutorId = autorByAutorId;
    }
}
