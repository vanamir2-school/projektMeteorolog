package ppj.vana.projekt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "meshistory")
public class MesHistory {

    @Id
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
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MesHistory that = (MesHistory) o;
        return timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        return timestamp.hashCode();
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
