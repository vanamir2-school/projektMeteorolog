package ppj.vana.projekt.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "meshistory")
public class MesHistory {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;

    @Basic
    @NotNull
    @Column(name = "time")
    private Timestamp timestamp;

    public MesHistory() {
    }

    public MesHistory(@NotNull Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MesHistory{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MesHistory that = (MesHistory) o;

        if (!id.equals(that.id)) return false;
        return timestamp.equals(that.timestamp);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + timestamp.hashCode();
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
