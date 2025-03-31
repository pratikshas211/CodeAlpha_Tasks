import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

class Room {
    private final int id;
    private final String type;
    private final double price;
    private boolean available;

    public Room(int id, String type, double price, boolean available) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.available = available;
    }

    public int getId() { return id; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return "Room ID: " + id + ", Type: " + type + ", Price: $" + price + ", Available: " + available;
    }
}

class Booking {
    private final int bookingId;
    private final String customerName;
    private final Room room;

    public Booking(int bookingId, String customerName, Room room) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.room = room;
        room.setAvailable(false);
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId + ", Customer: " + customerName + ", Room: " + room.getId();
    }
}

public class HotelReservationSystem {
    private static final List<Room> rooms = new ArrayList<>();
    private static final List<Booking> bookings = new ArrayList<>();
    private static int bookingCounter = 1;
    private static JTextArea outputArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HotelReservationSystem::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Initialize room data
        rooms.add(new Room(1, "Standard", 100.0, true));
        rooms.add(new Room(2, "Deluxe", 200.0, true));
        rooms.add(new Room(3, "Suite", 300.0, true));

        // Create main frame
        JFrame frame = new JFrame("Hotel Reservation System");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // Create buttons
        JButton viewRoomsButton = new JButton("View Available Rooms");
        JButton bookRoomButton = new JButton("Make a Reservation");
        JButton viewBookingsButton = new JButton("View Bookings");

        // Create text area with scroll
        outputArea = new JTextArea(12, 40);
        outputArea.setEditable(false);
        outputArea.setText("Welcome to the Hotel Reservation System!\nClick a button to proceed.\n"); // Initial message
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Button actions
        viewRoomsButton.addActionListener(e -> viewAvailableRooms());
        bookRoomButton.addActionListener(e -> makeReservation());
        viewBookingsButton.addActionListener(e -> viewBookings());

        // Add components to frame
        frame.add(viewRoomsButton);
        frame.add(bookRoomButton);
        frame.add(viewBookingsButton);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private static void viewAvailableRooms() {
        outputArea.setText("Available Rooms:\n");
        boolean hasRooms = false;
        for (Room room : rooms) {
            if (room.isAvailable()) {
                outputArea.append(room.toString() + "\n");
                hasRooms = true;
            }
        }
        if (!hasRooms) outputArea.append("No rooms available.\n");
        outputArea.repaint(); // Ensure UI updates
    }

    private static void makeReservation() {
        String name = JOptionPane.showInputDialog("Enter customer name:");
        if (name == null || name.trim().isEmpty()) {
            outputArea.setText("Reservation cancelled.\n");
            return;
        }
        
        String roomIdStr = JOptionPane.showInputDialog("Enter room ID to book:");
        if (roomIdStr == null || roomIdStr.trim().isEmpty()) {
            outputArea.setText("Reservation cancelled.\n");
            return;
        }
        
        try {
            int roomId = Integer.parseInt(roomIdStr);
            for (Room room : rooms) {
                if (room.getId() == roomId && room.isAvailable()) {
                    bookings.add(new Booking(bookingCounter++, name, room));
                    outputArea.setText("Booking successful!\n");
                    return;
                }
            }
            outputArea.setText("Room not available or invalid ID.\n");
        } catch (NumberFormatException e) {
            outputArea.setText("Invalid room ID.\n");
        }
        outputArea.repaint();
    }

    private static void viewBookings() {
        outputArea.setText("Bookings:\n");
        if (bookings.isEmpty()) {
            outputArea.append("No bookings found.\n");
        } else {
            for (Booking booking : bookings) {
                outputArea.append(booking.toString() + "\n");
            }
        }
        outputArea.repaint();
    }
}
