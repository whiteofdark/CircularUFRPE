package net.alunando.circularufrpe;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private int token;
    private String avatar;

    public Usuario(int id, String nome, String email, String senha, int token, String avatar) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.token = token;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
