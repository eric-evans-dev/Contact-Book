import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

public class ContactBook {
    private Scanner input;
    private ArrayList<Contact> contacts;

    public ContactBook(){
        input = new Scanner(System.in);
        contacts = new ArrayList<>();
        loadContacts();
    }

    //Starts running the whole program
    public void run(){
        //used to continue running run() until changed to false
        boolean running = true;


        do{
            printMenu();
            int choice = readNumber();
            switch(choice){
                case 1:
                    addContact();
                    System.out.print("Press enter to continue...");
                    input.nextLine(); // makes it so there's a break between showing the prompted choice and printing the menu again
                    break;
                case 2:
                    listContacts();
                    System.out.print("Press enter to continue...");
                    input.nextLine();
                    break;
                case 3:
                    searchContact();
                    System.out.print("Press enter to continue...");
                    input.nextLine();
                    break;
                case 4:
                    editContact();
                    System.out.print("Press enter to continue...");
                    input.nextLine();
                    break;
                case 5:
                    deleteContact();
                    System.out.print("Press enter to continue...");
                    input.nextLine();
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    running = false;
                    saveContacts();
                    break;
                default:
                    System.out.println("Invalid option, please enter 1-6!");
                    System.out.print("Press enter to continue...");
                    input.nextLine();
                    break;
            }
        }while(running);

    }

    //makes it easier to print the menu when needed
    private void printMenu(){
        System.out.println("================================");
        System.out.println("         CONTACT BOOK");
        System.out.println("================================");
        System.out.println("1. Add Contact");
        System.out.println("2. List Contacts");
        System.out.println("3. Search");
        System.out.println("4. Edit Contact");
        System.out.println("5. Delete Contact");
        System.out.println("6. Exit");
        System.out.println("--------------------------------");
        System.out.print("Choose an option: ");
    }

    //add contacts when prompted
    private void addContact(){
        System.out.print("Name: ");
        String name = input.nextLine().trim();
        System.out.print("Phone Number: ");
        String phone = input.nextLine().trim();
        System.out.print("Email: ");
        String email = input.nextLine().trim();
        Contact contact = new Contact(name, email, phone);
        contacts.add(contact);
        System.out.println("Contact added successfully!");
    }

    //lists contacts when prompted
    private void listContacts(){
        if(contacts.isEmpty()){
            System.out.println("Nothing to list!");
        }
        else{
            printHeader();
            int i = 0;
            for(Contact contact : contacts){
                i++;
                System.out.printf("%-3d %s%n", i, contact);
            }
            System.out.printf("%d contacts found!\n", i);
        }

    }

    //searches for contacts with partial matches
    private void searchContact(){
        if(contacts.isEmpty()){
            System.out.println("Nothing to search!");
        }
        else{
            System.out.print("Search for: ");
            String term = input.nextLine().trim().toLowerCase();
            int count = 0;
            for(int i = 0; i < contacts.size(); i++){
                if(term.equals("")){
                    System.out.println("Nothing to search!");
                    break;
                }
                Contact contact = contacts.get(i);
                if(i == 0){
                    printHeader();
                }
                // makes it so partial matches are found
                if(contact.getName().toLowerCase().contains(term)){
                    System.out.printf("%-3d %s%n", i+1,contact);
                    count++;
                }
            }
            if(count == 0){
                System.out.println("Contact not found!");
            }else{
                System.out.printf("%d matches found!\n", count);
            }
        }
    }

    private void editContact(){
        int choice = chooseContact();

        if(choice == -1){
            return;
        }else{
            Contact contact =  contacts.get(choice);
            System.out.println("Edit contact: ");
            System.out.printf("Name [%s]: ", contact.getName());
            String newName = input.nextLine().trim();
            if(!newName.isEmpty()){
                contact.setName(newName);
            }

            System.out.printf("Phone Number [%s]: ", contact.getPhoneNumber());
            String newPhone = input.nextLine().trim();
            if(!newPhone.isEmpty()){
                contact.setPhoneNumber(newPhone);
            }

            System.out.printf("Email [%s]: ", contact.getEmail());
            String newEmail = input.nextLine().trim();
            if(!newEmail.isEmpty()){
                contact.setEmail(newEmail);
            }

            printHeader();
            System.out.printf("%-3d %s%n", choice+1,contact);
            System.out.println("Contact updated successfully!");

        }
    }

    private void deleteContact(){
        int choice = chooseContact();
        if(choice == -1){
            return;
        }else{
            System.out.println("Are you sure you want to delete " + contacts.get(choice).getName() + "? (y/n)");
            char answer = input.nextLine().charAt(0);
            if(answer == 'y' || answer == 'Y'){
                contacts.remove(choice);
            }else if(answer == 'n' || answer == 'N'){
                System.out.println("Cancelled");
            } else{
                System.out.println("Invalid choice!");
                deleteContact();
            }
        }

    }

    private void saveContacts(){
        for(int i = 0; i < 3; i++) {
            try (FileWriter saved = new FileWriter("Contacts.txt")) {
                for (Contact contact : contacts) {
                    saved.write(contact.getName() + "|" + contact.getEmail() + "|" + contact.getPhoneNumber() + "\n");

                }
                return;
            } catch (IOException e) {
                System.out.println("Retrying...");
            }
        }
        System.out.println("Could not save contacts");

    }

    private void loadContacts(){
        File savedFile = new File("Contacts.txt");
        if(!savedFile.exists()){
            return;
        }

        try(Scanner saved = new Scanner(savedFile)){
            while(saved.hasNextLine()){
                String line = saved.nextLine();
                String[] parts = line.split("\\|");
                if(parts.length != 3){
                    continue;
                }
                contacts.add(new Contact(parts[0], parts[1], parts[2]));
            }
        }catch(FileNotFoundException e){
            System.out.println("Could not load contacts: " + e.getMessage());
        }

    }

    private int readNumber(){
        String choice = input.nextLine();

        try{
            return Integer.parseInt(choice.trim());

        }catch(NumberFormatException e){
            return -1;
        }

    }

    private void printHeader(){
        System.out.printf("%-3s %-15s %-15s %s%n", "#", "Name", "Phone", "Email");
        System.out.println("--------------------------------------------------------");
    }

    private int chooseContact(){
        if(contacts.isEmpty()){
            System.out.println("Nothing to choose!");
            return -1;
        }
        listContacts();
        System.out.print("Pick a contact: ");
        int choice = readNumber();

        if(choice <= 0 || choice > contacts.size()){
            System.out.println("Invalid choice!");
            return -1;
        }
        else{
            return choice - 1;
        }

    }

}
