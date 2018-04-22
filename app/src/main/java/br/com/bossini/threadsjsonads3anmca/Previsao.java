package br.com.bossini.threadsjsonads3anmca;

/**
 * Marcelo Victor da Silva
 * RA: 816119006
 * ADSMCA3
 */

public class Previsao {


    public Previsao (){

    }

    public Previsao (double min, double max, String descricao, String cidade){
        setMin(min);
        setMax(max);
        setDescricao(descricao);
        setCidade(cidade);
    }

    public Previsao (double min, double max, String descricao){
        setMin(min);
        setMax(max);
        setDescricao(descricao);
    }

    private double min, max;
    private String descricao, cidade;

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
