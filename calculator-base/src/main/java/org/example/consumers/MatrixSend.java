package org.example.consumers;

import com.google.inject.Singleton;
import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.models.Matrix;

import java.util.UUID;
import java.util.function.Consumer;

@Singleton
public final class MatrixSend {

    public void accept(byte[] excelBytes) throws JMSException {
        ConnectionFactory factory =
                new ActiveMQConnectionFactory("tcp://localhost:61616");

        try (Connection connection = factory.createConnection()) {
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("matrix.queue");

            MessageProducer producer = session.createProducer(queue);

            BytesMessage message = session.createBytesMessage();
            message.writeBytes(excelBytes);

            message.setStringProperty("documentId", UUID.randomUUID().toString());
            message.setStringProperty("correlationId", "calc-123"); // or pass dynamically
            message.setStringProperty("fileName", "matrix.xlsx");
            message.setStringProperty(
                    "contentType",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            );
            message.setStringProperty("xmlMetadata", "<matrix/>");
            message.setStringProperty("sourceSystem", "matrix-app");
            message.setStringProperty("targetSystem", "excel-consumer");
            message.setStringProperty("direction", "OUTBOUND");

            producer.send(message);
        }
    }
}
