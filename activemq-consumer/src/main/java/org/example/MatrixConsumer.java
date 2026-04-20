package org.example;
import javax.jms.*;
//import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.io.FileOutputStream;

public class MatrixConsumer {

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory =
                new ActiveMQConnectionFactory("tcp://localhost:61616");

        try (Connection connection = factory.createConnection()) {
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("matrix.queue");

            MessageConsumer consumer = session.createConsumer(queue);

            System.out.println("Waiting for Excel files...");

            while (true) {

                Message message = consumer.receive();

                if (message instanceof BytesMessage bytesMessage) {

                    // ===== SAFE read =====
                    byte[] data = new byte[(int) bytesMessage.getBodyLength()];
                    bytesMessage.readBytes(data);

                    String fileName = bytesMessage.getStringProperty("fileName");
                    if (fileName == null) {
                        fileName = "matrix.xlsx";
                        System.out.printf("File name is null");
                    }

                    // ===== Save file =====
                    try (FileOutputStream fos = new FileOutputStream(fileName)) {
                        fos.write(data);
                    }

                    System.out.println("Saved: " + fileName);
                }
            }
        }
    }

}