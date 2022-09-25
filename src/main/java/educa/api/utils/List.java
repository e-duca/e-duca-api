package educa.api.utils;

public class List<T> {

    private T[] vetor;
    private int nroElem;

    public List(int tam) {
        this.vetor = (T[]) new Object[tam];
        this.nroElem = 0;
    }

    public void add(T elemento) {
        if (nroElem != vetor.length) {
            for (int i = 0; i < vetor.length; i++) {
                vetor[nroElem] = elemento;
                nroElem++;
                break;
            }
        } else {
            System.out.println("Lista Cheia!");
        }
    }

    public void all() {
        System.out.print("[");
        for (int i = 0; i < nroElem; i++) {
            System.out.print(vetor[i] + ",");
        }
        System.out.print("]");
    }

    public int get(T elemento) {
        for (int i = 0; i < nroElem; i++) {
            if (elemento.equals(vetor[i])) {
                return i;
            }
        }
        return -1;
    }

    public boolean removeI(int index) {
        if (!(index < 0 || index > nroElem)) {
            for (int i = index; i < nroElem; i++) {
                vetor[i] = vetor[i + 1];
            }
            nroElem--;
            return true;
        }
        return false;
    }

    public boolean removeE(T elemento) {
        return removeI(get(elemento));
    }

    public Integer getTamanho() {
        return nroElem;
    }

    public T getElemento(int indice) {
        if (!(indice <0 || indice >= nroElem)) {
            return vetor[indice];
        }
        return null;
    }

    public void limpa() {
        for (int i = 0; i < nroElem; i++) {
            vetor[i] = null;
        }
        nroElem = 0;
    }

}