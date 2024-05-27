package co.edu.uniquindio.agencia20241.controller;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitFactory {
    private ConnectionFactory connectionFactory;
    public RabbitFactory() {
        this.connectionFactory = new ConnectionFactory();
        this.connectionFactory.setHost("localhost");
        this.connectionFactory.setPort(5672);
        this.connectionFactory.setUsername("guest");
        this.connectionFactory.setPassword("guest");
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }
}
