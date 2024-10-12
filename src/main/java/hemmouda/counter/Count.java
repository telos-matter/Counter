package hemmouda.counter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Document
public class Count {

    @Id
    private String id;

    @NotBlank(message = "Name is mandatory")
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

    public long inc () {
        return ++value;
    }

    public long dec () {
        if (value > 0) {
            value--;
        }
        return value;
    }

    public long reset () {
        return value = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

        if (value != count.value) return false;
        if (!Objects.equals(id, count.id)) return false;
        return Objects.equals(name, count.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value);
    }

    @Override
    public String toString() {
        return "Count{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
