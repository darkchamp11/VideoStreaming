import org.bytedeco.javacv.*;
import org.bytedeco.javacv.Frame;
import java.sql.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class CanvasOutput {
    private static Connection connection;
    private static final int INITIAL_BUFFER_SIZE = 10; // Initial buffer size
    private static int dynamicBufferSize = INITIAL_BUFFER_SIZE;

    public static void main(String[] args) {
        String videoPath = "D:/Movie.mkv"; // Update this path

        try {
            // Initialize database connection
            setupDatabase();

            // Start video processing with buffering
            processVideoWithBuffering(videoPath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabaseConnection();
        }
    }

    private static void processVideoWithBuffering(String videoPath) {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath);
        CanvasFrame canvas = new CanvasFrame("Buffered Video Streaming");
        Deque<Frame> frameBuffer = new ArrayDeque<>(dynamicBufferSize); // Initialize buffer

        try {
            grabber.start();
            canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

            Frame frame;
            int frameNumber = 0;

            while ((frame = grabber.grab()) != null) {
                if (!canvas.isVisible()) {
                    break; // Stop processing if the window is closed
                }

                // Simulate network or streaming conditions
                simulateNetworkConditions(frameNumber);

                // Add frame to buffer
                if (frameBuffer.size() >= dynamicBufferSize) {
                    frameBuffer.pollFirst(); // Remove the oldest frame if buffer is full
                }
                frameBuffer.offerLast(frame); // Add the new frame to the buffer

                // Display the most recent frame in the buffer
                canvas.showImage(frameBuffer.peekLast());

                // Save frame metadata to database
                saveFrameMetadataToDatabase(frameNumber, grabber.getTimestamp());

                frameNumber++;

                // Simulate real-time playback
                Thread.sleep((long) (1000 / grabber.getFrameRate()));
            }

            grabber.stop();
            System.out.println("Video processing with buffering completed.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                grabber.close();
                canvas.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void setupDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/videobufferdb";
            String user = "root";
            String password = "root"; // Replace with your MySQL root password
            connection = DriverManager.getConnection(url, user, password);

            // Create table if not exists
            String createTableSQL = "CREATE TABLE IF NOT EXISTS frame_metadata (" +
                    "frame_number INT, " +
                    "timestamp BIGINT, " +
                    "PRIMARY KEY (frame_number))";
            connection.createStatement().execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void saveFrameMetadataToDatabase(int frameNumber, long timestamp) {
        try {
            String insertSQL = "INSERT IGNORE INTO frame_metadata (frame_number, timestamp) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, frameNumber);
            preparedStatement.setLong(2, timestamp);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void closeDatabaseConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void simulateNetworkConditions(int frameNumber) {
        if (frameNumber % 50 == 0) { // Every 50 frames, simulate slower network
            dynamicBufferSize = Math.min(dynamicBufferSize + 5, 50); // Increase buffer size
        } else if (frameNumber % 100 == 0) { // Every 100 frames, simulate faster network
            dynamicBufferSize = Math.max(dynamicBufferSize - 5, INITIAL_BUFFER_SIZE); // Decrease buffer size
        }
    }
}
