package net.alunando.circularufrpe.classes;

public class Post {
    private int id;
    private int usuario;
    private String data;
    private String hora;
    private int likes;
    private int comments;
    private String texto;

    public Post(int id, int usuario, String data, String hora, int likes, String texto) {
        this.id = id;
        this.usuario = usuario;
        this.data = data;
        this.hora = hora;
        this.likes = likes;
        this.texto = texto;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
