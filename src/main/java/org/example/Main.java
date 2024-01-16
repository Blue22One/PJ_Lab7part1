package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

record Carte(String titlul, String autorul, int anul){
};
public class Main {
    public static void main(String[] args) {
        Carte carte = new Carte("C++","Schildt",2010);
        //System.out.println(carte.anul());
        Map<Integer, Carte> mapa = citire();
        mapa.entrySet().forEach(System.out::println);
        System.out.println("2) Dati cheia de sters (nr pozitiv nenul):");
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        mapa.remove(x);

        System.out.println("3)--------------------------------------------------------");
        mapa.entrySet().forEach(System.out::println);
        Carte introBook = new Carte("Crima si pedeapsa", "Fiodor Dostoievski", 2015);
        mapa.putIfAbsent(x, introBook);

        System.out.println("4)-------------------------------------------------------");
        mapa.entrySet().forEach(System.out::println);
        scriere(mapa);

        System.out.println("--------------------------------------------------------");
        System.out.println("5) Colectia Yuval Noah Harari:");
        var books = mapa.entrySet().stream()
                .filter(m -> m.getValue().autorul().equals("Yuval Noah Harari"))
                .collect(Collectors.toSet());
        books.forEach(System.out::println);

        System.out.println("--------------------------------------------------------");
        System.out.println("6) Ordonare cu Lambda & Stream API:");
        books.stream()
                .sorted((b1,b2)->b1.getValue().titlul().compareTo(b2.getValue().titlul()))
                .forEach(System.out::println);

        System.out.println("--------------------------------------------------------");
        Optional<Map.Entry<Integer,Carte>> oldest = mapa.entrySet().stream()
                .min(Comparator.comparing(m->m.getValue().anul()));

        System.out.println(oldest);
    }
    public static Map<Integer, Carte> citire()
    {
        try{
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/main/resources/carti.json");
            var m = mapper.readValue(file, new TypeReference<HashMap<Integer, Carte>>() {});
            return m;
        }
        catch(IOException exc)
        {
            exc.printStackTrace();
        }
        return null;
    }
    public static void scriere(Map<Integer, Carte> mapa)
    {
        try
        {
            ObjectMapper mapper=new ObjectMapper();
            File file = new File("src/main/resources/carti.json");
            mapper.writeValue(file, mapa);
        }
        catch(IOException exc)
        {
            exc.printStackTrace();
        }
    }
}