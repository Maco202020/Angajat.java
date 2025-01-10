import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void scriere(List<Angajat> lista) {
        try {
            ObjectMapper mapper=new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            File file=new File("C:\\Users\\macov\\Desktop\\java_laboratoare\\lab6ex1\\src\\main\\resources\\angajati.json");
            mapper.writeValue(file,lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<Angajat> citire() {
        try {
            File file=new File("C:\\Users\\macov\\Desktop\\java_laboratoare\\lab6ex1\\src\\main\\resources\\angajati.json");
            ObjectMapper mapper=new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            List<Angajat> persoane = mapper
                    .readValue(file, new TypeReference<List<Angajat>>(){});
            return persoane;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        List<Angajat>a=new ArrayList<>();
        a.add(new Angajat("Ionut","Sef", LocalDate.parse("2022-04-01"),15000));
        a.add(new Angajat("Mark","Contabil", LocalDate.parse("2017-12-01"),3570));
        a.add(new Angajat("Vasile","Programator", LocalDate.parse("2019-12-01"),9040));
        a.add(new Angajat("Merry","Director", LocalDate.parse("2019-05-01"),8089));
        a.add(new Angajat("ion","Programator", LocalDate.parse("2020-03-01"),1205));
        a.add(new Angajat("Ionut","CO", LocalDate.parse("2020-04-08"),1034));
        a.add(new Angajat("Ionel","Sef pe Curatenie", LocalDate.parse("2021-04-01"),1210));
        scriere(a);

        List<Angajat> Angajati=citire();
        // Angajati.forEach(System.out::println);

        Scanner sc=new Scanner(System.in);
        while(true)
        {
            System.out.println("\n");
            System.out.println("0.Iesire");
            System.out.println("1.Afisare angajati");
            System.out.println("2.Afisare peste 2500");
            System.out.println("3.Lista angajati anul trecut aprilie (sef sau director)");
            System.out.println("4.Afisare angajati !(sef sau director)");
            System.out.println("5.Extragerea din lista de angajați a unei liste de String-uri cu numele angajaților");
            System.out.println("6.Afisarea salariilor <3000 ");
            System.out.println("7.Afisarea primului angajat din firma");
            System.out.println("8.Salariul minim / maxim / mediu");
            System.out.println("9.Afisare daca exista cel putin un Ion in firma");
            System.out.println("10.Nr Persoane angajata vara anului precedent");
            System.out.println("Optiunea dvs:");
            int opt=sc.nextInt();
            switch (opt) {
                case 0:
                    System.exit(0);
                case 1:
                    Angajati.forEach(System.out::println);
                    break;

                case 2:
                    Angajati.stream()
                            .filter(p -> p.getSalariul() > 2500)
                            .forEach(System.out::println);
                    break;

                case 3:
                    List<Angajat> l = Angajati.stream()
                            .filter(p -> p.getData_angajarii().getYear() == LocalDate.now().getYear() - 1 && p.getData_angajarii().getMonthValue() == 4 && (p.getPostul().equals("Sef") || p.getPostul().equals("Director")))
                            .collect(Collectors.toList());
                    l.forEach(System.out::println);
                    break;

                case 4:
                    Angajati.stream()
                            .filter(p -> !(p.getPostul().equals("Sef") || p.getPostul().equals("Director")))
                            .forEach(System.out::println);
                    break;
                case 5:
                    List<String> nume = Angajati.stream()
                            .map(Angajat::getNumele)
                            .collect(Collectors.toList());
                    nume.forEach(System.out::println);
                    break;
                case 6:
                    Angajati.stream()
                            .filter(p -> p.getSalariul() < 3000)
                            .map(Angajat::getSalariul)
                            .forEach(System.out::println);
                    break;
                case 7:
                    Angajati.stream()
                            .min(Comparator.comparing(Angajat::getData_angajarii))
                            .ifPresentOrElse(System.out::println, () -> System.out.println("Nu exista angajati"));
                    break;
                case 8:
                    System.out.println("Salariul minim: "+Angajati.stream()
                            .map(Angajat::getSalariul)
                            .min(Comparator.comparing(c->c)).get());

                    System.out.println("Salariul maxim: "+Angajati.stream()
                            .map(Angajat::getSalariul)
                            .max(Comparator.comparing(c->c)).get());

                    System.out.println("Salariul mediu: "+Angajati.stream()
                            .mapToDouble(Angajat::getSalariul)
                            .sum()/Angajati.size());

                    break;
                case 9:
                    if(Angajati.stream().anyMatch(p->p.getNumele().equals("Ion")))
                        System.out.println("Firma are cel puțin un Ion angajat");
                    else
                        System.out.println("Firma nu are nici un Ion angajat");
                    break;
                case 10:
                    System.out.println("Numarul de angajati angajati vara anului precedent: "+Angajati.stream()
                            .filter(p->p.getData_angajarii().getYear()==LocalDate.now().getYear()-1 && (p.getData_angajarii().getMonthValue()>=6 && p.getData_angajarii().getMonthValue()<=8))
                            .count());
                    break;
                default:
                    System.out.println("Optiune invalida");
                    break;
            }
        }
    }
}

