package common;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * Единица обмена между клиентом и сервером.
 * Передаётся через ObjectOutputStream / ObjectInputStream.
 */
public class Packet implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final PacketType type;
    private final String from;          // отправитель (null, если пакет от сервера)
    private final String to;            // получатель (null = всем / серверу)
    private final Serializable payload; // данные; тип зависит от PacketType
    private final long timestamp;       // время создания, мс с 1970-01-01 UTC

    public Packet(PacketType type, String from, String to, Serializable payload) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.payload = payload;
        this.timestamp = System.currentTimeMillis();
    }

    /** Короткая форма для пакетов без отправителя и получателя. */
    public Packet(PacketType type, Serializable payload) {
        this(type, null, null, payload);
    }

    public PacketType getType() { return type; }
    public String getFrom() { return from; }
    public String getTo() { return to; }
    public Serializable getPayload() { return payload; }
    public long getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "Packet[" + type + ", from=" + from + ", to=" + to
                + ", payload=" + payload + ", time=" + Instant.ofEpochMilli(timestamp) + "]";
    }
}
