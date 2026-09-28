# Contact Book

A command-line contact manager written in Java. Add, view, search, edit, and delete contacts from a simple menu, with everything saved to a file so your contacts are still there the next time you run it.

## Features

- **Add, list, edit, and delete** contacts, each storing a name, phone number, and email
- **Search** by name with case-insensitive, partial matching ("jo" finds both "John" and "Joanna")
- **Edit in place**: press Enter at any prompt to keep a field's current value
- **Delete confirmation** to prevent accidental removal
- **Persistent storage**: contacts are saved to a text file on exit and loaded on startup
- **Robust input handling**: invalid menu choices and out-of-range selections are rejected with a message instead of crashing
- **Fault-tolerant file handling**: saving retries automatically on failure, and malformed lines in the data file are skipped when loading

## Sample Output

```
================================
         CONTACT BOOK
================================
1. Add Contact
2. List Contacts
3. Search
4. Edit Contact
5. Delete Contact
6. Exit
--------------------------------
Choose an option: 2

#   Name               Phone           Email
--------------------------------------------------------
1   Jane Doe       304-555-0142    Jan@example.com
2   John Doe       304-555-0199    JDoe@example.com
3   Eric Evans     304-555-0117    Eric@example.com
3 contacts
```

## Getting Started

### Running in IntelliJ IDEA

1. Clone the repository and open the folder in IntelliJ.
2. Open `src/Main.java` and click the green run arrow.

### Running from the command line

Run the program from the project root so the data file is created there.

## Project Structure

```
src/
├── Main.java          Entry point: creates a ContactBook and starts it
├── Contact.java       Data class for a single contact
└── ContactBook.java   Menu loop, contact operations, and file storage
```

## Data Storage

Contacts are stored in `Contacts.txt`, one contact per line, with fields separated by a pipe character "|":

```
John Doe|John.Doe@gmail.com|123-456-7890
```

A pipe was chosen over a comma because commas can appear in names (for example, "Smith, John"), which would break parsing. The file is created automatically on first save.

## Design Notes

- **Separation of responsibilities**: `Contact` holds data, `ContactBook` handles behavior, and `Main` only starts the program.
- **Line-based input**: all input is read with `Scanner.nextLine()` and parsed manually, avoiding the common skipped-input issue when mixing `nextInt()` and `nextLine()`.
- **Shared helpers**: `readNumber()` and `chooseContact()` centralise input parsing and contact selection, so edit and delete reuse the same validated logic.

## Planned Features

- User accounts with login, each with a separate contact book
- Email and phone number validation
- Alphabetical sorting and duplicate detection
