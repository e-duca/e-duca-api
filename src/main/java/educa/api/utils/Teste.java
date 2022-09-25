package educa.api.utils;

public class Teste {

    public static void main(String[] args) {
        List<String> listaObj = new List(5);

        System.out.println("Adicionando elementos à lista...");

        listaObj.add("Arroz");
        listaObj.add("Feijão");
        listaObj.add("Carne");
        listaObj.add("Peixe");

        System.out.println("Exibindo elementos da lista...");

        listaObj.all();

        System.out.println("\nExibindo elemento de valor 2...");

        System.out.println(listaObj.get("Peixe"));

        System.out.println("\nApagando elemento na posição [2]...");

        System.out.println(listaObj.removeI(1));

        System.out.println("\nExibindo elementos da lista...");

        listaObj.all();

        System.out.println("\nApagando por elemento...");

        System.out.println(listaObj.removeE("Feijão"));

        System.out.println("\nExibindo elementos da lista...");

        listaObj.all();

        System.out.printf("\nExibindo total de elementos...\n");

        System.out.println(listaObj.getTamanho());

        System.out.println("\nExibindo elemento <Peixe> do indice 2...");

        System.out.println(listaObj.getElemento(2));

        System.out.println("\nLimpando lista...");

        listaObj.limpa();
    }
}
