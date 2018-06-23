package net.alunando.circularufrpe;

public class Comentario {
    private int id;
    private int post;
    private int usuario;
    private String data;
    private String hora;
    private String texto;

    public Comentario(int id, int post, int usuario, String data, String hora, String texto) {
        this.id = id;
        this.post = post;
        this.usuario = usuario;
        this.data = data;
        this.hora = hora;
        this.texto = texto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
