package hr.riteh.fanzonef1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="user_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public User(String username, String email, String password, String dateOfBirth){
        this.username = username;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="username", nullable = false, unique = true)
    private String username;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="password", unique = true)
    private String password;

    @Column(name="dob")
    private String dateOfBirth;

    @Column(name="points")
    private int points = 0;
}
