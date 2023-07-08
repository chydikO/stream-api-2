package org.itstep;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class StreamDemo {
    public static void main(String[] args) {
        List<Address> addresses = List.of(
                new Address("69000", "Ukraine", "Kharkiv", "Shevchenka, 1"),
                new Address("49000", "Ukraine", "Dnipro", "Yavornitskogo, 101"),
                new Address("49000", "Ukraine", "Dnipro", "Televizijna, 4a"),
                new Address("49000", "Ukraine", "Dnipro", "Voskresenskogo, 5"),
                new Address("69000", "Ukraine", "Kharkiv", "Dovbush, 41"),
                new Address("32000", "Ukraine", "Lviv", "Stepana Banderi, 34")
        );

        Consumer<Address> addressConsumer = new Consumer<Address>() {
            @Override
            public void accept(Address address) {
                System.out.println(address);
            }
        };
        addressConsumer = address -> System.out.println(address);
        addressConsumer = StreamDemo::consume;
        addressConsumer = System.out::println;

        List<?> cities = addresses.stream()
                //.map(Address::city) //.map(address -> address.city())
                //.distinct()
                .sorted(Comparator.comparing(Address::addressLine1).reversed())
                .map(Address::addressLine1)
                .toList();          //.collect(Collectors.toList());
        //.forEach(System.out::println);// .forEach(address -> System.out.println(address))
        System.out.println("cities = " + cities);
        //System.out.println(addresses);

        Optional<Address> optionalAddress = addresses.stream()
                .filter(address -> address.city().isBlank())
                .findFirst();
        optionalAddress.ifPresentOrElse(System.out::println,
                () -> System.err.println("Address is not present"));

        boolean containsLviv = addresses.stream()
                .anyMatch(address -> address.city().equals("Lviv"));
        System.out.println("containsLviv = " + containsLviv);
        boolean allCitiesAreLviv = addresses.stream()
                .allMatch(address -> address.city().equals("Lviv"));
        System.out.println("allCitiesAreLviv = " + allCitiesAreLviv);
        boolean containsAddressFromEvil = addresses.stream()
                .noneMatch(address -> address.country().equalsIgnoreCase("russia"));
        System.out.println("containsAddressFromEvil = " + containsAddressFromEvil);

        addresses.stream()
                .skip(1)
                .dropWhile(address -> !address.city().equals("Kharkiv"))
                .forEach(System.out::println);

        List<Integer> integers = List.of(1, 2, 3, 4, 5);
        int p = 1;
        for(Integer i: integers) {
            p *= i;
        }
        System.out.println("p = " + p);
        Integer multiplication = integers.stream().reduce(1, (prev, current) -> prev * current);
        System.out.println("multiplication = " + multiplication);

        addresses.stream()
                .skip(1) // skip Kharkiv
                .takeWhile(address -> address.city().equals("Dnipro")) // take Dnipro
                .forEach(System.out::println);
        System.out.println("---");
        addresses.stream()
                .max(Comparator.comparingInt(addr -> addr.addressLine1().length()))
                .ifPresent(System.out::println);

        List<City> cityList = List.of(
                new City("Dnipro", List.of(
                        new Address("49000", "Ukraine", "Dnipro", "address line 1")
                )),
                new City("Kharkiv", List.of(
                        new Address("69000", "Ukraine", "Kharkiv", "address line 2")
                ))
        );
        System.out.println("---");
        cityList.stream()
                .map(city -> city.address().stream())
                .flatMap((Function<Stream<Address>, Stream<Address>>) addressStream -> addressStream)
                .forEach(System.out::println);

//        if(first.isPresent()) {
//            Address address = first.get();
//            System.out.println(address);
//        }
    }

    static void consume(Object address) {
        System.out.println(address);
    }
}

record City(String name, List<Address> address) {}

record Address(String postCode,
               String country,
               String city,
               String addressLine1,
               String addressLine2) {
    public Address(String postCode,
                   String country,
                   String city,
                   String addressLine1) {
        this(postCode, country, city, addressLine1, "");
    }
}
