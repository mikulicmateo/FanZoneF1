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

    public Vote(int n1, int n2, int n3, int season, int race, User user){
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
        this.season = season;
        this.round = race;
        this.user = user;
    }

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
