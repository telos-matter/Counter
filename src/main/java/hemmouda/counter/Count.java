package hemmouda.counter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class Count {

    @Id
    private String name;
    private long value;

    public Count () {}

    public Count(String name, long value) {
        this.name = name;
        this.value = value;
    }

    public Count (String name, int value) {
        this(name, (long) value);
    }

    public Count (String name) {
        this(name, 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Count count)) return false;

        if (!Objects.equals(name, count.name)) return false;
        return Objects.equals(value, count.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        return "Count{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
