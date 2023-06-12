package ma.emsi.firstfx.logic.entities;

import java.io.Serializable;
import java.util.Date;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Data

public class Film implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String titre;
    private String category;
    private int min_age;
    private int max_age;
    private Date registrationDate;

}
