package hr.riteh.fanzonef1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "vote_table")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Vote {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="n1", nullable = false)
    private int n1;

    @Column(name="n2", nullable = false)
    private int n2;

    @Column(name="n3", nullable = false)
    private int n3;

    @Column(name="season", nullable = false)
    private int season;

    @Column(name="round", nullable = false)
    private int round;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

}
