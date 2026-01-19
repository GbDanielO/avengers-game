package br.com.avengers.shared;

import java.util.Date;

public class ResponseError {

    private Date timestamp = new Date();
    private String status = "error";
    private int statusCode = 400;
    private String mensagem;

    public ResponseError(){}

    public ResponseError(String mensagem){
        this.mensagem = mensagem;
    }

    public ResponseError(String mensagem, int statusCode, String status) {
        this.status = status;
        this.statusCode = statusCode;
        this.mensagem = mensagem;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
