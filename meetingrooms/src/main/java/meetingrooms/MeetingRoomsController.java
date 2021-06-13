package meetingrooms;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MeetingRoomsController {

    private Scanner scanner;
    private MeetingRoomsRepository meetingRoomsRepository = new MariaDbMeetingRoomsRepository();
    //    private MeetingRoomsRepository meetingRoomsRepository = new InMemoryMeetingRoomsRepository();
    private MeetingRoomsService meetingRoomsService = new MeetingRoomsService(meetingRoomsRepository);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        new MeetingRoomsController().start(scanner);
    }

    private void start(Scanner scanner) {
        this.scanner = scanner;

        int selection;
        do {
            selection = selectFromMenu();
            switch (selection) {
                case 0 -> newMeetingRoom();
                case 1 -> orderByName();
                case 2 -> reverseOrder();
                case 3 -> evenMeetengRooms();
                case 4 -> sizes();
                case 5 -> findByExactName();
                case 6 -> findByNamePart();
                case 7 -> findBySize();
            }
        } while (selection < 8);
    }

    private int selectFromMenu() {
        System.out.println("""
                                
                Menü:     
                0. Tárgyaló rögzítése
                1. Tárgyalók névsorrendben
                2. Tárgyalók név alapján visszafele sorrendben
                3. Minden második tárgyaló
                4. Területek
                5. Keresés pontos név alapján
                6. Keresés névtöredék alapján
                7. Keresés terület alapján
                8. Kilépés""");
        String selection;
        do {
            selection = scanner.nextLine();
        } while (!selection.matches("[0-8]{1}"));

        return Integer.parseInt(selection);
    }


    private void newMeetingRoom() {
        System.out.println("Tárgyaló neve: ");
        String name = scanner.nextLine();
        System.out.println("szélessége méterben: ");
        int width = getNumber(3);
        System.out.println("hossza méterben: ");
        int length = getNumber(3);

        meetingRoomsService.newMeetingRoom(new MeetingRoom(name, width, length));
    }

    private int getNumber(int digit) {
        String value;
        do {
            value = scanner.nextLine();
        } while (!value.matches("[0-9]{1," + digit + "}"));
        return Integer.parseInt(value);
    }


    private void orderByName() {
        System.out.println("Tárgyalók névsorrendben:");
        meetingRoomsService.orderByName().forEach(System.out::println);
    }


    private void reverseOrder() {
        System.out.println("Tárgyalók fordított névsorrendben:");
        meetingRoomsService.reverseOrder().forEach(System.out::println);
    }


    private void evenMeetengRooms() {
        System.out.println("Névsorrendben minden második tárgyaló:");
        meetingRoomsService.evenMeetengRooms().forEach(System.out::println);
    }


    private void sizes() {
        System.out.println("Tárgyalók méret szerint csökkenő sorrendben:");
        meetingRoomsService.sizes().stream()
                .map(MeetingRoom::reportFormat)
                .forEach(System.out::println);
    }


    private void findByExactName() {
        System.out.println("Adja meg a tárgyaló nevét: ");
        String name = scanner.nextLine();

        Optional<MeetingRoom> meetingRoom = meetingRoomsService.findByExactName(name);
        if (meetingRoom.isPresent()) {
            System.out.println(meetingRoom.get().reportFormat());
        } else {
            System.out.println("Nincs '" + name + "' nevű tárgyaló!");
        }
    }


    private void findByNamePart() {
        System.out.println("Adjon meg egy töredéket a keresett tárgyaló nevéből: ");
        String namePart = scanner.nextLine();

        List<MeetingRoom> meetingRooms = meetingRoomsService.findByNamePart(namePart);
        if (meetingRooms.isEmpty()) {
            System.out.println("Nem található a megadott '" + namePart + "' részlet semelyik tárgyaló nevében!");
        } else {
            meetingRooms.stream().map(MeetingRoom::reportFormat).forEach(System.out::println);
        }
    }


    private void findBySize() {
        System.out.println("Legalább mekkora tárgyalót keres: ");
        int size = getNumber(6);

        List<MeetingRoom> meetingRooms = meetingRoomsService.findBySize(size);
        if (meetingRooms.isEmpty()) {
            System.out.println("Nincs ilyen nagy tárgyaló!");
        } else {
            meetingRooms.stream().map(MeetingRoom::reportFormat).forEach(System.out::println);
        }

    }
}
