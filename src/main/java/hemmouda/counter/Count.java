package hemmouda.counter;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "_count")
public class Count {

    @Id
    private String name;

    @Column(name = "_value")
    private Long value;

    public Count () {}

    public Count(String name, Long value) {
        this.name = name;
        this.value = value;
    }

    public Count (String name, Integer value) {
        this(name, (long) value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
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
