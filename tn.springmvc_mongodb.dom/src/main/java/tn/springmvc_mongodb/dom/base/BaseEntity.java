package tn.springmvc_mongodb.dom.base;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public abstract class BaseEntity implements Serializable {

    //@NotNull
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if ((other.getClass() != this.getClass()))
            return false;
        else {

            return ((BaseEntity) other).getId() == this.getId();

        }

    }
}
