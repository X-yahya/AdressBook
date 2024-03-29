import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddressBookUI {
    private JFrame frame;
    private AddressBook addressBook;
    private JTextArea displayArea;

    public AddressBookUI() {
        frame = new JFrame("Address Book");
        addressBook = new AddressBook();
        JButton addButton = new JButton("Add Contact");
        JButton searchButton = new JButton("Search");
        JButton deleteButton = new JButton("Delete");
        JButton updateButton = new JButton("Update");
        displayArea = new JTextArea();
        displayArea.setEditable(false) ; 
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchContact();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteContact();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContact();
            }
        });

      
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);

      
        frame.getContentPane().add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    private void addContact() {
        String name = JOptionPane.showInputDialog("Enter name:");
        String phoneNumber = JOptionPane.showInputDialog("Enter phone number:");
        String email = JOptionPane.showInputDialog("Enter email:");

        Contact newContact = new Contact(name, phoneNumber, email);
        addressBook.addContact(newContact);

        updateDisplay();
    }

    
    private void searchContact() {
        String nameToSearch = JOptionPane.showInputDialog("Enter name to search:");
        for (Contact contact : addressBook.getContacts()) {
            if (contact.getName().equalsIgnoreCase(nameToSearch)) {
                displayArea.setText(contact.toString());
                return;
            }
        }
        displayArea.setText("Contact not found.");
    }

    private void deleteContact() {
        String nameToDelete = JOptionPane.showInputDialog("Enter name to delete:");
        for (Contact contact : addressBook.getContacts()) {
            if (contact.getName().equalsIgnoreCase(nameToDelete)) {
                addressBook.getContacts().remove(contact);
                updateDisplay();
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Contact not found.");
    }

    private void updateContact() {
    String nameToUpdate = JOptionPane.showInputDialog("Enter name to update:");
    for (Contact contact : addressBook.getContacts()) {
        if (contact.getName().equalsIgnoreCase(nameToUpdate)) {
            String option = JOptionPane.showInputDialog("Choose what to update (number/email):");
            if (option != null && !option.isEmpty()) {
                option = option.toLowerCase();
                switch (option) {
                    case "number":
                        String newPhoneNumber = JOptionPane.showInputDialog("Enter new phone number:");
                        contact.setPhoneNumber(newPhoneNumber);
                        break;
                    case "email":
                        String newEmail = JOptionPane.showInputDialog("Enter new email:");
                        contact.setEmail(newEmail);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid option. Choose 'number' or 'email'.");
                        return;
                }
                updateDisplay();
                return;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid option. Choose 'number' or 'email'.");
                return;
            }
        }
    }
    JOptionPane.showMessageDialog(null, "Contact not found.");
    }

    private void updateDisplay() {
        displayArea.setText("");
        for (Contact contact : addressBook.getContacts()) {
            displayArea.append(contact.toString() + "\n\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddressBookUI();
            }
        });
    }
}